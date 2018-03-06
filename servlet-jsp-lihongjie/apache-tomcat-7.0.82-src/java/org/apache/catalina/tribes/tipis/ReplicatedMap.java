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
package org.apache.catalina.tribes.tipis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.ChannelException.FaultyMember;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.RemoteProcessException;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * All-to-all replication for a hash map implementation. Each node in the cluster will carry an identical 
 * copy of the map.<br><br>
 * This map implementation doesn't have a background thread running to replicate changes.
 * If you do have changes without invoking put/remove then you need to invoke one of the following methods:
 * <ul>
 * <li><code>replicate(Object,boolean)</code> - replicates only the object that belongs to the key</li>
 * <li><code>replicate(boolean)</code> - Scans the entire map for changes and replicates data</li>
 *  </ul>
 * the <code>boolean</code> value in the <code>replicate</code> method used to decide
 * whether to only replicate objects that implement the <code>ReplicatedMapEntry</code> interface
 * or to replicate all objects. If an object doesn't implement the <code>ReplicatedMapEntry</code> interface
 * each time the object gets replicated the entire object gets serialized, hence a call to <code>replicate(true)</code>
 * will replicate all objects in this map that are using this node as primary.
 *
 * <br><br><b>REMEMBER TO CALL <code>breakdown()</code> or <code>finalize()</code>
 * when you are done with the map to avoid memory leaks.</b><br><br>
 * TODO implement periodic sync/transfer thread<br>
 * TODO memberDisappeared, should do nothing except change map membership
 *       by default it relocates the primary objects
 *
 * @author Filip Hanik
 * @version 1.0
 */
public class ReplicatedMap<K,V> extends AbstractReplicatedMap<K,V> {

    private static final long serialVersionUID = 1L;

    private final Log log = LogFactory.getLog(ReplicatedMap.class);

    //--------------------------------------------------------------------------
    //              CONSTRUCTORS / DESTRUCTORS
    //--------------------------------------------------------------------------
    /**
     * Creates a new map
     * @param channel The channel to use for communication
     * @param timeout long - timeout for RPC messages
     * @param mapContextName String - unique name for this map, to allow multiple maps per channel
     * @param initialCapacity int - the size of this map, see HashMap
     * @param loadFactor float - load factor, see HashMap
     */
    public ReplicatedMap(MapOwner owner, Channel channel, long timeout, String mapContextName, int initialCapacity,float loadFactor, ClassLoader[] cls) {
        super(owner,channel, timeout, mapContextName, initialCapacity, loadFactor, Channel.SEND_OPTIONS_DEFAULT, cls, true);
    }

    /**
     * Creates a new map
     * @param channel The channel to use for communication
     * @param timeout long - timeout for RPC messages
     * @param mapContextName String - unique name for this map, to allow multiple maps per channel
     * @param initialCapacity int - the size of this map, see HashMap
     */
    public ReplicatedMap(MapOwner owner, Channel channel, long timeout, String mapContextName, int initialCapacity, ClassLoader[] cls) {
        super(owner,channel, timeout, mapContextName, initialCapacity, AbstractReplicatedMap.DEFAULT_LOAD_FACTOR,Channel.SEND_OPTIONS_DEFAULT, cls, true);
    }

    /**
     * Creates a new map
     * @param channel The channel to use for communication
     * @param timeout long - timeout for RPC messages
     * @param mapContextName String - unique name for this map, to allow multiple maps per channel
     */
    public ReplicatedMap(MapOwner owner, Channel channel, long timeout, String mapContextName, ClassLoader[] cls) {
        super(owner, channel, timeout, mapContextName,AbstractReplicatedMap.DEFAULT_INITIAL_CAPACITY, AbstractReplicatedMap.DEFAULT_LOAD_FACTOR, Channel.SEND_OPTIONS_DEFAULT, cls, true);
    }

    /**
     * Creates a new map
     * @param channel The channel to use for communication
     * @param timeout long - timeout for RPC messages
     * @param mapContextName String - unique name for this map, to allow multiple maps per channel
     * @param terminate boolean - Flag for whether to terminate this map that failed to start.
     */
    public ReplicatedMap(MapOwner owner, Channel channel, long timeout, String mapContextName, ClassLoader[] cls, boolean terminate) {
        super(owner, channel, timeout, mapContextName,AbstractReplicatedMap.DEFAULT_INITIAL_CAPACITY,
                AbstractReplicatedMap.DEFAULT_LOAD_FACTOR, Channel.SEND_OPTIONS_DEFAULT, cls, terminate);
    }

//------------------------------------------------------------------------------
//              METHODS TO OVERRIDE
//------------------------------------------------------------------------------
    @Override
    protected int getStateMessageType() {
        return AbstractReplicatedMap.MapMessage.MSG_STATE_COPY;
    }

    @Override
    protected int getReplicateMessageType() {
        return AbstractReplicatedMap.MapMessage.MSG_COPY;
    }

    /**
     * publish info about a map pair (key/value) to other nodes in the cluster
     * @param key Object
     * @param value Object
     * @return Member - the backup node
     * @throws ChannelException
     */
    @Override
    protected Member[] publishEntryInfo(Object key, Object value) throws ChannelException {
        if  (! (key instanceof Serializable && value instanceof Serializable)  ) return new Member[0];
        //select a backup node
        Member[] backup = getMapMembers();

        if (backup == null || backup.length == 0) return null;

        try {
            
            //publish the data out to all nodes
            MapMessage msg = new MapMessage(getMapContextName(), MapMessage.MSG_COPY, false,
                    (Serializable) key, (Serializable) value, null,channel.getLocalMember(false), backup);

            getChannel().send(backup, msg, getChannelSendOptions());
        } catch (ChannelException e) {
            FaultyMember[] faultyMembers = e.getFaultyMembers();
            if (faultyMembers.length == 0) throw e;
            ArrayList<Member> faulty = new ArrayList<Member>();
            for (FaultyMember faultyMember : faultyMembers) {
                if (!(faultyMember.getCause() instanceof RemoteProcessException)) {
                    faulty.add(faultyMember.getMember());
                }
            }
            Member[] realFaultyMembers = faulty.toArray(new Member[faulty.size()]);
            if (realFaultyMembers.length != 0) {
                backup = excludeFromSet(realFaultyMembers, backup);
                if (backup.length == 0) {
                    throw e;
                } else {
                    if (log.isWarnEnabled()) {
                        log.warn("Unable to replicate backup key:" + key
                                + ". Success nodes:" + Arrays.toString(backup)
                                + ". Failed nodes:" + Arrays.toString(realFaultyMembers), e);
                    }
                }
            }
        }
        return backup;
    }

    @Override
    public void memberDisappeared(Member member) {
        boolean removed = false;
        synchronized (mapMembers) {
            removed = (mapMembers.remove(member) != null );
            if (!removed) {
                if (log.isDebugEnabled()) log.debug("Member["+member+"] disappeared, but was not present in the map.");
                return; //the member was not part of our map.
            }
        }
        if (log.isInfoEnabled())
            log.info("Member["+member+"] disappeared. Related map entries will be relocated to the new node.");
        long start = System.currentTimeMillis();
        Iterator<Map.Entry<K,MapEntry<K,V>>> i = innerMap.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry<K,MapEntry<K,V>> e = i.next();
            MapEntry<K,V> entry = innerMap.get(e.getKey());
            if (entry==null) continue;
            if (entry.isPrimary()) {
                try {
                    Member[] backup = getMapMembers();
                    if (backup.length > 0) {
                        MapMessage msg = new MapMessage(getMapContextName(), MapMessage.MSG_NOTIFY_MAPMEMBER,false,
                                (Serializable)entry.getKey(),null,null,channel.getLocalMember(false),backup);
                        getChannel().send(backup, msg, getChannelSendOptions());
                    }
                    entry.setBackupNodes(backup);
                    entry.setPrimary(channel.getLocalMember(false));
                } catch (ChannelException x) {
                    log.error("Unable to relocate[" + entry.getKey() + "] to a new backup node", x);
                }
            } else if (member.equals(entry.getPrimary())) {
                entry.setPrimary(null);
            }

            if ( entry.getPrimary() == null &&
                        entry.isCopy() &&
                        entry.getBackupNodes()!=null &&
                        entry.getBackupNodes().length > 0 &&
                        entry.getBackupNodes()[0].equals(channel.getLocalMember(false)) ) {
                try {
                    entry.setPrimary(channel.getLocalMember(false));
                    entry.setBackup(false);
                    entry.setProxy(false);
                    entry.setCopy(false);
                    Member[] backup = getMapMembers();
                    if (backup.length > 0) {
                        MapMessage msg = new MapMessage(getMapContextName(), MapMessage.MSG_NOTIFY_MAPMEMBER,false,
                                (Serializable)entry.getKey(),null,null,channel.getLocalMember(false),backup);
                        getChannel().send(backup, msg, getChannelSendOptions());
                    }
                    entry.setBackupNodes(backup);
                    if ( mapOwner!=null ) mapOwner.objectMadePrimay(entry.getKey(),entry.getValue());

                } catch (ChannelException x) {
                    log.error("Unable to relocate[" + entry.getKey() + "] to a new backup node", x);
                }
            }

        } //while
        long complete = System.currentTimeMillis() - start;
        if (log.isInfoEnabled()) log.info("Relocation of map entries was complete in " + complete + " ms.");
    }

    @Override
    public void mapMemberAdded(Member member) {
        if ( member.equals(getChannel().getLocalMember(false)) ) return;
        boolean memberAdded = false;
        synchronized (mapMembers) {
            if (!mapMembers.containsKey(member) ) {
                mapMembers.put(member, Long.valueOf(System.currentTimeMillis()));
                memberAdded = true;
            }
        }
        if ( memberAdded ) {
            synchronized (stateMutex) {
                Member[] backup = getMapMembers();
                Iterator<Map.Entry<K,MapEntry<K,V>>> i = innerMap.entrySet().iterator();
                while (i.hasNext()) {
                    Map.Entry<K,MapEntry<K,V>> e = i.next();
                    MapEntry<K,V> entry = innerMap.get(e.getKey());
                    if ( entry == null ) continue;
                    if (entry.isPrimary() && !inSet(member,entry.getBackupNodes())) {
                        entry.setBackupNodes(backup);
                    }
                }
            }
        }
    }

}