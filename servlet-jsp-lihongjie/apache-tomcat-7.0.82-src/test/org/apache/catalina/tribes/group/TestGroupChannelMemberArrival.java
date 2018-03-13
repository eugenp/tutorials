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

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ManagedChannel;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.MembershipListener;
import org.apache.catalina.tribes.TesterUtil;

public class TestGroupChannelMemberArrival {
    private static int count = 10;
    private ManagedChannel[] channels = new ManagedChannel[count];
    private TestMbrListener[] listeners = new TestMbrListener[count];

    @Before
    public void setUp() throws Exception {
        for (int i = 0; i < channels.length; i++) {
            channels[i] = new GroupChannel();
            channels[i].getMembershipService().setPayload( ("Channel-" + (i + 1)).getBytes("ASCII"));
            listeners[i] = new TestMbrListener( ("Listener-" + (i + 1)));
            channels[i].addMembershipListener(listeners[i]);
        }
        TesterUtil.addRandomDomain(channels);
    }

    @Test
    public void testMemberArrival() throws Exception {
        //purpose of this test is to make sure that we have received all the members
        //that we can expect before the start method returns
        Thread[] threads = new Thread[channels.length];
        for (int i=0; i<channels.length; i++ ) {
            final Channel channel = channels[i];
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        channel.start(Channel.DEFAULT);
                    }catch ( Exception x ) {
                        throw new RuntimeException(x);
                    }
                }
            };
            threads[i] = t;
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        Thread.sleep(5000);
        System.out.println(System.currentTimeMillis()
                + " All channels started.");
        for (int i = listeners.length - 1; i >= 0; i--) {
            TestMbrListener listener = listeners[i];
            synchronized (listener.members) {
                assertEquals("Checking member arrival length (" + listener.name
                        + ")", channels.length - 1, listener.members.size());
            }
        }
        System.out.println(System.currentTimeMillis()
                + " Members arrival counts checked.");
    }

    @After
    public void tearDown() throws Exception {

        for (int i = 0; i < channels.length; i++) {
            try {
                channels[i].stop(Channel.DEFAULT);
            } catch (Exception ignore) {
                // Ignore
            }
        }
    }

    public static class TestMbrListener
        implements MembershipListener {
        public String name = null;
        public TestMbrListener(String name) {
            this.name = name;
        }

        public ArrayList<Member> members = new ArrayList<Member>(1);

        @Override
        public void memberAdded(Member member) {
            String msg;
            int count;
            synchronized (members) {
                if (!members.contains(member)) {
                    members.add(member);
                    msg = "member added";
                } else {
                    msg = "member added called, but member is already in the list";
                }
                count = members.size();
            }
            report(msg, member, count);
        }

        @Override
        public void memberDisappeared(Member member) {
            String msg;
            int count;
            synchronized (members) {
                if (members.contains(member)) {
                    members.remove(member);
                    msg = "member disappeared";
                } else {
                    msg = "member disappeared called, but there is no such member in the list";
                }
                count = members.size();
            }
            report(msg, member, count);
        }

        private void report(String event, Member member, int count) {
            StringBuilder message = new StringBuilder(100);
            message.append(System.currentTimeMillis());
            message.append(' ');
            message.append(name);
            message.append(':');
            message.append(event);
            message.append(", has ");
            message.append(count);
            message.append(" members now. Member:[");
            message.append("host: ");
            appendByteArrayToString(message, member.getHost());
            message.append(", port: ");
            message.append(member.getPort());
            message.append(", id: ");
            appendByteArrayToString(message, member.getUniqueId());
            message.append(", payload: ");
            try {
                message.append(new String(member.getPayload(), "ASCII"));
            } catch (Exception x) {
                message.append("unknown");
            }
            Thread t = Thread.currentThread();
            message.append("]; Thread:").append(t.getName()).append(", hash:")
                    .append(t.hashCode());
            System.out.println(message);
        }

        private void appendByteArrayToString(StringBuilder sb, byte[] input) {
            if (input == null) {
                sb.append("null");
                return;
            }
            for (int i = 0; i < input.length; i++) {
                if (i > 0) {
                    sb.append('.');
                }
                sb.append(input[i] & 0xFF);
            }
        }
    }

}
