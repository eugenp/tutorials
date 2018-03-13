/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tomcat.websocket;

import java.nio.ByteBuffer;
import java.util.concurrent.Future;

import javax.websocket.RemoteEndpoint;
import javax.websocket.SendHandler;

public class WsRemoteEndpointAsync extends WsRemoteEndpointBase
        implements RemoteEndpoint.Async {

    WsRemoteEndpointAsync(WsRemoteEndpointImplBase base) {
        super(base);
    }


    @Override
    public long getSendTimeout() {
        return base.getSendTimeout();
    }


    @Override
    public void setSendTimeout(long timeout) {
        base.setSendTimeout(timeout);
    }


    @Override
    public void sendText(String text, SendHandler completion) {
        base.sendStringByCompletion(text, completion);
    }


    @Override
    public Future<Void> sendText(String text) {
        return base.sendStringByFuture(text);
    }


    @Override
    public Future<Void> sendBinary(ByteBuffer data) {
        return base.sendBytesByFuture(data);
    }


    @Override
    public void sendBinary(ByteBuffer data, SendHandler completion) {
        base.sendBytesByCompletion(data, completion);
    }


    @Override
    public Future<Void> sendObject(Object obj) {
        return base.sendObjectByFuture(obj);
    }


    @Override
    public void sendObject(Object obj, SendHandler completion) {
        base.sendObjectByCompletion(obj, completion);
    }
}
