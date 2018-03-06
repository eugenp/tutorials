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

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.apache.catalina.tribes.ByteMessage;
import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.ManagedChannel;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.MembershipListener;
import org.apache.catalina.tribes.TesterUtil;
import org.apache.catalina.tribes.group.GroupChannel;

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
public class TestTcpFailureDetector {
    private TcpFailureDetector tcpFailureDetector1 = null;
    private TcpFailureDetector tcpFailureDetector2 = null;
    private ManagedChannel channel1 = null;
    private ManagedChannel channel2 = null;
    private TestMbrListener mbrlist1 = null;
    private TestMbrListener mbrlist2 = null;

    @Before
    public void setUp() throws Exception {
        channel1 = new GroupChannel();
        channel2 = new GroupChannel();
        channel1.getMembershipService().setPayload("Channel-1".getBytes("ASCII"));
        channel2.getMembershipService().setPayload("Channel-2".getBytes("ASCII"));
        mbrlist1 = new TestMbrListener("Channel-1");
        mbrlist2 = new TestMbrListener("Channel-2");
        tcpFailureDetector1 = new TcpFailureDetector();
        tcpFailureDetector2 = new TcpFailureDetector();
        channel1.addInterceptor(tcpFailureDetector1);
        channel2.addInterceptor(tcpFailureDetector2);
        channel1.addMembershipListener(mbrlist1);
        channel2.addMembershipListener(mbrlist2);
        TesterUtil.addRandomDomain(new ManagedChannel[] {channel1, channel2});
    }

    public void clear() {
        mbrlist1.members.clear();
        mbrlist2.members.clear();
    }

    @Test
    public void testTcpSendFailureMemberDrop() throws Exception {
        System.out.println("testTcpSendFailureMemberDrop()");
        clear();
        channel1.start(Channel.DEFAULT);
        channel2.start(Channel.DEFAULT);
        //Thread.sleep(1000);
        assertEquals("Expecting member count to be equal",mbrlist1.members.size(),mbrlist2.members.size());
        channel2.stop(Channel.SND_RX_SEQ);
        ByteMessage msg = new ByteMessage(new byte[1024]);
        try {
            channel1.send(channel1.getMembers(), msg, 0);
            fail("Message send should have failed.");
        } catch ( ChannelException x ) {
            // Ignore
        }
        assertEquals("Expecting member count to not be equal",mbrlist1.members.size()+1,mbrlist2.members.size());
        channel1.stop(Channel.DEFAULT);
        channel2.stop(Channel.DEFAULT);
    }

    @Test
    public void testTcpFailureMemberAdd() throws Exception {
        System.out.println("testTcpFailureMemberAdd()");
        clear();
        channel1.start(Channel.DEFAULT);
        channel2.start(Channel.SND_RX_SEQ);
        channel2.start(Channel.SND_TX_SEQ);
        channel2.start(Channel.MBR_RX_SEQ);
        channel2.stop(Channel.SND_RX_SEQ);
        channel2.start(Channel.MBR_TX_SEQ);
        //Thread.sleep(1000);
        assertEquals("Expecting member count to not be equal",mbrlist1.members.size()+1,mbrlist2.members.size());
        channel1.stop(Channel.DEFAULT);
        channel2.stop(Channel.DEFAULT);
    }

    @Test
    public void testTcpMcastFail() throws Exception {
        System.out.println("testTcpMcastFail()");
        clear();
        channel1.start(Channel.DEFAULT);
        channel2.start(Channel.DEFAULT);
        //Thread.sleep(1000);
        assertEquals("Expecting member count to be equal",mbrlist1.members.size(),mbrlist2.members.size());
        channel2.stop(Channel.MBR_TX_SEQ);
        ByteMessage msg = new ByteMessage(new byte[1024]);
        try {
            Thread.sleep(5000);
            assertEquals("Expecting member count to be equal",mbrlist1.members.size(),mbrlist2.members.size());
            channel1.send(channel1.getMembers(), msg, 0);
        } catch ( ChannelException x ) {
            fail("Message send should have succeeded.");
        }
        channel1.stop(Channel.DEFAULT);
        channel2.stop(Channel.DEFAULT);
    }

    @After
    public void tearDown() throws Exception {
        tcpFailureDetector1 = null;
        tcpFailureDetector2 = null;
        try {
            channel1.stop(Channel.DEFAULT);
        } catch (Exception ignore) {
            // Ignore
        }
        channel1 = null;
        try {
            channel2.stop(Channel.DEFAULT);
        } catch (Exception ignore) {
            // Ignore
        }
        channel2 = null;
    }

    public static class TestMbrListener implements MembershipListener {
        public String name = null;
        public TestMbrListener(String name) {
            this.name = name;
        }
        public ArrayList<Member> members = new ArrayList<Member>();
        @Override
        public void memberAdded(Member member) {
            if ( !members.contains(member) ) {
                members.add(member);
                try{
                    System.out.println(name + ":member added[" + new String(member.getPayload(), "ASCII") + "]");
                }catch ( Exception x ) {
                    System.out.println(name + ":member added[unknown]");
                }
            }
        }

        @Override
        public void memberDisappeared(Member member) {
            if ( members.contains(member) ) {
                members.remove(member);
                try{
                    System.out.println(name + ":member disappeared[" + new String(member.getPayload(), "ASCII") + "]");
                }catch ( Exception x ) {
                    System.out.println(name + ":member disappeared[unknown]");
                }
            }
        }

    }

}
