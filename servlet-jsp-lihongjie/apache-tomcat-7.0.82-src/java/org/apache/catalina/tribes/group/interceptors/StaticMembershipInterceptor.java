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
import java.util.Arrays;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.ChannelInterceptor;
import org.apache.catalina.tribes.ChannelMessage;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.group.AbsoluteOrder;
import org.apache.catalina.tribes.group.ChannelInterceptorBase;
import org.apache.catalina.tribes.io.ChannelData;
import org.apache.catalina.tribes.io.XByteBuffer;
import org.apache.catalina.tribes.membership.StaticMember;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class StaticMembershipInterceptor extends ChannelInterceptorBase {

    private static final Log log = LogFactory.getLog(StaticMembershipInterceptor.class);

    protected static final byte[] MEMBER_START = new byte[] {
        76, 111, 99, 97, 108, 32, 83, 116, 97, 116, 105, 99, 77, 101, 109, 98, 101, 114, 32, 78,
        111, 116, 105, 102, 105, 99, 97, 116, 105, 111, 110, 32, 68, 97, 116, 97};

    protected static final byte[] MEMBER_STOP = new byte[] {
        76, 111, 99, 97, 108, 32, 83, 116, 97, 116, 105, 99, 77, 101, 109, 98, 101, 114, 32, 83,
        104, 117, 116, 100, 111, 119, 110, 32, 68, 97, 116, 97};

    protected ArrayList<Member> members = new ArrayList<Member>();
    protected Member localMember = null;

    public StaticMembershipInterceptor() {
        super();
    }

    public void addStaticMember(Member member) {
        synchronized (members) {
            if (!members.contains(member)) members.add(member);
        }
    }

    public void removeStaticMember(Member member) {
        synchronized (members) {
            if (members.contains(member)) members.remove(member);
        }
    }

    public void setLocalMember(Member member) {
        this.localMember = member;
        ((StaticMember)localMember).setLocal(true);
    }

    @Override
    public void messageReceived(ChannelMessage msg) {
        if (msg.getMessage().getLength() == MEMBER_START.length &&
                Arrays.equals(MEMBER_START, msg.getMessage().getBytes())) {
            // receive member start
            Member member = getMember(msg.getAddress());
            if (member != null) {
                super.memberAdded(member);
            }
        } else if (msg.getMessage().getLength() == MEMBER_STOP.length &&
                Arrays.equals(MEMBER_STOP, msg.getMessage().getBytes())) {
            // receive member shutdown
            Member member = getMember(msg.getAddress());
            if (member != null && member instanceof StaticMember) {
                try {
                    ((StaticMember)member).setCommand(Member.SHUTDOWN_PAYLOAD);
                    super.memberDisappeared(member);
                } finally {
                    ((StaticMember)member).setCommand(new byte[0]);
                }
            }
        } else {
            super.messageReceived(msg);
        }
    }

    /**
     * has members
     */
    @Override
    public boolean hasMembers() {
        return super.hasMembers() || (members.size()>0);
    }

    /**
     * Get all current cluster members
     * @return all members or empty array
     */
    @Override
    public Member[] getMembers() {
        if ( members.size() == 0 ) return super.getMembers();
        else {
            synchronized (members) {
                Member[] others = super.getMembers();
                Member[] result = new Member[members.size() + others.length];
                for (int i = 0; i < others.length; i++) result[i] = others[i];
                for (int i = 0; i < members.size(); i++) result[i + others.length] = members.get(i);
                AbsoluteOrder.absoluteOrder(result);
                return result;
            }//sync
        }//end if
    }

    /**
     *
     * @param mbr Member
     * @return Member
     */
    @Override
    public Member getMember(Member mbr) {
        if ( members.contains(mbr) ) return members.get(members.indexOf(mbr));
        else return super.getMember(mbr);
    }

    /**
     * Return the member that represents this node.
     *
     * @return Member
     */
    @Override
    public Member getLocalMember(boolean incAlive) {
        if (this.localMember != null ) return localMember;
        else return super.getLocalMember(incAlive);
    }
    
    /**
     * Send notifications upwards
     * @param svc int
     * @throws ChannelException
     */
    @Override
    public void start(int svc) throws ChannelException {
        if ( (Channel.SND_RX_SEQ&svc)==Channel.SND_RX_SEQ ) super.start(Channel.SND_RX_SEQ); 
        if ( (Channel.SND_TX_SEQ&svc)==Channel.SND_TX_SEQ ) super.start(Channel.SND_TX_SEQ); 
        final ChannelInterceptorBase base = this;
        for (final Member member : members) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    base.memberAdded(member);
                    if (getfirstInterceptor().getMember(member) != null) {
                        sendLocalMember(new Member[]{member});
                    }
                }
            };
            t.start();
        }
        super.start(svc & (~Channel.SND_RX_SEQ) & (~Channel.SND_TX_SEQ));

        // check required interceptors
        TcpFailureDetector failureDetector = null;
        TcpPingInterceptor pingInterceptor = null;
        ChannelInterceptor prev = getPrevious();
        while (prev != null) {
            if (prev instanceof TcpFailureDetector ) failureDetector = (TcpFailureDetector) prev;
            if (prev instanceof TcpPingInterceptor) pingInterceptor = (TcpPingInterceptor) prev;
            prev = prev.getPrevious();
        }
        if (failureDetector == null) {
            log.warn("There is no TcpFailureDetector. Automatic detection of static members does"
                    + " not work properly. By defining the StaticMembershipInterceptor under the"
                    + " TcpFailureDetector, automatic detection of the static members will work.");
        }
        if (pingInterceptor == null) {
            log.warn("There is no TcpPingInterceptor. The health check of static member does"
                    + " not work properly. By defining the TcpPingInterceptor, the health check of"
                    + " static member will work.");
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Sends local member shutdown.
     */
    @Override
    public void stop(int svc) throws ChannelException {
        // Sends local member shutdown.
        Member[] members = getfirstInterceptor().getMembers();
        sendShutdown(members);
        super.stop(svc);
    }

    protected void sendLocalMember(Member[] members) {
        try {
            sendMemberMessage(members, MEMBER_START);
        } catch (ChannelException cx) {
            log.warn("Local member notification failed.",cx);
        }
    }

    protected void sendShutdown(Member[] members) {
        try {
            sendMemberMessage(members, MEMBER_STOP);
        } catch (ChannelException cx) {
            log.warn("Shutdown notification failed.",cx);
        }
    }

    protected ChannelInterceptor getfirstInterceptor() {
        ChannelInterceptor result = null;
        ChannelInterceptor now = this;
        do {
            result = now;
            now = now.getPrevious();
        } while (now.getPrevious() != null);
        return result;
    }

    protected void sendMemberMessage(Member[] members, byte[] message) throws ChannelException {
        if ( members == null || members.length == 0 ) return;
        ChannelData data = new ChannelData(true);
        data.setAddress(getLocalMember(false));
        data.setTimestamp(System.currentTimeMillis());
        data.setOptions(getOptionFlag());
        data.setMessage(new XByteBuffer(message, false));
        super.sendMessage(members, data, null);
    }
}