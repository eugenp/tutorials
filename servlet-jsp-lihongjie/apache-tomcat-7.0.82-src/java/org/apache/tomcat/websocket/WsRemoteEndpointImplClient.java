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
import java.util.concurrent.TimeUnit;

import javax.websocket.SendHandler;

public class WsRemoteEndpointImplClient extends WsRemoteEndpointImplBase {

    private final AsyncChannelWrapper channel;

    public WsRemoteEndpointImplClient(AsyncChannelWrapper channel) {
        this.channel = channel;
    }


    @Override
    protected boolean isMasked() {
        return true;
    }


    @Override
    protected void doWrite(SendHandler handler, ByteBuffer... data) {
        long timeout = getSendTimeout();
        if (timeout < 1) {
            timeout = Long.MAX_VALUE;

        }
        SendHandlerToCompletionHandler sh2ch =
                new SendHandlerToCompletionHandler(handler);
        try {
            channel.write(data, 0, data.length, timeout, TimeUnit.MILLISECONDS,
                    null, sh2ch);
        } catch (IllegalStateException ise) {
            sh2ch.failed(ise, null);
        }
    }

    @Override
    protected void doClose() {
        channel.close();
    }
}
