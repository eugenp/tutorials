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
package org.apache.tomcat.util.buf;

import java.nio.charset.Charset;
import java.nio.charset.MalformedInputException;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

public class TestB2CConverter {

    private static final byte[] UTF16_MESSAGE =
            new byte[] {-2, -1, 0, 65, 0, 66, 0, 67};

    private static final byte[] UTF8_INVALID = new byte[] {-8, -69, -73, -77};

    private static final byte[] UTF8_PARTIAL = new byte[] {-50};

    @Test
    public void testSingleMessage() throws Exception {
        testMessages(1);
    }

    @Test
    public void testTwoMessage() throws Exception {
        testMessages(2);
    }

    @Test
    public void testManyMessage() throws Exception {
        testMessages(10);
    }

    private void testMessages(int msgCount) throws Exception {
        B2CConverter conv = new B2CConverter("UTF-16");

        ByteChunk bc = new ByteChunk();
        CharChunk cc = new CharChunk(32);


        for (int i = 0; i < msgCount; i++) {
            bc.append(UTF16_MESSAGE, 0, UTF16_MESSAGE.length);
            conv.convert(bc, cc, true);
            Assert.assertEquals("ABC", cc.toString());
            bc.recycle();
            cc.recycle();
            conv.recycle();
        }

        System.out.println(cc);
    }

    @Test
    public void testLeftoverSize() {
        float maxLeftover = 0;
        String charsetName = "UNSET";
        for (Charset charset : Charset.availableCharsets().values()) {
            float leftover;
            if (charset.name().toLowerCase(Locale.ENGLISH).startsWith("x-")) {
                // Non-standard charset that browsers won't be using
                // Likely something used internally by the JRE
                continue;
            }
            if (charset.name().equals("COMPOUND_TEXT")) {
                // Java for-internal-use-only charset
                // See:
                // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6392670
                // http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6381697
                continue;
            }
            try {
                leftover = charset.newEncoder().maxBytesPerChar();
            } catch (UnsupportedOperationException uoe) {
                // Skip it
                continue;
            }
            if (leftover > maxLeftover) {
                maxLeftover = leftover;
                charsetName = charset.name();
            }
        }
        Assert.assertTrue("Limit needs to be at least " + maxLeftover +
                " (used in charset '" + charsetName + "')",
                maxLeftover <= B2CConverter.LEFTOVER_SIZE);
    }

    // TODO Work-around bug in UTF8 decoder
    //@Test(expected=MalformedInputException.class)
    public void testBug54602a() throws Exception {
        // Check invalid input is rejected straight away
        B2CConverter conv = new B2CConverter("UTF-8");
        ByteChunk bc = new ByteChunk();
        CharChunk cc = new CharChunk();

        bc.append(UTF8_INVALID, 0, UTF8_INVALID.length);
        cc.allocate(bc.getLength(), -1);

        conv.convert(bc, cc, false);
    }

    @Test(expected=MalformedInputException.class)
    public void testBug54602b() throws Exception {
        // Check partial input is rejected
        B2CConverter conv = new B2CConverter("UTF-8");
        ByteChunk bc = new ByteChunk();
        CharChunk cc = new CharChunk();

        bc.append(UTF8_PARTIAL, 0, UTF8_PARTIAL.length);
        cc.allocate(bc.getLength(), -1);

        conv.convert(bc, cc, true);
    }

    @Test
    public void testBug54602c() throws Exception {
        // Check partial input is rejected once it is known to be all available
        B2CConverter conv = new B2CConverter("UTF-8");
        ByteChunk bc = new ByteChunk();
        CharChunk cc = new CharChunk();

        bc.append(UTF8_PARTIAL, 0, UTF8_PARTIAL.length);
        cc.allocate(bc.getLength(), -1);

        conv.convert(bc, cc, false);

        Exception e = null;
        try {
            conv.convert(bc, cc, true);
        } catch (MalformedInputException mie) {
            e = mie;
        }
        Assert.assertNotNull(e);
    }
}
