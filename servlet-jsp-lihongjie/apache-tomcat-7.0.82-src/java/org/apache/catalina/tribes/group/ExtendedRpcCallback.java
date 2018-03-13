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

import java.io.Serializable;

import org.apache.catalina.tribes.Member;
/**
 * Extension to the {@link RpcCallback} interface. Allows a RPC messenger to get a confirmation if the reply
 * was sent successfully to the original sender. 
 * @author fhanik
 *
 */
public interface ExtendedRpcCallback extends RpcCallback {
    
    /**
     * The reply failed. 
     * @param request - the original message that requested the reply
     * @param response - the reply message to the original message
     * @param sender - the sender requested that reply
     * @param reason - the reason the reply failed
     */
    public void replyFailed(Serializable request, Serializable response, Member sender, Exception reason);
    
    /**
     * The reply succeeded 
     * @param request - the original message that requested the reply
     * @param response - the reply message to the original message
     * @param sender - the sender requested that reply
     */
    public void replySucceeded(Serializable request, Serializable response, Member sender);
}
