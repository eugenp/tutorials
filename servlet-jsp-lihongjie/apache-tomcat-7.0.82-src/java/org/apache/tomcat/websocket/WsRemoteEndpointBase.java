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

import java.io.IOException;
import java.nio.ByteBuffer;

import javax.websocket.RemoteEndpoint;

public abstract class WsRemoteEndpointBase implements RemoteEndpoint {

    protected final WsRemoteEndpointImplBase base;


    WsRemoteEndpointBase(WsRemoteEndpointImplBase base) {
        this.base = base;
    }


    @Override
    public final void setBatchingAllowed(boolean batchingAllowed) throws IOException {
        base.setBatchingAllowed(batchingAllowed);
    }


    @Override
    public final boolean getBatchingAllowed() {
        return base.getBatchingAllowed();
    }


    @Override
    public final void flushBatch() throws IOException {
        base.flushBatch();
    }


    @Override
    public final void sendPing(ByteBuffer applicationData) throws IOException,
            IllegalArgumentException {
        base.sendPing(applicationData);
    }


    @Override
    public final void sendPong(ByteBuffer applicationData) throws IOException,
            IllegalArgumentException {
        base.sendPong(applicationData);
    }
}
