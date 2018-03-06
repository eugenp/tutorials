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
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.tomcat.util.res.StringManager;

/**
 * NIO based character decoder.
 */
public class B2CConverter {

    private static final StringManager sm =
        StringManager.getManager(Constants.Package);

    private static final Map<String, Charset> encodingToCharsetCache =
        new HashMap<String, Charset>();

    public static final Charset ISO_8859_1;
    public static final Charset UTF_8;

    // Protected so unit tests can use it
    protected static final int LEFTOVER_SIZE = 9;

    static {
        for (Charset charset: Charset.availableCharsets().values()) {
            encodingToCharsetCache.put(
                    charset.name().toLowerCase(Locale.ENGLISH), charset);
            for (String alias : charset.aliases()) {
                encodingToCharsetCache.put(
                        alias.toLowerCase(Locale.ENGLISH), charset);
            }
        }
        Charset iso88591 = null;
        Charset utf8 = null;
        try {
            iso88591 = getCharset("ISO-8859-1");
            utf8 = getCharset("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // Impossible. All JVMs must support these.
            e.printStackTrace();
        }
        ISO_8859_1 = iso88591;
        UTF_8 = utf8;
    }

    public static Charset getCharset(String enc)
            throws UnsupportedEncodingException {

        // Encoding names should all be ASCII
        String lowerCaseEnc = enc.toLowerCase(Locale.ENGLISH);

        return getCharsetLower(lowerCaseEnc);
    }

    /**
     * Only to be used when it is known that the encoding name is in lower case.
     */
    public static Charset getCharsetLower(String lowerCaseEnc)
            throws UnsupportedEncodingException {

        Charset charset = encodingToCharsetCache.get(lowerCaseEnc);

        if (charset == null) {
            // Pre-population of the cache means this must be invalid
            throw new UnsupportedEncodingException(
                    sm.getString("b2cConverter.unknownEncoding", lowerCaseEnc));
        }
        return charset;
    }

    private final CharsetDecoder decoder;
    private ByteBuffer bb = null;
    private CharBuffer cb = null;

    /**
     * Leftover buffer used for incomplete characters.
     */
    private final ByteBuffer leftovers;

    public B2CConverter(String encoding) throws IOException {
        this(encoding, false);
    }

    public B2CConverter(String encoding, boolean replaceOnError)
            throws IOException {
        byte[] left = new byte[LEFTOVER_SIZE];
        leftovers = ByteBuffer.wrap(left);
        CodingErrorAction action;
        if (replaceOnError) {
            action = CodingErrorAction.REPLACE;
        } else {
            action = CodingErrorAction.REPORT;
        }
        Charset charset = getCharset(encoding);
        // Special case. Use the Apache Harmony based UTF-8 decoder because it
        // - a) rejects invalid sequences that the JVM decoder does not
        // - b) fails faster for some invalid sequences
        if (charset.equals(UTF_8)) {
            decoder = new Utf8Decoder();
        } else {
            decoder = charset.newDecoder();
        }
        decoder.onMalformedInput(action);
        decoder.onUnmappableCharacter(action);
    }

    /** 
     * Reset the decoder state.
     */
    public void recycle() {
        decoder.reset();
        leftovers.position(0);
    }

    /**
     * Convert the given bytes to characters.
     * 
     * @param bc byte input
     * @param cc char output
     * @param endOfInput    Is this all of the available data
     */
    public void convert(ByteChunk bc, CharChunk cc, boolean endOfInput)
            throws IOException {
        if ((bb == null) || (bb.array() != bc.getBuffer())) {
            // Create a new byte buffer if anything changed
            bb = ByteBuffer.wrap(bc.getBuffer(), bc.getStart(), bc.getLength());
        } else {
            // Initialize the byte buffer
            bb.limit(bc.getEnd());
            bb.position(bc.getStart());
        }
        if ((cb == null) || (cb.array() != cc.getBuffer())) {
            // Create a new char buffer if anything changed
            cb = CharBuffer.wrap(cc.getBuffer(), cc.getEnd(), 
                    cc.getBuffer().length - cc.getEnd());
        } else {
            // Initialize the char buffer
            cb.limit(cc.getBuffer().length);
            cb.position(cc.getEnd());
        }
        CoderResult result = null;
        // Parse leftover if any are present
        if (leftovers.position() > 0) {
            int pos = cb.position();
            // Loop until one char is decoded or there is a decoder error
            do {
                leftovers.put(bc.substractB());
                leftovers.flip();
                result = decoder.decode(leftovers, cb, endOfInput);
                leftovers.position(leftovers.limit());
                leftovers.limit(leftovers.array().length);
            } while (result.isUnderflow() && (cb.position() == pos));
            if (result.isError() || result.isMalformed()) {
                result.throwException();
            }
            bb.position(bc.getStart());
            leftovers.position(0);
        }
        // Do the decoding and get the results into the byte chunk and the char
        // chunk
        result = decoder.decode(bb, cb, endOfInput);
        if (result.isError() || result.isMalformed()) {
            result.throwException();
        } else if (result.isOverflow()) {
            // Propagate current positions to the byte chunk and char chunk, if
            // this continues the char buffer will get resized
            bc.setOffset(bb.position());
            cc.setEnd(cb.position());
        } else if (result.isUnderflow()) {
            // Propagate current positions to the byte chunk and char chunk
            bc.setOffset(bb.position());
            cc.setEnd(cb.position());
            // Put leftovers in the leftovers byte buffer
            if (bc.getLength() > 0) {
                leftovers.limit(leftovers.array().length);
                leftovers.position(bc.getLength());
                bc.substract(leftovers.array(), 0, bc.getLength());
            }
        }
    }
}
