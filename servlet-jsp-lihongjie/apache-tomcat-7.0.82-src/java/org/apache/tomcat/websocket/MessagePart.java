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

import javax.websocket.SendHandler;

class MessagePart {
    private final boolean fin;
    private final int rsv;
    private final byte opCode;
    private final ByteBuffer payload;
    private final SendHandler intermediateHandler;
    private volatile SendHandler endHandler;

    public MessagePart( boolean fin, int rsv, byte opCode, ByteBuffer payload,
            SendHandler intermediateHandler, SendHandler endHandler) {
        this.fin = fin;
        this.rsv = rsv;
        this.opCode = opCode;
        this.payload = payload;
        this.intermediateHandler = intermediateHandler;
        this.endHandler = endHandler;
    }


    public boolean isFin() {
        return fin;
    }


    public int getRsv() {
        return rsv;
    }


    public byte getOpCode() {
        return opCode;
    }


    public ByteBuffer getPayload() {
        return payload;
    }


    public SendHandler getIntermediateHandler() {
        return intermediateHandler;
    }


    public SendHandler getEndHandler() {
        return endHandler;
    }

    public void setEndHandler(SendHandler endHandler) {
        this.endHandler = endHandler;
    }
}


