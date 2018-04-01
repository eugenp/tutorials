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

import java.util.HashMap;
import java.util.Map;

import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.ChannelMessage;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.UniqueId;
import org.apache.catalina.tribes.group.ChannelInterceptorBase;
import org.apache.catalina.tribes.group.InterceptorPayload;
import org.apache.catalina.tribes.util.Arrays;
import org.apache.catalina.tribes.util.UUIDGenerator;

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
public class TwoPhaseCommitInterceptor extends ChannelInterceptorBase {

    private static final byte[] START_DATA = new byte[] {113, 1, -58, 2, -34, -60, 75, -78, -101, -12, 32, -29, 32, 111, -40, 4};
    private static final byte[] END_DATA = new byte[] {54, -13, 90, 110, 47, -31, 75, -24, -81, -29, 36, 52, -58, 77, -110, 56};
    private static final org.apache.juli.logging.Log log = org.apache.juli.logging.LogFactory.getLog(TwoPhaseCommitInterceptor.class);

    protected HashMap<UniqueId, MapEntry> messages = new HashMap<UniqueId, MapEntry>();
    protected long expire = 1000 * 60; //one minute expiration
    protected boolean deepclone = true;

    @Override
    public void sendMessage(Member[] destination, ChannelMessage msg, InterceptorPayload payload) throws
        ChannelException {
        //todo, optimize, if destination.length==1, then we can do
        //msg.setOptions(msg.getOptions() & (~getOptionFlag())
        //and just send one message
        if (okToProcess(msg.getOptions()) ) {
            super.sendMessage(destination, msg, null);
            ChannelMessage confirmation = null;
            if ( deepclone ) confirmation = (ChannelMessage)msg.deepclone();
            else confirmation = (ChannelMessage)msg.clone();
            confirmation.getMessage().reset();
            UUIDGenerator.randomUUID(false,confirmation.getUniqueId(),0);
            confirmation.getMessage().append(START_DATA,0,START_DATA.length);
            confirmation.getMessage().append(msg.getUniqueId(),0,msg.getUniqueId().length);
            confirmation.getMessage().append(END_DATA,0,END_DATA.length);
            super.sendMessage(destination,confirmation,payload);
        } else {
            //turn off two phase commit
            //this wont work if the interceptor has 0 as a flag
            //since there is no flag to turn off
            //msg.setOptions(msg.getOptions() & (~getOptionFlag()));
            super.sendMessage(destination, msg, payload);
        }
    }

    @Override
    public void messageReceived(ChannelMessage msg) {
        if (okToProcess(msg.getOptions())) {
            if ( msg.getMessage().getLength() == (START_DATA.length+msg.getUniqueId().length+END_DATA.length) &&
                 Arrays.contains(msg.getMessage().getBytesDirect(),0,START_DATA,0,START_DATA.length) &&
                 Arrays.contains(msg.getMessage().getBytesDirect(),START_DATA.length+msg.getUniqueId().length,END_DATA,0,END_DATA.length) ) {
                UniqueId id = new UniqueId(msg.getMessage().getBytesDirect(),START_DATA.length,msg.getUniqueId().length);
                MapEntry original = messages.get(id);
                if ( original != null ) {
                    super.messageReceived(original.msg);
                    messages.remove(id);
                } else log.warn("Received a confirmation, but original message is missing. Id:"+Arrays.toString(id.getBytes()));
            } else {
                UniqueId id = new UniqueId(msg.getUniqueId());
                MapEntry entry = new MapEntry((ChannelMessage)msg.deepclone(),id,System.currentTimeMillis());
                messages.put(id,entry);
            }
        } else {
            super.messageReceived(msg);
        }
    }

    public boolean getDeepclone() {
        return deepclone;
    }

    public long getExpire() {
        return expire;
    }

    public void setDeepclone(boolean deepclone) {
        this.deepclone = deepclone;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    @Override
    public void heartbeat() {
        try {
            long now = System.currentTimeMillis();
            @SuppressWarnings("unchecked")
            Map.Entry<UniqueId,MapEntry>[] entries = messages.entrySet().toArray(new Map.Entry[messages.size()]);
            for (int i=0; i<entries.length; i++ ) {
                MapEntry entry = entries[i].getValue();
                if ( entry.expired(now,expire) ) {
                    if(log.isInfoEnabled())
                        log.info("Message ["+entry.id+"] has expired. Removing.");
                    messages.remove(entry.id);
                }//end if
            }
        } catch ( Exception x ) {
            log.warn("Unable to perform heartbeat on the TwoPhaseCommit interceptor.",x);
        } finally {
            super.heartbeat();
        }
    }

    public static class MapEntry {
        public ChannelMessage msg;
        public UniqueId id;
        public long timestamp;

        public MapEntry(ChannelMessage msg, UniqueId id, long timestamp) {
            this.msg = msg;
            this.id = id;
            this.timestamp = timestamp;
        }
        public boolean expired(long now, long expiration) {
            return (now - timestamp ) > expiration;
        }

    }

}