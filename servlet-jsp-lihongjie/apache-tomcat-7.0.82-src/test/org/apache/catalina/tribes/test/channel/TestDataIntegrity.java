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

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelListener;
import org.apache.catalina.tribes.ManagedChannel;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.TesterUtil;
import org.apache.catalina.tribes.group.GroupChannel;
import org.apache.catalina.tribes.group.interceptors.MessageDispatch15Interceptor;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class TestDataIntegrity {
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
        listener1 = new Listener();
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
    public void testDataSendNO_ACK() throws Exception {
        System.err.println("Starting NO_ACK");
        Thread[] threads = new Thread[threadCount];
        for (int x=0; x<threads.length; x++ ) {
            threads[x] = new Thread() {
                @Override
                public void run() {
                    try {
                        long start = System.currentTimeMillis();
                        for (int i = 0; i < msgCount; i++) channel1.send(new Member[] {channel2.getLocalMember(false)}, Data.createRandomData(),0);
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
        while ( (System.currentTimeMillis()-start)<15000 && msgCount*threadCount!=listener1.count) Thread.sleep(500);
        System.err.println("Finished NO_ACK ["+listener1.count+"]");
        assertEquals("Checking success messages.",msgCount*threadCount,listener1.count);
    }

    @Test
    public void testDataSendASYNCM() throws Exception {
            System.err.println("Starting ASYNC MULTI THREAD");
            Thread[] threads = new Thread[threadCount];
            for (int x=0; x<threads.length; x++ ) {
                threads[x] = new Thread() {
                    @Override
                    public void run() {
                        try {
                            long start = System.currentTimeMillis();
                            for (int i = 0; i < msgCount; i++) channel1.send(new Member[] {channel2.getLocalMember(false)}, Data.createRandomData(),Channel.SEND_OPTIONS_ASYNCHRONOUS);
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
            while ( (System.currentTimeMillis()-start)<25000 && msgCount*threadCount!=listener1.count) Thread.sleep(500);
            System.err.println("Finished ASYNC MULTI THREAD ["+listener1.count+"]");
            assertEquals("Checking success messages.",msgCount*threadCount,listener1.count);
    }

    @Test
    public void testDataSendASYNC() throws Exception {
        System.err.println("Starting ASYNC");
        for (int i=0; i<msgCount; i++) channel1.send(new Member[] {channel2.getLocalMember(false)},Data.createRandomData(),Channel.SEND_OPTIONS_ASYNCHRONOUS);
        //sleep for 50 sec, let the other messages in
        long start = System.currentTimeMillis();
        while ( (System.currentTimeMillis()-start)<5000 && msgCount!=listener1.count) Thread.sleep(500);
        System.err.println("Finished ASYNC");
        assertEquals("Checking success messages.",msgCount,listener1.count);
    }

    @Test
    public void testDataSendACK() throws Exception {
        System.err.println("Starting ACK");
        for (int i=0; i<msgCount; i++) channel1.send(new Member[] {channel2.getLocalMember(false)},Data.createRandomData(),Channel.SEND_OPTIONS_USE_ACK);
        Thread.sleep(250);
        System.err.println("Finished ACK");
        assertEquals("Checking success messages.",msgCount,listener1.count);
    }

    @Test
    public void testDataSendSYNCACK() throws Exception {
        System.err.println("Starting SYNC_ACK");
        for (int i=0; i<msgCount; i++) channel1.send(new Member[] {channel2.getLocalMember(false)},Data.createRandomData(),Channel.SEND_OPTIONS_SYNCHRONIZED_ACK|Channel.SEND_OPTIONS_USE_ACK);
        Thread.sleep(250);
        System.err.println("Finished SYNC_ACK");
        assertEquals("Checking success messages.",msgCount,listener1.count);
    }

    public static class Listener implements ChannelListener {
        long count = 0;
        @Override
        public boolean accept(Serializable s, Member m) {
            return (s instanceof Data);
        }

        @Override
        public void messageReceived(Serializable s, Member m) {
            Data d = (Data)s;
            if ( !Data.verify(d) ) {
                System.err.println("ERROR");
            } else {
                count++;
                if ((count %1000) ==0 ) {
                    System.err.println("SUCCESS:"+count);
                }
            }
        }
    }

    public static class Data implements Serializable {
        private static final long serialVersionUID = 1L;
        public int length;
        public byte[] data;
        public byte key;
        public static Random r = new Random();
        public static Data createRandomData() {
            int i = r.nextInt();
            i = ( i % 127 );
            int length = Math.abs(r.nextInt() % 65555);
            Data d = new Data();
            d.length = length;
            d.key = (byte)i;
            d.data = new byte[length];
            Arrays.fill(d.data,d.key);
            return d;
        }

        public static boolean verify(Data d) {
            boolean result = (d.length == d.data.length);
            for ( int i=0; result && (i<d.data.length); i++ ) result = result && d.data[i] == d.key;
            return result;
        }
    }



}
