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

package org.apache.catalina.tribes;

/**
 * The MembershipListener interface is used as a callback to the
 * membership service. It has two methods that will notify the listener
 * when a member has joined the group and when a member has disappeared (crashed)
 *
 * @author Filip Hanik
 */
public interface MembershipListener {
    /**
     * A member was added to the group
     * @param member Member - the member that was added
     */
    public void memberAdded(Member member);
    
    /**
     * A member was removed from the group<br>
     * If the member left voluntarily, the Member.getCommand will contain the Member.SHUTDOWN_PAYLOAD data
     * @param member Member
     * @see Member#SHUTDOWN_PAYLOAD
     */
    public void memberDisappeared(Member member);

}