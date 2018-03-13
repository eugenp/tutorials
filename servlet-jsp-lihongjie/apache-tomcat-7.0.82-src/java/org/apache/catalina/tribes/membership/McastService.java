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

package org.apache.catalina.tribes.membership;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.Properties;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.ChannelMessage;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.MembershipListener;
import org.apache.catalina.tribes.MembershipService;
import org.apache.catalina.tribes.MessageListener;
import org.apache.catalina.tribes.io.ChannelData;
import org.apache.catalina.tribes.io.XByteBuffer;
import org.apache.catalina.tribes.util.Arrays;
import org.apache.catalina.tribes.util.StringManager;
import org.apache.catalina.tribes.util.UUIDGenerator;

/**
 * A <b>membership</b> implementation using simple multicast.
 * This is the representation of a multicast membership service.
 * This class is responsible for maintaining a list of active cluster nodes in the cluster.
 * If a node fails to send out a heartbeat, the node will be dismissed.
 *
 * @author Filip Hanik
 */
public class McastService implements MembershipService,MembershipListener,MessageListener {

    private static final org.apache.juli.logging.Log log =
        org.apache.juli.logging.LogFactory.getLog( McastService.class );

    /**
     * The string manager for this package.
     */
    protected static final StringManager sm = StringManager.getManager(Constants.Package);

    /**
     * The descriptive information about this implementation.
     */
    private static final String info = "McastService/2.1";

    /**
     * The implementation specific properties
     */
    protected Properties properties = new Properties();
    /**
     * A handle to the actual low level implementation
     */
    protected McastServiceImpl impl;
    /**
     * A membership listener delegate (should be the cluster :)
     */
    protected MembershipListener listener;
    /**
     * A message listener delegate for broadcasts
     */
    protected MessageListener msglistener;
    /**
     * The local member
     */
    protected MemberImpl localMember ;
    private int mcastSoTimeout;
    private int mcastTTL;

    protected byte[] payload;

    protected byte[] domain;

    private Channel channel;

    /**
     * Create a membership service.
     */
    public McastService() {
        //default values
        properties.setProperty("mcastPort","45564");
        properties.setProperty("mcastAddress","228.0.0.4");
        properties.setProperty("memberDropTime","3000");
        properties.setProperty("mcastFrequency","500");

    }

    /**
     * Return descriptive information about this implementation and the
     * corresponding version number, in the format
     * <code>&lt;description&gt;/&lt;version&gt;</code>.
     */
    public String getInfo() {
        return (info);
    }

    /**
     *
     * @param properties
     * <br>All are required<br>
     * 1. mcastPort - the port to listen to<BR>
     * 2. mcastAddress - the mcast group address<BR>
     * 4. bindAddress - the bind address if any - only one that can be null<BR>
     * 5. memberDropTime - the time a member is gone before it is considered gone.<BR>
     * 6. mcastFrequency - the frequency of sending messages<BR>
     * 7. tcpListenPort - the port this member listens to<BR>
     * 8. tcpListenHost - the bind address of this member<BR>
     * @exception java.lang.IllegalArgumentException if a property is missing.
     */
    @Override
    public void setProperties(Properties properties) {
        hasProperty(properties,"mcastPort");
        hasProperty(properties,"mcastAddress");
        hasProperty(properties,"memberDropTime");
        hasProperty(properties,"mcastFrequency");
        hasProperty(properties,"tcpListenPort");
        hasProperty(properties,"tcpListenHost");
        this.properties = properties;
    }

    /**
     * Return the properties, see setProperties
     */
    @Override
    public Properties getProperties() {
        return properties;
    }

    /**
     * Return the local member name
     */
    public String getLocalMemberName() {
        return localMember.toString() ;
    }

    /**
     * Return the local member
     */
    @Override
    public Member getLocalMember(boolean alive) {
        if ( alive && localMember != null && impl != null) localMember.setMemberAliveTime(System.currentTimeMillis()-impl.getServiceStartTime());
        return localMember;
    }

    /**
     * Sets the local member properties for broadcasting
     */
    @Override
    public void setLocalMemberProperties(String listenHost, int listenPort, int securePort, int udpPort) {
        properties.setProperty("tcpListenHost",listenHost);
        properties.setProperty("tcpListenPort",String.valueOf(listenPort));
        properties.setProperty("udpListenPort",String.valueOf(udpPort));
        properties.setProperty("tcpSecurePort",String.valueOf(securePort));
        try {
            if (localMember != null) {
                localMember.setHostname(listenHost);
                localMember.setPort(listenPort);
            } else {
                localMember = new MemberImpl(listenHost, listenPort, 0);
                localMember.setUniqueId(UUIDGenerator.randomUUID(true));
                localMember.setPayload(getPayload());
                localMember.setDomain(getDomain());
                localMember.setLocal(true);
            }
            localMember.setSecurePort(securePort);
            localMember.setUdpPort(udpPort);
            localMember.getData(true, true);
        }catch ( IOException x ) {
            throw new IllegalArgumentException(x);
        }
    }

    public void setAddress(String addr) {
        properties.setProperty("mcastAddress", addr);
    }

    /**
     * @deprecated use setAddress
     * @param addr String
     */
    @Deprecated
    public void setMcastAddr(String addr) {
        setAddress(addr);
    }

    public String getAddress() {
        return properties.getProperty("mcastAddress");
    }

    /**
     * @deprecated use getAddress
     * @return String
     */
    @Deprecated
    public String getMcastAddr() {
        return getAddress();
    }

    public void setMcastBindAddress(String bindaddr) {
        setBind(bindaddr);
    }

    public void setBind(String bindaddr) {
        properties.setProperty("mcastBindAddress", bindaddr);
    }
    /**
     * @deprecated use getBind
     * @return String
     */
    @Deprecated
    public String getMcastBindAddress() {
        return getBind();
    }

    public String getBind() {
        return properties.getProperty("mcastBindAddress");
    }

    /**
     * @deprecated use setPort
     * @param port int
     */
    @Deprecated
    public void setMcastPort(int port) {
        setPort(port);
    }

    public void setPort(int port) {
        properties.setProperty("mcastPort", String.valueOf(port));
    }

    public void setRecoveryCounter(int recoveryCounter) {
        properties.setProperty("recoveryCounter", String.valueOf(recoveryCounter));
    }
    
    public int getRecoveryCounter(){
        String p = properties.getProperty("recoveryCounter");
        if(p != null){
            return Integer.parseInt(p);
        }
        return -1;
    }

    public void setRecoveryEnabled(boolean recoveryEnabled) {
        properties.setProperty("recoveryEnabled", String.valueOf(recoveryEnabled));
    }
    
    public boolean getRecoveryEnabled() {
        String p = properties.getProperty("recoveryEnabled");
        if(p != null){
            return Boolean.parseBoolean(p);
        }
        return false;
    }

    public void setRecoverySleepTime(long recoverySleepTime) {
        properties.setProperty("recoverySleepTime", String.valueOf(recoverySleepTime));
    }
    
    public long getRecoverySleepTime(){
        String p = properties.getProperty("recoverySleepTime");
        if(p != null){
            return Long.parseLong(p);
        }
        return -1;
    }

    public void setLocalLoopbackDisabled(boolean localLoopbackDisabled) {
        properties.setProperty("localLoopbackDisabled",String.valueOf(localLoopbackDisabled));
    }
    
    public boolean getLocalLoopbackDisabled(boolean localLoopbackDisabled) {
        String p = properties.getProperty("localLoopbackDisabled");
        if(p != null){
            return Boolean.parseBoolean(p);
        }
        return false;
    }

    /**
     * @deprecated use getPort()
     * @return int
     */
    @Deprecated
    public int getMcastPort() {
        return getPort();
    }
    public int getPort() {
        String p = properties.getProperty("mcastPort");
        return Integer.parseInt(p);
    }

    /**
     * @deprecated use setFrequency
     * @param time long
     */
    @Deprecated
    public void setMcastFrequency(long time) {
        setFrequency(time);
    }

    public void setFrequency(long time) {
        properties.setProperty("mcastFrequency", String.valueOf(time));
    }

    /**
     * @deprecated use getFrequency
     * @return long
     */
    @Deprecated
    public long getMcastFrequency() {
        return getFrequency();
    }

    public long getFrequency() {
        String p = properties.getProperty("mcastFrequency");
        return Long.parseLong(p);
    }

    public void setMcastDropTime(long time) {
        setDropTime(time);
    }
    public void setDropTime(long time) {
        properties.setProperty("memberDropTime", String.valueOf(time));
    }

    /**
     * @deprecated use getDropTime
     * @return long
     */
    @Deprecated
    public long getMcastDropTime() {
        return getDropTime();
    }

    public long getDropTime() {
        String p = properties.getProperty("memberDropTime");
        return Long.parseLong(p);
    }

    /**
     * Check if a required property is available.
     * @param properties The set of properties
     * @param name The property to check for
     */
    protected void hasProperty(Properties properties, String name){
        if ( properties.getProperty(name)==null) throw new IllegalArgumentException("McastService:Required property \""+name+"\" is missing.");
    }

    /**
     * Start broadcasting and listening to membership pings
     * @throws java.lang.Exception if a IO error occurs
     */
    @Override
    public void start() throws java.lang.Exception {
        start(MembershipService.MBR_RX);
        start(MembershipService.MBR_TX);
    }

    @Override
    public void start(int level) throws java.lang.Exception {
        hasProperty(properties,"mcastPort");
        hasProperty(properties,"mcastAddress");
        hasProperty(properties,"memberDropTime");
        hasProperty(properties,"mcastFrequency");
        hasProperty(properties,"tcpListenPort");
        hasProperty(properties,"tcpListenHost");
        hasProperty(properties,"tcpSecurePort");
        hasProperty(properties,"udpListenPort");


        if ( impl != null ) {
            impl.start(level);
            return;
        }
        String host = getProperties().getProperty("tcpListenHost");
        int port = Integer.parseInt(getProperties().getProperty("tcpListenPort"));
        int securePort = Integer.parseInt(getProperties().getProperty("tcpSecurePort"));
        int udpPort = Integer.parseInt(getProperties().getProperty("udpListenPort"));

        if ( localMember == null ) {
            localMember = new MemberImpl(host, port, 100);
            localMember.setUniqueId(UUIDGenerator.randomUUID(true));
            localMember.setLocal(true);
        } else {
            localMember.setHostname(host);
            localMember.setPort(port);
            localMember.setMemberAliveTime(100);
        }
        localMember.setSecurePort(securePort);
        localMember.setUdpPort(udpPort);
        if ( this.payload != null ) localMember.setPayload(payload);
        if ( this.domain != null ) localMember.setDomain(domain);
        localMember.setServiceStartTime(System.currentTimeMillis());
        java.net.InetAddress bind = null;
        if ( properties.getProperty("mcastBindAddress")!= null ) {
            bind = java.net.InetAddress.getByName(properties.getProperty("mcastBindAddress"));
        }
        int ttl = -1;
        int soTimeout = -1;
        if ( properties.getProperty("mcastTTL") != null ) {
            try {
                ttl = Integer.parseInt(properties.getProperty("mcastTTL"));
            } catch ( Exception x ) {
                log.error("Unable to parse mcastTTL="+properties.getProperty("mcastTTL"),x);
            }
        }
        if ( properties.getProperty("mcastSoTimeout") != null ) {
            try {
                soTimeout = Integer.parseInt(properties.getProperty("mcastSoTimeout"));
            } catch ( Exception x ) {
                log.error("Unable to parse mcastSoTimeout="+properties.getProperty("mcastSoTimeout"),x);
            }
        }

        impl = new McastServiceImpl(localMember,Long.parseLong(properties.getProperty("mcastFrequency")),
                                    Long.parseLong(properties.getProperty("memberDropTime")),
                                    Integer.parseInt(properties.getProperty("mcastPort")),
                                    bind,
                                    java.net.InetAddress.getByName(properties.getProperty("mcastAddress")),
                                    ttl,
                                    soTimeout,
                                    this,
                                    this,
                                    Boolean.parseBoolean(properties.getProperty("localLoopbackDisabled","false")));
        String value = properties.getProperty("recoveryEnabled","true");
        boolean recEnabled = Boolean.parseBoolean(value);
        impl.setRecoveryEnabled(recEnabled);
        int recCnt = Integer.parseInt(properties.getProperty("recoveryCounter","10"));
        impl.setRecoveryCounter(recCnt);
        long recSlpTime = Long.parseLong(properties.getProperty("recoverySleepTime","5000"));
        impl.setRecoverySleepTime(recSlpTime);
        impl.setChannel(channel);

        impl.start(level);


    }


    /**
     * Stop broadcasting and listening to membership pings
     */
    @Override
    public void stop(int svc) {
        try  {
            if ( impl != null && impl.stop(svc) ) {
                impl.setChannel(null);
                impl = null;
                channel = null;
            }
        } catch ( Exception x)  {
            log.error("Unable to stop the mcast service, level:"+svc+".",x);
        }
    }


    /**
     * Return all the members by name
     */
    @Override
    public String[] getMembersByName() {
        Member[] currentMembers = getMembers();
        String [] membernames ;
        if(currentMembers != null) {
            membernames = new String[currentMembers.length];
            for (int i = 0; i < currentMembers.length; i++) {
                membernames[i] = currentMembers[i].toString() ;
            }
        } else
            membernames = new String[0] ;
        return membernames ;
    }

    /**
     * Return the member by name
     */
    @Override
    public Member findMemberByName(String name) {
        Member[] currentMembers = getMembers();
        for (int i = 0; i < currentMembers.length; i++) {
            if (name.equals(currentMembers[i].toString()))
                return currentMembers[i];
        }
        return null;
    }

    /**
     * has members?
     */
    @Override
    public boolean hasMembers() {
       if ( impl == null || impl.membership == null ) return false;
       return impl.membership.hasMembers();
    }

    @Override
    public Member getMember(Member mbr) {
        if ( impl == null || impl.membership == null ) return null;
        return impl.membership.getMember(mbr);
    }

    /**
     * Return all the members
     */
    protected static final Member[]EMPTY_MEMBERS = new Member[0];
    @Override
    public Member[] getMembers() {
        if ( impl == null || impl.membership == null ) return EMPTY_MEMBERS;
        return impl.membership.getMembers();
    }
    /**
     * Add a membership listener, this version only supports one listener per service,
     * so calling this method twice will result in only the second listener being active.
     * @param listener The listener
     */
    @Override
    public void setMembershipListener(MembershipListener listener) {
        this.listener = listener;
    }
    
    public void setMessageListener(MessageListener listener) {
        this.msglistener = listener;
    }
    
    public void removeMessageListener() {
        this.msglistener = null;
    }
    /**
     * Remove the membership listener
     */
    @Override
    public void removeMembershipListener(){
        listener = null;
    }

    @Override
    public void memberAdded(Member member) {
        if ( listener!=null ) listener.memberAdded(member);
    }

    /**
     * Callback from the impl when a new member has been received
     * @param member The member
     */
    @Override
    public void memberDisappeared(Member member)
    {
        if ( listener!=null ) listener.memberDisappeared(member);
    }
    
    @Override
    public void messageReceived(ChannelMessage msg) {
        if (msglistener!=null && msglistener.accept(msg)) msglistener.messageReceived(msg); 
    }
    
    @Override
    public boolean accept(ChannelMessage msg) {
        return true;
    }
    @Override
    public void broadcast(ChannelMessage message) throws ChannelException {
        if (impl==null || (impl.startLevel & Channel.MBR_TX_SEQ)!=Channel.MBR_TX_SEQ )
            throw new ChannelException("Multicast send is not started or enabled.");
        
        byte[] data = XByteBuffer.createDataPackage((ChannelData)message);
        if (data.length>McastServiceImpl.MAX_PACKET_SIZE) {
            throw new ChannelException("Packet length["+data.length+"] exceeds max packet size of "+McastServiceImpl.MAX_PACKET_SIZE+" bytes.");
        }
        DatagramPacket packet = new DatagramPacket(data,0,data.length);
        try {
            impl.send(false, packet);
        } catch (Exception x) {
            throw new ChannelException(x);
        }
    }

    /**
     * @deprecated use getSoTimeout
     * @return int
     */
    @Deprecated
    public int getMcastSoTimeout() {
        return getSoTimeout();
    }

    public int getSoTimeout() {
        return mcastSoTimeout;
    }

    /**
     * @deprecated use setSoTimeout
     * @param mcastSoTimeout int
     */
    @Deprecated
    public void setMcastSoTimeout(int mcastSoTimeout) {
        setSoTimeout(mcastSoTimeout);
    }

    public void setSoTimeout(int mcastSoTimeout) {
        this.mcastSoTimeout = mcastSoTimeout;
        properties.setProperty("mcastSoTimeout", String.valueOf(mcastSoTimeout));
    }

    /**
     * @deprecated use getTtl
     * @return int
     */
    @Deprecated
    public int getMcastTTL() {
        return getTtl();
    }

    public int getTtl() {
        return mcastTTL;
    }

    public byte[] getPayload() {
        return payload;
    }

    public byte[] getDomain() {
        return domain;
    }

    /**
     * @deprecated use setTtl
     * @param mcastTTL int
     */
    @Deprecated
    public void setMcastTTL(int mcastTTL) {
        setTtl(mcastTTL);
    }

    public void setTtl(int mcastTTL) {
        this.mcastTTL = mcastTTL;
        properties.setProperty("mcastTTL", String.valueOf(mcastTTL));
    }

    @Override
    public void setPayload(byte[] payload) {
        this.payload = payload;
        if ( localMember != null ) {
            localMember.setPayload(payload);
            localMember.getData(true,true);
            try {
                if (impl != null) impl.send(false);
            }catch ( Exception x ) {
                log.error("Unable to send payload update.",x);
            }
        }
    }

    @Override
    public void setDomain(byte[] domain) {
        this.domain = domain;
        if ( localMember != null ) {
            localMember.setDomain(domain);
            localMember.getData(true,true);
            try {
                if (impl != null) impl.send(false);
            }catch ( Exception x ) {
                log.error("Unable to send domain update.",x);
            }
        }
    }

    public void setDomain(String domain) {
        if ( domain == null ) return;
        if ( domain.startsWith("{") ) setDomain(Arrays.fromString(domain));
        else setDomain(Arrays.convert(domain));
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    /**
     * Simple test program
     * @param args Command-line arguments
     * @throws Exception If an error occurs
     */
    public static void main(String args[]) throws Exception {
        if(log.isInfoEnabled())
            log.info("Usage McastService hostname tcpport");
        McastService service = new McastService();
        java.util.Properties p = new java.util.Properties();
        p.setProperty("mcastPort","5555");
        p.setProperty("mcastAddress","224.10.10.10");
        p.setProperty("mcastClusterDomain","catalina");
        p.setProperty("bindAddress","localhost");
        p.setProperty("memberDropTime","3000");
        p.setProperty("mcastFrequency","500");
        p.setProperty("tcpListenPort","4000");
        p.setProperty("tcpListenHost","127.0.0.1");
        p.setProperty("tcpSecurePort","4100");
        p.setProperty("udpListenPort","4200");
        service.setProperties(p);
        service.start();
        Thread.sleep(60*1000*60);
    }
}
