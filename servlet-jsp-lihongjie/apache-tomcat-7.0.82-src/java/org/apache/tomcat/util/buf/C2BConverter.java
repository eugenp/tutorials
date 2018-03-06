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

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

/**
 * NIO based character encoder.
 */
public final class C2BConverter {

    CharsetEncoder encoder = null;
    ByteBuffer bb = null;
    CharBuffer cb = null;

    /**
     * Leftover buffer used for multi-characters characters.
     */
    CharBuffer leftovers = null;

    public C2BConverter(String encoding) throws IOException {
        encoder = B2CConverter.getCharset(encoding).newEncoder();
        // FIXME: See if unmappable/malformed behavior configuration is needed
        //        in practice
        encoder.onUnmappableCharacter(CodingErrorAction.REPLACE)
            .onMalformedInput(CodingErrorAction.REPLACE);
        char[] left = new char[4];
        leftovers = CharBuffer.wrap(left);
    }

    /**
     * Reset the encoder state.
     */
    public void recycle() {
        encoder.reset();
        leftovers.position(0);
    }

    public boolean isUndeflow() {
        return (leftovers.position() > 0);
    }

    /**
     * Convert the given characters to bytes.
     * 
     * @param cc char input
     * @param bc byte output
     */
    public void convert(CharChunk cc, ByteChunk bc) 
            throws IOException {
        if ((bb == null) || (bb.array() != bc.getBuffer())) {
            // Create a new byte buffer if anything changed
            bb = ByteBuffer.wrap(bc.getBuffer(), bc.getEnd(), 
                    bc.getBuffer().length - bc.getEnd());
        } else {
            // Initialize the byte buffer
            bb.limit(bc.getBuffer().length);
            bb.position(bc.getEnd());
        }
        if ((cb == null) || (cb.array() != cc.getBuffer())) {
            // Create a new char buffer if anything changed
            cb = CharBuffer.wrap(cc.getBuffer(), cc.getStart(), 
                    cc.getLength());
        } else {
            // Initialize the char buffer
            cb.limit(cc.getEnd());
            cb.position(cc.getStart());
        }
        CoderResult result = null;
        // Parse leftover if any are present
        if (leftovers.position() > 0) {
            int pos = bb.position();
            // Loop until one char is encoded or there is a encoder error
            do {
                leftovers.put((char) cc.substract());
                leftovers.flip();
                result = encoder.encode(leftovers, bb, false);
                leftovers.position(leftovers.limit());
                leftovers.limit(leftovers.array().length);
            } while (result.isUnderflow() && (bb.position() == pos));
            if (result.isError() || result.isMalformed()) {
                result.throwException();
            }
            cb.position(cc.getStart());
            leftovers.position(0);
        }
        // Do the decoding and get the results into the byte chunk and the char
        // chunk
        result = encoder.encode(cb, bb, false);
        if (result.isError() || result.isMalformed()) {
            result.throwException();
        } else if (result.isOverflow()) {
            // Propagate current positions to the byte chunk and char chunk
            bc.setEnd(bb.position());
            cc.setOffset(cb.position());
        } else if (result.isUnderflow()) {
            // Propagate current positions to the byte chunk and char chunk
            bc.setEnd(bb.position());
            cc.setOffset(cb.position());
            // Put leftovers in the leftovers char buffer
            if (cc.getLength() > 0) {
                leftovers.limit(leftovers.array().length);
                leftovers.position(cc.getLength());
                cc.substract(leftovers.array(), 0, cc.getLength());
            }
        }
    }
}
