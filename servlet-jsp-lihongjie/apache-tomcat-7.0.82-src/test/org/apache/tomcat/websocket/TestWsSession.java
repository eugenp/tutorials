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

import java.nio.ByteBuffer;

import org.junit.Assert;
import org.junit.Test;

public class TestWsSession {

    @Test
    public void testAppendCloseReasonWithTruncation01() {
        doTestAppendCloseReasonWithTruncation(100);
    }


    @Test
    public void testAppendCloseReasonWithTruncation02() {
        doTestAppendCloseReasonWithTruncation(119);
    }


    @Test
    public void testAppendCloseReasonWithTruncation03() {
        doTestAppendCloseReasonWithTruncation(120);
    }


    @Test
    public void testAppendCloseReasonWithTruncation04() {
        doTestAppendCloseReasonWithTruncation(121);
    }


    @Test
    public void testAppendCloseReasonWithTruncation05() {
        doTestAppendCloseReasonWithTruncation(122);
    }


    @Test
    public void testAppendCloseReasonWithTruncation06() {
        doTestAppendCloseReasonWithTruncation(123);
    }


    @Test
    public void testAppendCloseReasonWithTruncation07() {
        doTestAppendCloseReasonWithTruncation(124);
    }


    @Test
    public void testAppendCloseReasonWithTruncation08() {
        doTestAppendCloseReasonWithTruncation(125);
    }


    @Test
    public void testAppendCloseReasonWithTruncation09() {
        doTestAppendCloseReasonWithTruncation(150);
    }


    private void doTestAppendCloseReasonWithTruncation(int reasonLength) {
        StringBuilder reason = new StringBuilder(reasonLength);
        for (int i = 0; i < reasonLength; i++) {
            reason.append('a');
        }

        ByteBuffer buf = ByteBuffer.allocate(256);

        WsSession.appendCloseReasonWithTruncation(buf, reason.toString());

        // Check the position and contents
        if (reasonLength <= 123) {
            Assert.assertEquals(reasonLength, buf.position());
            for (int i = 0; i < reasonLength; i++) {
                Assert.assertEquals('a', buf.get(i));
            }
        } else {
            // Must have been truncated
            Assert.assertEquals(123, buf.position());
            for (int i = 0; i < 120; i++) {
                Assert.assertEquals('a', buf.get(i));
            }
            Assert.assertEquals(0xE2, buf.get(120) & 0xFF);
            Assert.assertEquals(0x80, buf.get(121) & 0xFF);
            Assert.assertEquals(0xA6, buf.get(122) & 0xFF);
        }
    }
}
