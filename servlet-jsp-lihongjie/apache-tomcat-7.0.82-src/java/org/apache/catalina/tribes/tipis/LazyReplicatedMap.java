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

import org.apache.catalina.tribes.Channel;
import org.apache.catalina.tribes.ChannelException;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.UniqueId;
import org.apache.catalina.tribes.util.Arrays;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * A smart implementation of a stateful replicated map. uses primary/secondary backup strategy. 
 * One node is always the primary and one node is always the backup.
 * This map is synchronized across a cluster, and only has one backup member.<br>
 * A perfect usage for this map would be a session map for a session manager in a clustered environment.<br>
 * The only way to modify this list is to use the <code>put, putAll, remove</code> methods.
 * entrySet, entrySetFull, keySet, keySetFull, returns all non modifiable sets.<br><br>
 * If objects (values) in the map change without invoking <code>put()</code> or <code>remove()</code>
 * the data can be distributed using two different methods:<br>
 * <code>replicate(boolean)</code> and <code>replicate(Object, boolean)</code><br>
 * These two methods are very important two understand. The map can work with two set of value objects:<br>
 * 1. Serializable - the entire object gets serialized each time it is replicated<br>
 * 2. ReplicatedMapEntry - this interface allows for a isDirty() flag and to replicate diffs if desired.<br>
 * Implementing the <code>ReplicatedMapEntry</code> interface allows you to decide what objects 
 * get replicated and how much data gets replicated each time.<br>
 * If you implement a smart AOP mechanism to detect changes in underlying objects, you can replicate
 * only those changes by implementing the ReplicatedMapEntry interface, and return true when isDiffable()
 * is invoked.<br><br>
 * 
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
 * <br><br><b>REMEMBER TO CALL <code>breakdown()</code> or <code>finalize()</code> when you are done with the map to 
 * avoid memory leaks.<br><br>
 * TODO implement periodic sync/transfer thread
 * @author Filip Hanik
 * @version 1.0
 */
public class LazyReplicatedMap<K,V> extends AbstractReplicatedMap<K,V> {
    private static final long serialVersionUID = 1L;
    private final Log log = LogFactory.getLog(LazyReplicatedMap.class);


//------------------------------------------------------------------------------    
//              CONSTRUCTORS / DESTRUCTORS
//------------------------------------------------------------------------------   
    /**
     * Creates a new map
     * @param channel The channel to use for communication
     * @param timeout long - timeout for RPC messages
     * @param mapContextName String - unique name for this map, to allow multiple maps per channel
     * @param initialCapacity int - the size of this map, see HashMap
     * @param loadFactor float - load factor, see HashMap
     */
    public LazyReplicatedMap(MapOwner owner, Channel channel, long timeout, String mapContextName, int initialCapacity, float loadFactor, ClassLoader[] cls) {
        super(owner,channel,timeout,mapContextName,initialCapacity,loadFactor, Channel.SEND_OPTIONS_DEFAULT,cls, true);
    }

    /**
     * Creates a new map
     * @param channel The channel to use for communication
     * @param timeout long - timeout for RPC messages
     * @param mapContextName String - unique name for this map, to allow multiple maps per channel
     * @param initialCapacity int - the size of this map, see HashMap
     */
    public LazyReplicatedMap(MapOwner owner, Channel channel, long timeout, String mapContextName, int initialCapacity, ClassLoader[] cls) {
        super(owner, channel,timeout,mapContextName,initialCapacity, AbstractReplicatedMap.DEFAULT_LOAD_FACTOR, Channel.SEND_OPTIONS_DEFAULT, cls, true);
    }

    /**
     * Creates a new map
     * @param channel The channel to use for communication
     * @param timeout long - timeout for RPC messages
     * @param mapContextName String - unique name for this map, to allow multiple maps per channel
     */
    public LazyReplicatedMap(MapOwner owner, Channel channel, long timeout, String mapContextName, ClassLoader[] cls) {
        super(owner, channel,timeout,mapContextName, AbstractReplicatedMap.DEFAULT_INITIAL_CAPACITY,AbstractReplicatedMap.DEFAULT_LOAD_FACTOR,Channel.SEND_OPTIONS_DEFAULT, cls, true);
    }

    /**
     * Creates a new map
     * @param channel The channel to use for communication
     * @param timeout long - timeout for RPC messages
     * @param mapContextName String - unique name for this map, to allow multiple maps per channel
     * @param terminate boolean - Flag for whether to terminate this map that failed to start.
     */
    public LazyReplicatedMap(MapOwner owner, Channel channel, long timeout, String mapContextName, ClassLoader[] cls, boolean terminate) {
        super(owner, channel,timeout,mapContextName, AbstractReplicatedMap.DEFAULT_INITIAL_CAPACITY,
                AbstractReplicatedMap.DEFAULT_LOAD_FACTOR,Channel.SEND_OPTIONS_DEFAULT, cls, terminate);
    }


//------------------------------------------------------------------------------    
//              METHODS TO OVERRIDE    
//------------------------------------------------------------------------------
    @Override
    protected int getStateMessageType() {
        return AbstractReplicatedMap.MapMessage.MSG_STATE;
    }

    @Override
    protected int getReplicateMessageType() {
        return AbstractReplicatedMap.MapMessage.MSG_BACKUP;
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
        Member[] members = getMapMembers();
        int firstIdx = getNextBackupIndex();
        int nextIdx = firstIdx;
        Member[] backup = new Member[0];
        
        //there are no backups
        if ( members.length == 0 || firstIdx == -1 ) return backup;
        
        boolean success = false;
        do {
            //select a backup node
            Member next = members[nextIdx];
            
            //increment for the next round of back up selection
            nextIdx = nextIdx + 1;
            if ( nextIdx >= members.length ) nextIdx = 0;
            
            if (next == null) {
                continue;
            }
            MapMessage msg = null;
            try {
                Member[] tmpBackup = wrap(next);
                //publish the backup data to one node
                msg = new MapMessage(getMapContextName(), MapMessage.MSG_BACKUP, false,
                                     (Serializable) key, (Serializable) value, null, channel.getLocalMember(false), tmpBackup);
                if ( log.isTraceEnabled() ) 
                    log.trace("Publishing backup data:"+msg+" to: "+next.getName());
                UniqueId id = getChannel().send(tmpBackup, msg, getChannelSendOptions());
                if ( log.isTraceEnabled() )
                    log.trace("Data published:"+msg+" msg Id:"+id);
                //we published out to a backup, mark the test success
                success = true;
                backup = tmpBackup;
            }catch ( ChannelException x ) {
                log.error("Unable to replicate backup key:"+key+" to backup:"+next+". Reason:"+x.getMessage(),x);
                continue;
            }
            try {
                //publish the data out to all nodes
                Member[] proxies = excludeFromSet(backup, getMapMembers());
                if (success && proxies.length > 0 ) {
                    msg = new MapMessage(getMapContextName(), MapMessage.MSG_PROXY, false,
                                         (Serializable) key, null, null, channel.getLocalMember(false),backup);
                    if ( log.isTraceEnabled() ) 
                        log.trace("Publishing proxy data:"+msg+" to: "+Arrays.toNameString(proxies));
                    getChannel().send(proxies, msg, getChannelSendOptions());
                }
            }catch  ( ChannelException x ) {
                //log the error, but proceed, this should only happen if a node went down,
                //and if the node went down, then it can't receive the message, the others
                //should still get it.
                log.error("Unable to replicate proxy key:"+key+" to backup:"+next+". Reason:"+x.getMessage(),x);
            }
        } while ( !success && (firstIdx!=nextIdx));
        return backup;
    }


}