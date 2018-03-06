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
package org.apache.catalina.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.junit.Test;

import org.apache.tomcat.util.buf.B2CConverter;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.buf.CharChunk;

public class TesterBase64Performance {

    private static final int SIZE = 10000000;

    @SuppressWarnings("deprecation")
    @Test
    public void testDecode() throws Exception {

        List<ByteChunk> inputs = new ArrayList<ByteChunk>(SIZE);

        for (int i = 0; i < SIZE; i++) {
            String decodedString = "abc" + Integer.valueOf(i) +
                    ":abc" + Integer.valueOf(i);
            byte[] decodedBytes =
                    decodedString.getBytes(B2CConverter.ISO_8859_1);
            String encodedString =
                    DatatypeConverter.printBase64Binary(decodedBytes);
            byte[] encodedBytes =
                    encodedString.getBytes(B2CConverter.ISO_8859_1);

            ByteChunk bc = new ByteChunk(encodedBytes.length);
            bc.append(encodedBytes, 0, encodedBytes.length);

            inputs.add(bc);
        }

        long startTomcat = System.currentTimeMillis();
        for (ByteChunk bc : inputs) {
            CharChunk cc = new CharChunk(bc.getLength());
            Base64.decode(bc, cc);
        }
        long stopTomcat =  System.currentTimeMillis();
        System.out.println("Tomcat: " + (stopTomcat - startTomcat) + " ms");

        long startCodec = System.currentTimeMillis();
        for (ByteChunk bc : inputs) {
            org.apache.tomcat.util.codec.binary.Base64.decodeBase64(
                    bc.getBuffer(), bc.getOffset(), bc.getLength());
        }
        long stopCodec =  System.currentTimeMillis();
        System.out.println("Codec: " + (stopCodec - startCodec) + " ms");
    }
}
