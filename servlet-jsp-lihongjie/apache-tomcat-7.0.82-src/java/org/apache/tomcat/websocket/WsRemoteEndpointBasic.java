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
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;

import javax.websocket.EncodeException;
import javax.websocket.RemoteEndpoint;

public class WsRemoteEndpointBasic extends WsRemoteEndpointBase
        implements RemoteEndpoint.Basic {

    WsRemoteEndpointBasic(WsRemoteEndpointImplBase base) {
        super(base);
    }


    @Override
    public void sendText(String text) throws IOException {
        base.sendString(text);
    }


    @Override
    public void sendBinary(ByteBuffer data) throws IOException {
        base.sendBytes(data);
    }


    @Override
    public void sendText(String fragment, boolean isLast) throws IOException {
        base.sendPartialString(fragment, isLast);
    }


    @Override
    public void sendBinary(ByteBuffer partialByte, boolean isLast)
            throws IOException {
        base.sendPartialBytes(partialByte, isLast);
    }


    @Override
    public OutputStream getSendStream() throws IOException {
        return base.getSendStream();
    }


    @Override
    public Writer getSendWriter() throws IOException {
        return base.getSendWriter();
    }


    @Override
    public void sendObject(Object o) throws IOException, EncodeException {
        base.sendObject(o);
    }
}
