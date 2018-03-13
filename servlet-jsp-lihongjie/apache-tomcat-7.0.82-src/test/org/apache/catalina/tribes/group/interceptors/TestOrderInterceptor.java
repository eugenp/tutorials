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
package org.apache.catalina.tribes.group.interceptors;

import java.io.Serializable;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.ChannelListener;
import org.apache.catalina.tribes.ChannelMessage;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.TesterUtil;
import org.apache.catalina.tribes.group.ChannelInterceptorBase;
import org.apache.catalina.tribes.group.GroupChannel;
import org.apache.catalina.tribes.group.InterceptorPayload;

public class TestOrderInterceptor {

    GroupChannel[] channels = null;
    OrderInterceptor[] orderitcs = null;
    MangleOrderInterceptor[] mangleitcs = null;
    TestListener[] test = null;
    int channelCount = 2;
    Thread[] threads = null;

    @Before
    public void setUp() throws Exception {
        System.out.println("Setup");
        channels = new GroupChannel[channelCount];
        orderitcs = new OrderInterceptor[channelCount];
        mangleitcs = new MangleOrderInterceptor[channelCount];
        test = new TestListener[channelCount];
        threads = new Thread[channelCount];
        for ( int i=0; i<channelCount; i++ ) {
            channels[i] = new GroupChannel();

            orderitcs[i] = new OrderInterceptor();
            mangleitcs[i] = new MangleOrderInterceptor();
            orderitcs[i].setExpire(Long.MAX_VALUE);
            channels[i].addInterceptor(orderitcs[i]);
            channels[i].addInterceptor(mangleitcs[i]);
            test[i] = new TestListener(i);
            channels[i].addChannelListener(test[i]);
            final int j = i;
            threads[i] = new Thread() {
                @Override
                public void run() {
                    try {
                        channels[j].start(Channel.DEFAULT);
                        Thread.sleep(50);
                    } catch (Exception x) {
                        x.printStackTrace();
                    }
                }
            };
        }
        TesterUtil.addRandomDomain(channels);
        for ( int i=0; i<channelCount; i++ ) threads[i].start();
        for ( int i=0; i<channelCount; i++ ) threads[i].join();
        Thread.sleep(1000);
    }

    @Test
    public void testOrder1() throws Exception {
        Member[] dest = channels[0].getMembers();
        final AtomicInteger value = new AtomicInteger(0);
        for ( int i=0; i<100; i++ ) {
            channels[0].send(dest,Integer.valueOf(value.getAndAdd(1)),0);
        }
        Thread.sleep(5000);
        for ( int i=0; i<test.length; i++ ) {
            assertFalse(test[i].fail);
        }
    }

    @Test
    public void testOrder2() throws Exception {
        final Member[] dest = channels[0].getMembers();
        final AtomicInteger value = new AtomicInteger(0);
        final Queue<Exception> exceptionQueue = new ConcurrentLinkedQueue<Exception>();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        synchronized (channels[0]) {
                            channels[0].send(dest, Integer.valueOf(value.getAndAdd(1)), 0);
                        }
                    }catch ( Exception x ) {
                        exceptionQueue.add(x);
                    }
                }
            }
        };
        Thread[] threads = new Thread[5];
        for (int i=0;i<threads.length;i++) {
            threads[i] = new Thread(run);
        }
        for (int i=0;i<threads.length;i++) {
            threads[i].start();
        }
        for (int i=0;i<threads.length;i++) {
            threads[i].join();
        }
        if (!exceptionQueue.isEmpty()) {
            fail("Exception while sending in threads: "
                    + exceptionQueue.remove().toString());
        }
        Thread.sleep(5000);
        for ( int i=0; i<test.length; i++ ) {
            assertFalse(test[i].fail);
        }
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("tearDown");
        for ( int i=0; i<channelCount; i++ ) {
            channels[i].stop(Channel.DEFAULT);
        }
    }

    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main(TestOrderInterceptor.class.getName());
    }

    public static class TestListener implements ChannelListener {
        int id = -1;
        public TestListener(int id) {
            this.id = id;
        }
        int cnt = 0;
        int total = 0;
        volatile boolean fail = false;
        @Override
        public synchronized void messageReceived(Serializable msg, Member sender) {
            total++;
            Integer i = (Integer)msg;
            if ( i.intValue() != cnt ) fail = true;
            else cnt++;
            System.out.println("Listener["+id+"] Message received:"+i+" Count:"+total+" Fail:"+fail);

        }

        @Override
        public boolean accept(Serializable msg, Member sender) {
            return (msg instanceof Integer);
        }
    }

    public static class MangleOrderInterceptor extends ChannelInterceptorBase {
        ChannelMessage hold = null;
        Member[] dest = null;
        @Override
        public synchronized void sendMessage(Member[] destination, ChannelMessage msg, InterceptorPayload payload) throws ChannelException {
            if ( hold == null ) {
                //System.out.println("Skipping message:"+msg);
                hold = (ChannelMessage)msg.deepclone();
                dest = new Member[destination.length];
                System.arraycopy(destination,0,dest,0,dest.length);
            } else {
                //System.out.println("Sending message:"+msg);
                super.sendMessage(destination,msg,payload);
                //System.out.println("Sending message:"+hold);
                super.sendMessage(dest,hold,null);
                hold = null;
                dest = null;
            }
        }
    }


}
