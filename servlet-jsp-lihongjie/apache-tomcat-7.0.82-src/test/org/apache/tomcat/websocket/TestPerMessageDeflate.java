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
package org.apache.tomcat.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.websocket.Extension;
import javax.websocket.Extension.Parameter;

import org.junit.Test;

public class TestPerMessageDeflate {

    /*
     * This replaces StandardCharsets.UTF_8 as that requires Java 7 and this is
     * simpler than refactoring the build script to build the WebSocket unit
     * tests with Java 7.
     */
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    /*
     * https://bz.apache.org/bugzilla/show_bug.cgi?id=61491
     */
    @Test
    public void testSendEmptyMessagePartWithContextTakeover() {

        // Set up the extension using defaults
        List<Parameter> parameters = Collections.emptyList();
        List<List<Parameter>> preferences = new ArrayList<List<Parameter>>();
        preferences.add(parameters);

        PerMessageDeflate perMessageDeflate = PerMessageDeflate.negotiate(preferences, true);
        perMessageDeflate.setNext(new TesterTransformation());

        ByteBuffer bb1 = ByteBuffer.wrap("A".getBytes(UTF_8));
        MessagePart mp1 = new MessagePart(true, 0, Constants.OPCODE_TEXT, bb1, null, null);

        List<MessagePart> uncompressedParts1 = new ArrayList<MessagePart>();
        uncompressedParts1.add(mp1);
        perMessageDeflate.sendMessagePart(uncompressedParts1);

        ByteBuffer bb2 = ByteBuffer.wrap("".getBytes(UTF_8));
        MessagePart mp2 = new MessagePart(true, 0, Constants.OPCODE_TEXT, bb2, null, null);

        List<MessagePart> uncompressedParts2 = new ArrayList<MessagePart>();
        uncompressedParts2.add(mp2);
        perMessageDeflate.sendMessagePart(uncompressedParts2);
    }


    /*
     * Minimal implementation to enable other transformations to be tested.
     */
    private static class TesterTransformation implements Transformation {
        @Override
        public boolean validateRsvBits(int i) {
            return false;
        }
        @Override
        public boolean validateRsv(int rsv, byte opCode) {
            return false;
        }
        @Override
        public void setNext(Transformation t) {
        }
        @Override
        public List<MessagePart> sendMessagePart(List<MessagePart> messageParts) {
            return messageParts;
        }
        @Override
        public TransformationResult getMoreData(byte opCode, boolean fin, int rsv, ByteBuffer dest)
                throws IOException {
            return null;
        }
        @Override
        public Extension getExtensionResponse() {
            return null;
        }
        @Override
        public void close() {
        }
    }
}
