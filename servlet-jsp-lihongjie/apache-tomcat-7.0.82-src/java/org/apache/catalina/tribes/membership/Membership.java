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


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.catalina.tribes.Member;

/**
 * A <b>membership</b> implementation using simple multicast.
 * This is the representation of a multicast membership.
 * This class is responsible for maintaining a list of active cluster nodes in the cluster.
 * If a node fails to send out a heartbeat, the node will be dismissed.
 *
 * @author Filip Hanik
 * @author Peter Rossbach
 */
public class Membership implements Cloneable {

    protected static final MemberImpl[] EMPTY_MEMBERS = new MemberImpl[0];
    
    private final Object membersLock = new Object();
    
    /**
     * The name of this membership, has to be the same as the name for the local
     * member
     */
    protected MemberImpl local;
    
    /**
     * A map of all the members in the cluster.
     */
    protected HashMap<MemberImpl, MbrEntry> map = new HashMap<MemberImpl, MbrEntry>();
    
    /**
     * A list of all the members in the cluster.
     */
    protected MemberImpl[] members = EMPTY_MEMBERS;
    
    /**
      * sort members by alive time
      */
    protected Comparator<Member> memberComparator = new MemberComparator();

    @Override
    public Object clone() {
        synchronized (membersLock) {
            Membership clone = new Membership(local, memberComparator);
            @SuppressWarnings("unchecked") // map is correct type already
            final HashMap<MemberImpl, MbrEntry> tmpclone = (HashMap<MemberImpl, MbrEntry>) map.clone();
            clone.map = tmpclone;
            clone.members = new MemberImpl[members.length];
            System.arraycopy(members,0,clone.members,0,members.length);
            return clone;
        }
    }

    /**
     * Constructs a new membership
     * @param local - has to be the name of the local member. Used to filter the local member from the cluster membership
     * @param includeLocal - TBA
     */
    public Membership(MemberImpl local, boolean includeLocal) {
        this.local = local;
        if ( includeLocal ) addMember(local);
    }

    public Membership(MemberImpl local) {
        this(local,false);
    }

    public Membership(MemberImpl local, Comparator<Member> comp) {
        this(local,comp,false);
    }

    public Membership(MemberImpl local, Comparator<Member> comp, boolean includeLocal) {
        this(local,includeLocal);
        this.memberComparator = comp;
    }
    /**
     * Reset the membership and start over fresh.
     * Ie, delete all the members and wait for them to ping again and join this membership
     */
    public synchronized void reset() {
        map.clear();
        members = EMPTY_MEMBERS ;
    }

    /**
     * Notify the membership that this member has announced itself.
     *
     * @param member - the member that just pinged us
     * @return - true if this member is new to the cluster, false otherwise.<br>
     * - false if this member is the local member or updated.
     */
    public synchronized boolean memberAlive(MemberImpl member) {
        boolean result = false;
        //ignore ourselves
        if (  member.equals(local) ) return result;

        //return true if the membership has changed
        MbrEntry entry = map.get(member);
        if ( entry == null ) {
            entry = addMember(member);
            result = true;
       } else {
            //update the member alive time
            MemberImpl updateMember = entry.getMember() ;
            if(updateMember.getMemberAliveTime() != member.getMemberAliveTime()) {
                //update fields that can change
                updateMember.setMemberAliveTime(member.getMemberAliveTime());
                updateMember.setPayload(member.getPayload());
                updateMember.setCommand(member.getCommand());
                Arrays.sort(members, memberComparator);
            }
        }
        entry.accessed();
        return result;
    }

    /**
     * Add a member to this component and sort array with memberComparator
     * @param member The member to add
     */
    public synchronized MbrEntry addMember(MemberImpl member) {
      synchronized (membersLock) {
          MbrEntry entry = new MbrEntry(member);
          if (!map.containsKey(member) ) {
              map.put(member, entry);
              MemberImpl results[] = new MemberImpl[members.length + 1];
              for (int i = 0; i < members.length; i++) results[i] = members[i];
              results[members.length] = member;
              members = results;
              Arrays.sort(members, memberComparator);
          }
          return entry;
      }
    }
    
    /**
     * Remove a member from this component.
     * 
     * @param member The member to remove
     */
    public void removeMember(MemberImpl member) {
        map.remove(member);
        synchronized (membersLock) {
            int n = -1;
            for (int i = 0; i < members.length; i++) {
                if (members[i] == member || members[i].equals(member)) {
                    n = i;
                    break;
                }
            }
            if (n < 0) return;
            MemberImpl results[] = new MemberImpl[members.length - 1];
            int j = 0;
            for (int i = 0; i < members.length; i++) {
                if (i != n)
                    results[j++] = members[i];
            }
            members = results;
        }
    }
    
    /**
     * Runs a refresh cycle and returns a list of members that has expired.
     * This also removes the members from the membership, in such a way that
     * getMembers() = getMembers() - expire()
     * @param maxtime - the max time a member can remain unannounced before it is considered dead.
     * @return the list of expired members
     */
    public synchronized MemberImpl[] expire(long maxtime) {
        if(!hasMembers() )
           return EMPTY_MEMBERS;
       
        ArrayList<MemberImpl> list = null;
        Iterator<MbrEntry> i = map.values().iterator();
        while(i.hasNext()) {
            MbrEntry entry = i.next();
            if( entry.hasExpired(maxtime) ) {
                if(list == null) // only need a list when members are expired (smaller gc)
                    list = new java.util.ArrayList<MemberImpl>();
                list.add(entry.getMember());
            }
        }
        
        if(list != null) {
            MemberImpl[] result = new MemberImpl[list.size()];
            list.toArray(result);
            for( int j=0; j<result.length; j++) {
                removeMember(result[j]);
            }
            return result;
        } else {
            return EMPTY_MEMBERS ;
        }
    }

    /**
     * Returning that service has members or not
     */
    public boolean hasMembers() {
        return members.length > 0 ;
    }
    
    
    public MemberImpl getMember(Member mbr) {
        if(hasMembers()) {
            MemberImpl result = null;
            for ( int i=0; i<this.members.length && result==null; i++ ) {
                if ( members[i].equals(mbr) ) result = members[i];
            }//for
            return result;
        } else {
            return null;
        }
    }
    
    public boolean contains(Member mbr) { 
        return getMember(mbr)!=null;
    }
 
    /**
     * Returning a list of all the members in the membership
     * We not need a copy: add and remove generate new arrays.
     */
    public MemberImpl[] getMembers() {
        if(hasMembers()) {
            return members;
        } else {
            return EMPTY_MEMBERS;
        }
    }

    /**
     * get a copy from all member entries
     */
    protected synchronized MbrEntry[] getMemberEntries()
    {
        MbrEntry[] result = new MbrEntry[map.size()];
        Iterator<Map.Entry<MemberImpl,MbrEntry>> i = map.entrySet().iterator();
        int pos = 0;
        while ( i.hasNext() )
            result[pos++] = i.next().getValue();
        return result;
    }
    
    // --------------------------------------------- Inner Class

    private static class MemberComparator implements Comparator<Member>,
            Serializable {

        private static final long serialVersionUID = 1L;

        @Override
        public int compare(Member m1, Member m2) {
            //longer alive time, means sort first
            long result = m2.getMemberAliveTime() - m1.getMemberAliveTime();
            if (result < 0)
                return -1;
            else if (result == 0)
                return 0;
            else
                return 1;
        }
    }
    
    /**
     * Inner class that represents a member entry
     */
    protected static class MbrEntry
    {

        protected MemberImpl mbr;
        protected long lastHeardFrom;

        public MbrEntry(MemberImpl mbr) {
           this.mbr = mbr;
        }

        /**
         * Indicate that this member has been accessed.
         */
        public void accessed(){
           lastHeardFrom = System.currentTimeMillis();
        }

        /**
         * Return the actual Member object
         */
        public MemberImpl getMember() {
            return mbr;
        }

        /**
         * Check if this dude has expired
         * @param maxtime The time threshold
         */
        public boolean hasExpired(long maxtime) {
            long delta = System.currentTimeMillis() - lastHeardFrom;
            return delta > maxtime;
        }
    }
}
