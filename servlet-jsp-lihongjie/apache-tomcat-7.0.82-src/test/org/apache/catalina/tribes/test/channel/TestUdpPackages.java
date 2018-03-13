/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.catalina.tribes.test.channel;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelListener;
import org.apache.catalina.tribes.ChannelReceiver;
import org.apache.catalina.tribes.ManagedChannel;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.TesterUtil;
import org.apache.catalina.tribes.group.GroupChannel;
import org.apache.catalina.tribes.group.interceptors.MessageDispatch15Interceptor;
import org.apache.catalina.tribes.group.interceptors.ThroughputInterceptor;
import org.apache.catalina.tribes.io.XByteBuffer;
import org.apache.catalina.tribes.transport.AbstractSender;
import org.apache.catalina.tribes.transport.ReceiverBase;
import org.apache.catalina.tribes.transport.ReplicationTransmitter;

/**
 */
public class TestUdpPackages {
    private int msgCount = 500;
    private int threadCount = 20;
    private GroupChannel channel1;
    private GroupChannel channel2;
    private Listener listener1;

    @Before
    public void setUp() throws Exception {
        channel1 = new GroupChannel();
        channel1.addInterceptor(new MessageDispatch15Interceptor());
        channel2 = new GroupChannel();
        channel2.addInterceptor(new MessageDispatch15Interceptor());
        ThroughputInterceptor tint = new ThroughputInterceptor();
        tint.setInterval(500);
        ThroughputInterceptor tint2 = new ThroughputInterceptor();
        tint2.setInterval(500);
        //channel1.addInterceptor(tint);
        channel2.addInterceptor(tint2);
        listener1 = new Listener();
        ReceiverBase rb1 = (ReceiverBase)channel1.getChannelReceiver();
        ReceiverBase rb2 = (ReceiverBase)channel2.getChannelReceiver();
        rb1.setUdpPort(50000);
        rb2.setUdpPort(50000);
        channel2.addChannelListener(listener1);
        TesterUtil.addRandomDomain(new ManagedChannel[] {channel1, channel2});
        channel1.start(Channel.DEFAULT);
        channel2.start(Channel.DEFAULT);
    }

    @After
    public void tearDown() throws Exception {
        channel1.stop(Channel.DEFAULT);
        channel2.stop(Channel.DEFAULT);
    }

    @Test
    public void testSingleDataSendNO_ACK() throws Exception {
        AbstractSender s1 =(AbstractSender) ((ReplicationTransmitter)channel1.getChannelSender()).getTransport();
        AbstractSender s2 =(AbstractSender) ((ReplicationTransmitter)channel2.getChannelSender()).getTransport();
        s1.setTimeout(Long.MAX_VALUE); //for debugging
        s2.setTimeout(Long.MAX_VALUE); //for debugging

        System.err.println("Starting Single package NO_ACK");
        channel1.send(new Member[] {channel2.getLocalMember(false)}, Data.createRandomData(1024),Channel.SEND_OPTIONS_UDP);
        Thread.sleep(500);
        System.err.println("Finished Single package NO_ACK ["+listener1.count+"]");
        assertEquals("Checking success messages.",1,listener1.count.get());
    }

    @Test
    public void testDataSendNO_ACK() throws Exception {
        final AtomicInteger counter = new AtomicInteger(0);
        ReceiverBase rb1 = (ReceiverBase)channel1.getChannelReceiver();
        ReceiverBase rb2 = (ReceiverBase)channel2.getChannelReceiver();
        rb1.setUdpRxBufSize(1024*1024*10);
        rb2.setUdpRxBufSize(1024*1024*10);
        rb1.setUdpTxBufSize(1024*1024*10);
        rb2.setUdpTxBufSize(1024*1024*10);
        System.err.println("Starting NO_ACK");
        Thread[] threads = new Thread[threadCount];
        for (int x=0; x<threads.length; x++ ) {
            threads[x] = new Thread() {
                @Override
                public void run() {
                    try {
                        long start = System.currentTimeMillis();
                        for (int i = 0; i < msgCount; i++) {
                            int cnt = counter.getAndAdd(1);
                            channel1.send(new Member[] {channel2.getLocalMember(false)}, Data.createRandomData(1024,cnt),Channel.SEND_OPTIONS_UDP);
                            //Thread.currentThread().sleep(10);
                        }
                        System.out.println("Thread["+this.getName()+"] sent "+msgCount+" messages in "+(System.currentTimeMillis()-start)+" ms.");
                    }catch ( Exception x ) {
                        x.printStackTrace();
                        return;
                    }
                }
            };
        }
        for (int x=0; x<threads.length; x++ ) { threads[x].start();}
        for (int x=0; x<threads.length; x++ ) { threads[x].join();}
        //sleep for 50 sec, let the other messages in
        long start = System.currentTimeMillis();
        while ( (System.currentTimeMillis()-start)<25000 && msgCount*threadCount!=listener1.count.get()) Thread.sleep(500);
        System.err.println("Finished NO_ACK ["+listener1.count+"]");
        System.out.println("Sent "+counter.get()+ " messages. Received "+listener1.count+" Highest msg received:"+listener1.maxIdx);
        System.out.print("Missing messages:");
        printMissingMsgs(listener1.nrs,counter.get());
        assertEquals("Checking success messages.",msgCount*threadCount,listener1.count.get());
    }

    public static void printMissingMsgs(int[] msgs, int maxIdx) {
        for (int i=0; i<maxIdx && i<msgs.length; i++) {
            if (msgs[i]==0) System.out.print(i+", ");
        }
        System.out.println();
    }

    @Test
    public void testDataSendASYNCM() throws Exception {
        final AtomicInteger counter = new AtomicInteger(0);
        ReceiverBase rb1 = (ReceiverBase)channel1.getChannelReceiver();
        ReceiverBase rb2 = (ReceiverBase)channel2.getChannelReceiver();
        rb1.setUdpRxBufSize(1024*1024*10);
        rb2.setUdpRxBufSize(1024*1024*10);
        rb1.setUdpTxBufSize(1024*1024*10);
        rb2.setUdpTxBufSize(1024*1024*10);
        System.err.println("Starting NO_ACK");
        Thread[] threads = new Thread[threadCount];
        for (int x=0; x<threads.length; x++ ) {
            threads[x] = new Thread() {
                @Override
                public void run() {
                    try {
                        long start = System.currentTimeMillis();
                        for (int i = 0; i < msgCount; i++) {
                            int cnt = counter.getAndAdd(1);
                            channel1.send(new Member[] {channel2.getLocalMember(false)}, Data.createRandomData(1024,cnt),Channel.SEND_OPTIONS_UDP|Channel.SEND_OPTIONS_ASYNCHRONOUS);
                            //Thread.currentThread().sleep(10);
                        }
                        System.out.println("Thread["+this.getName()+"] sent "+msgCount+" messages in "+(System.currentTimeMillis()-start)+" ms.");
                    }catch ( Exception x ) {
                        x.printStackTrace();
                        return;
                    }
                }
            };
        }
        for (int x=0; x<threads.length; x++ ) { threads[x].start();}
        for (int x=0; x<threads.length; x++ ) { threads[x].join();}
        //sleep for 50 sec, let the other messages in
        long start = System.currentTimeMillis();
        while ( (System.currentTimeMillis()-start)<25000 && msgCount*threadCount!=listener1.count.get()) Thread.sleep(500);
        System.err.println("Finished NO_ACK ["+listener1.count+"]");
        System.out.println("Sent "+counter.get()+ " messages. Received "+listener1.count+" Highest msg received:"+listener1.maxIdx);
        System.out.print("Missing messages:");
        printMissingMsgs(listener1.nrs,counter.get());
        assertEquals("Checking success messages.",msgCount*threadCount,listener1.count.get());
    }

    @Test
    public void testDataSendASYNC() throws Exception {
        System.err.println("Starting ASYNC");
        for (int i=0; i<msgCount; i++) channel1.send(new Member[] {channel2.getLocalMember(false)},Data.createRandomData(1024),Channel.SEND_OPTIONS_ASYNCHRONOUS|Channel.SEND_OPTIONS_UDP);
        //sleep for 50 sec, let the other messages in
        long start = System.currentTimeMillis();
        while ( (System.currentTimeMillis()-start)<5000 && msgCount!=listener1.count.get()) Thread.sleep(500);
        System.err.println("Finished ASYNC");
        assertEquals("Checking success messages.",msgCount,listener1.count.get());
    }

    @Test
    public void testDataSendACK() throws Exception {
        System.err.println("Starting ACK");
        for (int i=0; i<msgCount; i++) channel1.send(new Member[] {channel2.getLocalMember(false)},Data.createRandomData(1024),Channel.SEND_OPTIONS_USE_ACK|Channel.SEND_OPTIONS_UDP);
        Thread.sleep(250);
        System.err.println("Finished ACK");
        assertEquals("Checking success messages.",msgCount,listener1.count.get());
    }

    @Test
    public void testDataSendSYNCACK() throws Exception {
        System.err.println("Starting SYNC_ACK");
        for (int i=0; i<msgCount; i++) channel1.send(new Member[] {channel2.getLocalMember(false)},Data.createRandomData(1024),Channel.SEND_OPTIONS_SYNCHRONIZED_ACK|Channel.SEND_OPTIONS_USE_ACK|Channel.SEND_OPTIONS_UDP);
        Thread.sleep(250);
        System.err.println("Finished SYNC_ACK");
        assertEquals("Checking success messages.",msgCount,listener1.count.get());
    }

    public static class Listener implements ChannelListener {
        AtomicLong count = new AtomicLong(0);
        int maxIdx = -1;
        int[] nrs = new int[1000000];
        public Listener() {
            Arrays.fill(nrs, 0);
        }
        @Override
        public boolean accept(Serializable s, Member m) {
            return (s instanceof Data);
        }

        @Override
        public void messageReceived(Serializable s, Member m) {
            try {
                Data d = (Data)s;
                if ( !Data.verify(d) ) {
                    System.err.println("ERROR - Unable to verify data package");
                } else {
                    long c = count.addAndGet(1);
                    if ((c%1000) ==0 ) {
                        System.err.println("SUCCESS:"+c);
                    }
                    int nr = d.getNumber();
                    if (nr>=0 && nr<nrs.length) {
                        maxIdx = Math.max(maxIdx, nr);
                        nrs[nr] = 1;
                    }
                }
            }catch (Exception x ) {
                x.printStackTrace();
            }
        }
    }

    public static class Data implements Serializable {
        private static final long serialVersionUID = 1L;
        public int length;
        public byte[] data;
        public byte key;
        public boolean hasNr = false;
        public static Random r = new Random();
        public static Data createRandomData() {
            return createRandomData(ChannelReceiver.MAX_UDP_SIZE);
        }
        public static Data createRandomData(int size) {
            return createRandomData(size,-1);
        }

        public static Data createRandomData(int size, int number) {
            int i = r.nextInt();
            i = ( i % 127 );
            int length = Math.abs(r.nextInt() % size);
            if (length<100) length += 100;
            Data d = new Data();
            d.length = length;
            d.key = (byte)i;
            d.data = new byte[length];
            Arrays.fill(d.data,d.key);
            if (number>0 && d.data.length>=4) {
                //populate number
                d.hasNr = true;
                XByteBuffer.toBytes(number,d.data, 0);
            }
            return d;
        }

        public int getNumber() {
            if (!hasNr) return -1;
            return XByteBuffer.toInt(this.data, 0);
        }

        public static boolean verify(Data d) {
            boolean result = (d.length == d.data.length);
            for ( int i=(d.hasNr?4:0); result && (i<d.data.length); i++ ) result = result && d.data[i] == d.key;
            return result;
        }
    }



}
