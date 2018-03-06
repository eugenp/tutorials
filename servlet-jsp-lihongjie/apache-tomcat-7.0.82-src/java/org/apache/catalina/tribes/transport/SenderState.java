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

package org.apache.catalina.tribes.transport;

import java.util.HashMap;

import org.apache.catalina.tribes.Member;


/**
 * 
 * @author Filip Hanik
 * @version 1.0
 * @since 5.5.16
 */

public class SenderState {
    
    public static final int READY = 0;
    public static final int SUSPECT = 1;
    public static final int FAILING = 2;    
    
    protected static HashMap<Member, SenderState> memberStates = new HashMap<Member, SenderState>();
    
    public static SenderState getSenderState(Member member) {
        return getSenderState(member,true);
    }

    public static SenderState getSenderState(Member member, boolean create) {
        SenderState state = memberStates.get(member);
        if ( state == null && create) {
            synchronized ( memberStates ) {
                state = memberStates.get(member);
                if ( state == null ) {
                    state = new SenderState();
                    memberStates.put(member,state);
                }
            }
        }
        return state;
    }
    
    public static void removeSenderState(Member member) {
        synchronized ( memberStates ) {
            memberStates.remove(member);
        }
    }
    

    // ----------------------------------------------------- Instance Variables

    private int state = READY;

    //  ----------------------------------------------------- Constructor

    
    private SenderState() {
        this(READY);
    }

    private SenderState(int state) {
        this.state = state;
    }
    
    /**
     * 
     * @return boolean
     */
    public boolean isSuspect() {
        return (state == SUSPECT) || (state == FAILING);
    }

    public void setSuspect() {
        state = SUSPECT;
    }
    
    public boolean isReady() {
        return state == READY;
    }
    
    public void setReady() {
        state = READY;
    }
    
    public boolean isFailing() {
        return state == FAILING;
    }
    
    public void setFailing() {
        state = FAILING;
    }
    

    //  ----------------------------------------------------- Public Properties

}
