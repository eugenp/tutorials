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
package org.apache.catalina.tribes.transport.nio;


import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.ChannelMessage;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.UniqueId;
import org.apache.catalina.tribes.io.ChannelData;
import org.apache.catalina.tribes.io.XByteBuffer;
import org.apache.catalina.tribes.transport.AbstractSender;
import org.apache.catalina.tribes.transport.MultiPointSender;
import org.apache.catalina.tribes.transport.SenderState;
import org.apache.catalina.tribes.util.Logs;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

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
public class ParallelNioSender extends AbstractSender implements MultiPointSender {

    private static final Log log = LogFactory.getLog(ParallelNioSender.class);
    protected long selectTimeout = 5000; //default 5 seconds, same as send timeout
    protected Selector selector;
    protected HashMap<Member, NioSender> nioSenders =
        new HashMap<Member, NioSender>();

    public ParallelNioSender() throws IOException {
        synchronized (Selector.class) {
            // Selector.open() isn't thread safe
            // http://bugs.sun.com/view_bug.do?bug_id=6427854
            // Affects 1.6.0_29, fixed in 1.7.0_01
            selector = Selector.open();
        }
        setConnected(true);
    }


    @Override
    public synchronized void sendMessage(Member[] destination, ChannelMessage msg) throws ChannelException {
        long start = System.currentTimeMillis();
        this.setUdpBased((msg.getOptions()&Channel.SEND_OPTIONS_UDP) == Channel.SEND_OPTIONS_UDP);
        byte[] data = XByteBuffer.createDataPackage((ChannelData)msg);
        NioSender[] senders = setupForSend(destination);
        connect(senders);
        setData(senders,data);

        int remaining = senders.length;
        ChannelException cx = null;
        try {
            //loop until complete, an error happens, or we timeout
            long delta = System.currentTimeMillis() - start;
            boolean waitForAck = (Channel.SEND_OPTIONS_USE_ACK & msg.getOptions()) == Channel.SEND_OPTIONS_USE_ACK;
            while ( (remaining>0) && (delta<getTimeout()) ) {
                try {
                    remaining -= doLoop(selectTimeout, getMaxRetryAttempts(),waitForAck,msg);
                } catch (Exception x ) {
                    if (log.isTraceEnabled()) log.trace("Error sending message", x);
                    int faulty = (cx == null)?0:cx.getFaultyMembers().length;
                    if ( cx == null ) {
                        if ( x instanceof ChannelException ) cx = (ChannelException)x;
                        else cx = new ChannelException("Parallel NIO send failed.", x);
                    } else {
                        if (x instanceof ChannelException) cx.addFaultyMember( ( (ChannelException) x).getFaultyMembers());
                    }
                    //count down the remaining on an error
                    if (faulty<cx.getFaultyMembers().length) remaining -= (cx.getFaultyMembers().length-faulty);
                }
                //bail out if all remaining senders are failing
                if ( cx != null && cx.getFaultyMembers().length == remaining ) throw cx;
                delta = System.currentTimeMillis() - start;
            }
            if ( remaining > 0 ) {
                //timeout has occurred
                ChannelException cxtimeout = new ChannelException("Operation has timed out("+getTimeout()+" ms.).");
                if ( cx==null ) cx = new ChannelException("Operation has timed out("+getTimeout()+" ms.).");
                for (int i=0; i<senders.length; i++ ) {
                    if (!senders[i].isComplete() ) cx.addFaultyMember(senders[i].getDestination(),cxtimeout);
                }
                throw cx;
            } else if ( cx != null ) {
                //there was an error
                throw cx;
            }
        } catch (Exception x ) {
            try { this.disconnect(); } catch (Exception e) {/*Ignore*/}
            if ( x instanceof ChannelException ) throw (ChannelException)x;
            else throw new ChannelException(x);
        }

    }

    private int doLoop(long selectTimeOut, int maxAttempts, boolean waitForAck, ChannelMessage msg) throws IOException, ChannelException {
        int completed = 0;
        int selectedKeys = selector.select(selectTimeOut);

        if (selectedKeys == 0) {
            return 0;
        }

        Iterator<SelectionKey> it = selector.selectedKeys().iterator();
        while (it.hasNext()) {
            SelectionKey sk = it.next();
            it.remove();
            int readyOps = sk.readyOps();
            sk.interestOps(sk.interestOps() & ~readyOps);
            NioSender sender = (NioSender) sk.attachment();
            try {
                if (sender.process(sk,waitForAck)) {
                    completed++;
                    sender.setComplete(true);
                    if ( Logs.MESSAGES.isTraceEnabled() ) {
                        Logs.MESSAGES.trace("ParallelNioSender - Sent msg:" + new UniqueId(msg.getUniqueId()) + " at " +new java.sql.Timestamp(System.currentTimeMillis())+ " to "+sender.getDestination().getName());
                    }
                    SenderState.getSenderState(sender.getDestination()).setReady();
                }//end if
            } catch (Exception x) {
                if (log.isTraceEnabled()) {
                    log.trace("Error while processing send to " + sender.getDestination().getName(), x);
                }
                SenderState state = SenderState.getSenderState(sender.getDestination());
                int attempt = sender.getAttempt()+1;
                boolean retry = (sender.getAttempt() <= maxAttempts && maxAttempts>0);
                synchronized (state) {

                    //sk.cancel();
                    if (state.isSuspect()) state.setFailing();
                    if (state.isReady()) {
                        state.setSuspect();
                        if ( retry )
                            log.warn("Member send is failing for:" + sender.getDestination().getName() +" ; Setting to suspect and retrying.");
                        else
                            log.warn("Member send is failing for:" + sender.getDestination().getName() +" ; Setting to suspect.", x);
                    }
                }
                if ( !isConnected() ) {
                    log.warn("Not retrying send for:" + sender.getDestination().getName() + "; Sender is disconnected.");
                    ChannelException cx = new ChannelException("Send failed, and sender is disconnected. Not retrying.",x);
                    cx.addFaultyMember(sender.getDestination(),x);
                    throw cx;
                }

                byte[] data = sender.getMessage();
                if ( retry ) {
                    try {
                        sender.disconnect();
                        sender.connect();
                        sender.setAttempt(attempt);
                        sender.setMessage(data);
                    }catch ( Exception ignore){
                        state.setFailing();
                    }
                } else {
                    ChannelException cx = new ChannelException("Send failed, attempt:"+sender.getAttempt()+" max:"+maxAttempts,x);
                    cx.addFaultyMember(sender.getDestination(),x);
                    throw cx;
                }//end if
            }
        }
        return completed;

    }

    private void connect(NioSender[] senders) throws ChannelException {
        ChannelException x = null;
        for (int i=0; i<senders.length; i++ ) {
            try {
                senders[i].connect();
            }catch ( IOException io ) {
                if ( x==null ) x = new ChannelException(io);
                x.addFaultyMember(senders[i].getDestination(),io);
            }
        }
        if ( x != null ) throw x;
    }

    private void setData(NioSender[] senders, byte[] data) throws ChannelException {
        ChannelException x = null;
        for (int i=0; i<senders.length; i++ ) {
            try {
                senders[i].setMessage(data);
            }catch ( IOException io ) {
                if ( x==null ) x = new ChannelException(io);
                x.addFaultyMember(senders[i].getDestination(),io);
            }
        }
        if ( x != null ) throw x;
    }


    private NioSender[] setupForSend(Member[] destination) throws ChannelException {
        ChannelException cx = null;
        NioSender[] result = new NioSender[destination.length];
        for ( int i=0; i<destination.length; i++ ) {
            NioSender sender = nioSenders.get(destination[i]);
            try {

                if (sender == null) {
                    sender = new NioSender();
                    AbstractSender.transferProperties(this, sender);
                    nioSenders.put(destination[i], sender);
                }
                sender.reset();
                sender.setDestination(destination[i]);
                sender.setSelector(selector);
                sender.setUdpBased(isUdpBased());
                result[i] = sender;
            }catch ( UnknownHostException x ) {
                if (cx == null) cx = new ChannelException("Unable to setup NioSender.", x);
                cx.addFaultyMember(destination[i], x);
            }
        }
        if ( cx != null ) throw cx;
        else return result;
    }

    @Override
    public void connect() {
        //do nothing, we connect on demand
        setConnected(true);
    }


    private synchronized void close() throws ChannelException  {
        ChannelException x = null;
        Object[] members = nioSenders.keySet().toArray();
        for (int i=0; i<members.length; i++ ) {
            Member mbr = (Member)members[i];
            try {
                NioSender sender = nioSenders.get(mbr);
                sender.disconnect();
            }catch ( Exception e ) {
                if ( x == null ) x = new ChannelException(e);
                x.addFaultyMember(mbr,e);
            }
            nioSenders.remove(mbr);
        }
        if ( x != null ) throw x;
    }

    @Override
    public void add(Member member) {
        // NOOP
    }

    @Override
    public void remove(Member member) {
        //disconnect senders
        NioSender sender = nioSenders.remove(member);
        if ( sender != null ) sender.disconnect();
    }


    @Override
    public synchronized void disconnect() {
        setConnected(false);
        try {close(); }catch (Exception x){/*Ignore*/}
    }

    @Override
    public void finalize() throws Throwable {
        try {disconnect(); }catch ( Exception e){/*Ignore*/}
        try {
            selector.close();
        }catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("Failed to close selector", e);
            }
        }
        super.finalize();
    }

    @Override
    public boolean keepalive() {
        boolean result = false;
        for ( Iterator<Entry<Member, NioSender>> i = nioSenders.entrySet().iterator(); i.hasNext();) {
            Map.Entry<Member, NioSender> entry = i.next();
            NioSender sender = entry.getValue();
            if ( sender.keepalive() ) {
                //nioSenders.remove(entry.getKey());
                i.remove();
                result = true;
            } else {
                try {
                    sender.read(null);
                }catch ( IOException x ) {
                    sender.disconnect();
                    sender.reset();
                    //nioSenders.remove(entry.getKey());
                    i.remove();
                    result = true;
                }catch ( Exception x ) {
                    log.warn("Error during keepalive test for sender:"+sender,x);
                }
            }
        }
        //clean up any cancelled keys
        if ( result ) try { selector.selectNow(); }catch (Exception e){/*Ignore*/}
        return result;
    }

}