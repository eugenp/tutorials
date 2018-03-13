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

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.ChannelMessage;
import org.apache.catalina.tribes.ChannelReceiver;
import org.apache.catalina.tribes.ChannelSender;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.MembershipService;
import org.apache.catalina.tribes.MessageListener;
import org.apache.catalina.tribes.UniqueId;
import org.apache.catalina.tribes.membership.McastService;
import org.apache.catalina.tribes.membership.StaticMember;
import org.apache.catalina.tribes.transport.ReceiverBase;
import org.apache.catalina.tribes.transport.ReplicationTransmitter;
import org.apache.catalina.tribes.transport.SenderState;
import org.apache.catalina.tribes.transport.nio.NioReceiver;
import org.apache.catalina.tribes.util.Arrays;
import org.apache.catalina.tribes.util.Logs;


/**
 * The channel coordinator object coordinates the membership service,
 * the sender and the receiver.
 * This is the last interceptor in the chain.
 * @author Filip Hanik
 */
public class ChannelCoordinator extends ChannelInterceptorBase implements MessageListener {
    private ChannelReceiver clusterReceiver = new NioReceiver();
    private ChannelSender clusterSender = new ReplicationTransmitter();
    private MembershipService membershipService = new McastService();
    
    private int startLevel = 0;

    public ChannelCoordinator() {
        // Override default
        this.optionFlag = Channel.SEND_OPTIONS_BYTE_MESSAGE |
                Channel.SEND_OPTIONS_USE_ACK |
                Channel.SEND_OPTIONS_SYNCHRONIZED_ACK;
    }
    
    public ChannelCoordinator(ChannelReceiver receiver,
                              ChannelSender sender,
                              MembershipService service) {
        this();
        this.setClusterReceiver(receiver);
        this.setClusterSender(sender);
        this.setMembershipService(service);
    }
    
    /**
     * Send a message to one or more members in the cluster
     * @param destination Member[] - the destinations, null or zero length means all
     * @param msg ClusterMessage - the message to send
     * @param payload TBA
     */
    @Override
    public void sendMessage(Member[] destination, ChannelMessage msg, InterceptorPayload payload) throws ChannelException {
        if ( destination == null ) destination = membershipService.getMembers();
        if ((msg.getOptions()&Channel.SEND_OPTIONS_MULTICAST) == Channel.SEND_OPTIONS_MULTICAST) {
            membershipService.broadcast(msg);
        } else {
            clusterSender.sendMessage(msg,destination);
        }
        if ( Logs.MESSAGES.isTraceEnabled() ) {
            Logs.MESSAGES.trace("ChannelCoordinator - Sent msg:" + new UniqueId(msg.getUniqueId()) + " at " +new java.sql.Timestamp(System.currentTimeMillis())+ " to "+Arrays.toNameString(destination));
        }
    }
    

    /**
     * Starts up the channel. This can be called multiple times for individual services to start
     * The svc parameter can be the logical or value of any constants
     * @param svc int value of <BR>
     * DEFAULT - will start all services <BR>
     * MBR_RX_SEQ - starts the membership receiver <BR>
     * MBR_TX_SEQ - starts the membership broadcaster <BR>
     * SND_TX_SEQ - starts the replication transmitter<BR>
     * SND_RX_SEQ - starts the replication receiver<BR>
     * @throws ChannelException if a startup error occurs or the service is already started.
     */
    @Override
    public void start(int svc) throws ChannelException {
        this.internalStart(svc);
    }

    /**
     * Shuts down the channel. This can be called multiple times for individual services to shutdown
     * The svc parameter can be the logical or value of any constants
     * @param svc int value of <BR>
     * DEFAULT - will shutdown all services <BR>
     * MBR_RX_SEQ - stops the membership receiver <BR>
     * MBR_TX_SEQ - stops the membership broadcaster <BR>
     * SND_TX_SEQ - stops the replication transmitter<BR>
     * SND_RX_SEQ - stops the replication receiver<BR>
     * @throws ChannelException if a startup error occurs or the service is already started.
     */
    @Override
    public void stop(int svc) throws ChannelException {
        this.internalStop(svc);
    }    


    /**
     * Starts up the channel. This can be called multiple times for individual services to start
     * The svc parameter can be the logical or value of any constants
     * @param svc int value of <BR>
     * DEFAULT - will start all services <BR>
     * MBR_RX_SEQ - starts the membership receiver <BR>
     * MBR_TX_SEQ - starts the membership broadcaster <BR>
     * SND_TX_SEQ - starts the replication transmitter<BR>
     * SND_RX_SEQ - starts the replication receiver<BR>
     * @throws ChannelException if a startup error occurs or the service is already started.
     */
    protected synchronized void internalStart(int svc) throws ChannelException {
        try {
            boolean valid = false;
            //make sure we don't pass down any flags that are unrelated to the bottom layer
            svc = svc & Channel.DEFAULT;

            if (startLevel == Channel.DEFAULT) return; //we have already started up all components
            if (svc == 0 ) return;//nothing to start
            
            if (svc == (svc & startLevel)) throw new ChannelException("Channel already started for level:"+svc);

            //must start the receiver first so that we can coordinate the port it
            //listens to with the local membership settings
            if ( Channel.SND_RX_SEQ==(svc & Channel.SND_RX_SEQ) ) {
                clusterReceiver.setMessageListener(this);
                if (clusterReceiver instanceof ReceiverBase) {
                    ((ReceiverBase)clusterReceiver).setChannel(getChannel());
                }
                clusterReceiver.start();
                //synchronize, big time FIXME
                Member localMember = getChannel().getLocalMember(false);
                if (localMember instanceof StaticMember) {
                    // static member
                    StaticMember staticMember = (StaticMember)localMember;
                    staticMember.setHost(getClusterReceiver().getHost());
                    staticMember.setPort(getClusterReceiver().getPort());
                    staticMember.setSecurePort(getClusterReceiver().getSecurePort());
                } else {
                    // multicast member
                    membershipService.setLocalMemberProperties(getClusterReceiver().getHost(),
                            getClusterReceiver().getPort(),
                            getClusterReceiver().getSecurePort(),
                            getClusterReceiver().getUdpPort());
                   
                }
                valid = true;
            }
            if ( Channel.SND_TX_SEQ==(svc & Channel.SND_TX_SEQ) ) {
                if (clusterSender instanceof ReplicationTransmitter) {
                    ((ReplicationTransmitter)clusterSender).setChannel(getChannel());
                }
                valid = true;
                clusterSender.start();
            }
            
            if ( Channel.MBR_RX_SEQ==(svc & Channel.MBR_RX_SEQ) ) {
                membershipService.setMembershipListener(this);
                if (membershipService instanceof McastService) {
                    ((McastService)membershipService).setMessageListener(this);
                    ((McastService)membershipService).setChannel(getChannel());
                }
                membershipService.start(MembershipService.MBR_RX);
                valid = true;
            }
            if ( Channel.MBR_TX_SEQ==(svc & Channel.MBR_TX_SEQ) ) {
                if (membershipService instanceof McastService) {
                    ((McastService)membershipService).setChannel(getChannel());
                }
                membershipService.start(MembershipService.MBR_TX);
                valid = true;
            }
            
            if ( !valid) {
                throw new IllegalArgumentException("Invalid start level, valid levels are:SND_RX_SEQ,SND_TX_SEQ,MBR_TX_SEQ,MBR_RX_SEQ");
            }
            startLevel = (startLevel | svc);
        }catch ( ChannelException cx ) {
            throw cx;
        }catch ( Exception x ) {
            throw new ChannelException(x);
        }
    }

    /**
     * Shuts down the channel. This can be called multiple times for individual services to shutdown
     * The svc parameter can be the logical or value of any constants
     * @param svc int value of <BR>
     * DEFAULT - will shutdown all services <BR>
     * MBR_RX_SEQ - starts the membership receiver <BR>
     * MBR_TX_SEQ - starts the membership broadcaster <BR>
     * SND_TX_SEQ - starts the replication transmitter<BR>
     * SND_RX_SEQ - starts the replication receiver<BR>
     * @throws ChannelException if a startup error occurs or the service is already started.
     */
    protected synchronized void internalStop(int svc) throws ChannelException {
        try {
            //make sure we don't pass down any flags that are unrelated to the bottom layer
            svc = svc & Channel.DEFAULT;

            if (startLevel == 0) return; //we have already stopped up all components
            if (svc == 0 ) return;//nothing to stop

            boolean valid = false;
            if ( Channel.SND_RX_SEQ==(svc & Channel.SND_RX_SEQ) ) {
                clusterReceiver.stop();
                clusterReceiver.setMessageListener(null);
                valid = true;
            }
            if ( Channel.SND_TX_SEQ==(svc & Channel.SND_TX_SEQ) ) {
                clusterSender.stop();
                valid = true;
            }

            if ( Channel.MBR_RX_SEQ==(svc & Channel.MBR_RX_SEQ) ) {
                membershipService.stop(MembershipService.MBR_RX);
                membershipService.setMembershipListener(null);
                valid = true;
                
            }
            if ( Channel.MBR_TX_SEQ==(svc & Channel.MBR_TX_SEQ) ) {
                valid = true;
                membershipService.stop(MembershipService.MBR_TX);
            }            
            if ( !valid) {
                throw new IllegalArgumentException("Invalid start level, valid levels are:SND_RX_SEQ,SND_TX_SEQ,MBR_TX_SEQ,MBR_RX_SEQ");
            }

            startLevel = (startLevel & (~svc));
            setChannel(null);
        }catch ( Exception x ) {
            throw new ChannelException(x);
        } finally {
            
        }

    }
    
    @Override
    public void memberAdded(Member member){
        SenderState.getSenderState(member);
        super.memberAdded(member);
    }
    
    @Override
    public void memberDisappeared(Member member){
        SenderState.removeSenderState(member);
        super.memberDisappeared(member);
    }
    
    @Override
    public void messageReceived(ChannelMessage msg) {
        if ( Logs.MESSAGES.isTraceEnabled() ) {
            Logs.MESSAGES.trace("ChannelCoordinator - Received msg:" + new UniqueId(msg.getUniqueId()) + " at " +new java.sql.Timestamp(System.currentTimeMillis())+ " from "+msg.getAddress().getName());
        }
        super.messageReceived(msg);
    }

    public ChannelReceiver getClusterReceiver() {
        return clusterReceiver;
    }

    public ChannelSender getClusterSender() {
        return clusterSender;
    }

    public MembershipService getMembershipService() {
        return membershipService;
    }

    public void setClusterReceiver(ChannelReceiver clusterReceiver) {
        if ( clusterReceiver != null ) {
            this.clusterReceiver = clusterReceiver;
            this.clusterReceiver.setMessageListener(this);
        } else {
            if  (this.clusterReceiver!=null ) this.clusterReceiver.setMessageListener(null);
            this.clusterReceiver = null;
        }
    }

    public void setClusterSender(ChannelSender clusterSender) {
        this.clusterSender = clusterSender;
    }

    public void setMembershipService(MembershipService membershipService) {
        this.membershipService = membershipService;
        this.membershipService.setMembershipListener(this);
    }
    
    @Override
    public void heartbeat() {
        if ( clusterSender!=null ) clusterSender.heartbeat();
        super.heartbeat();
    }
    
    /**
     * has members
     */
    @Override
    public boolean hasMembers() {
        return this.getMembershipService().hasMembers();
    }

    /**
     * Get all current cluster members
     * @return all members or empty array
     */
    @Override
    public Member[] getMembers() {
        return this.getMembershipService().getMembers();
    }

    /**
     * 
     * @param mbr Member
     * @return Member
     */
    @Override
    public Member getMember(Member mbr){
        return this.getMembershipService().getMember(mbr);
    }


    /**
     * Return the member that represents this node.
     *
     * @return Member
     */
    @Override
    public Member getLocalMember(boolean incAlive) {
        return this.getMembershipService().getLocalMember(incAlive);
    }

   
}
