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
package org.apache.catalina.tribes.group;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.apache.catalina.startup.LoggingBaseTest;
import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelListener;
import org.apache.catalina.tribes.ManagedChannel;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.TesterUtil;
import org.apache.catalina.tribes.transport.ReplicationTransmitter;

public class TestGroupChannelSenderConnections extends LoggingBaseTest {
    private static final int count = 2;
    private ManagedChannel[] channels = new ManagedChannel[count];
    private TestMsgListener[] listeners = new TestMsgListener[count];

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        for (int i = 0; i < channels.length; i++) {
            channels[i] = new GroupChannel();
            channels[i].getMembershipService().setPayload( ("Channel-" + (i + 1)).getBytes("ASCII"));
            listeners[i] = new TestMsgListener( ("Listener-" + (i + 1)));
            channels[i].addChannelListener(listeners[i]);
        }
        TesterUtil.addRandomDomain(channels);
        for (int i = 0; i < channels.length; i++) {
            channels[i].start(Channel.SND_RX_SEQ|Channel.SND_TX_SEQ);
        }
    }

    public void sendMessages(long delay, long sleep) throws Exception {
        resetMessageCounters();
        Member local = channels[0].getLocalMember(true);
        Member dest = channels[1].getLocalMember(true);
        int n = 3;
        log.info("Sending " + n + " messages from [" + local.getName()
                + "] to [" + dest.getName() + "] with delay of " + delay
                + " ms between them.");
        for (int i = 0; i < n; i++) {
            channels[0].send(new Member[] { dest }, new TestMsg(), 0);
            boolean last = (i == n - 1);
            if (!last && delay > 0) {
                Thread.sleep(delay);
            }
        }
        log.info("Messages sent. Waiting no more than " + (sleep / 1000)
                + " seconds for them to be received");
        long startTime = System.currentTimeMillis();
        int countReceived;
        while ((countReceived = getReceivedMessageCount()) != n) {
            long time = System.currentTimeMillis();
            if ((time - startTime) > sleep) {
                fail("Only " + countReceived + " out of " + n
                        + " messages have been received in " + (sleep / 1000)
                        + " seconds");
                break;
            }
            Thread.sleep(100);
        }
    }

    @Test
    public void testConnectionLinger() throws Exception {
        sendMessages(0,15000);
    }

    @Test
    public void testKeepAliveCount() throws Exception {
        log.info("Setting keep alive count to 0");
        for (int i = 0; i < channels.length; i++) {
            ReplicationTransmitter t = (ReplicationTransmitter)channels[0].getChannelSender();
            t.getTransport().setKeepAliveCount(0);
        }
        sendMessages(1000,15000);
    }

    @Test
    public void testKeepAliveTime() throws Exception {
        log.info("Setting keep alive count to 1 second");
        for (int i = 0; i < channels.length; i++) {
            ReplicationTransmitter t = (ReplicationTransmitter)channels[0].getChannelSender();
            t.getTransport().setKeepAliveTime(1000);
        }
        sendMessages(2000,15000);
    }

    @After
    @Override
    public void tearDown() throws Exception {
        try {
            for (int i = 0; i < channels.length; i++) {
                channels[i].stop(Channel.DEFAULT);
            }
        } finally {
            super.tearDown();
        }
    }

    private void resetMessageCounters() {
        for (TestMsgListener listener: listeners) {
            listener.reset();
        }
    }

    private int getReceivedMessageCount() {
        int count = 0;
        for (TestMsgListener listener: listeners) {
            count += listener.getReceivedCount();
        }
        return count;
    }

    // Test message. The message size is random.
    public static class TestMsg implements Serializable {
        private static final long serialVersionUID = 1L;
        private static Random r = new Random();
        private HashMap<Integer, ArrayList<Object>> map =
            new HashMap<Integer, ArrayList<Object>>();
        public TestMsg() {
            int size = Math.abs(r.nextInt() % 200);
            for (int i=0; i<size; i++ ) {
                int length = Math.abs(r.nextInt() %65000);
                ArrayList<Object> list = new ArrayList<Object>(length);
                map.put(Integer.valueOf(i),list);
            }
        }
    }

    public class TestMsgListener implements ChannelListener {
        private final String name;
        private final AtomicInteger counter = new AtomicInteger();
        public TestMsgListener(String name) {
            this.name = name;
        }

        public void reset() {
            counter.set(0);
        }

        public int getReceivedCount() {
            return counter.get();
        }

        @Override
        public void messageReceived(Serializable msg, Member sender) {
            counter.incrementAndGet();
            log.info("["+name+"] Received message:"+msg+" from " + sender.getName());
        }

        @Override
        public boolean accept(Serializable msg, Member sender) {
            return true;
        }

    }

}
