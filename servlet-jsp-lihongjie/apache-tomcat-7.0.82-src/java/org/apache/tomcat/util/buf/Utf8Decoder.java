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
package org.apache.tomcat.util.buf;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;

/**
 * Decodes bytes to UTF-8. Extracted from Apache Harmony and modified to reject
 * code points from U+D800 to U+DFFF as per RFC3629. The standard Java decoder
 * does not reject these. It has also been modified to reject code points
 * greater than U+10FFFF which the standard Java decoder rejects but the harmony
 * one does not.
 */
public class Utf8Decoder extends CharsetDecoder {

    // The next table contains information about UTF-8 charset and
    // correspondence of 1st byte to the length of sequence
    // For information please visit http://www.ietf.org/rfc/rfc3629.txt
    //
    // Please note, o means 0, actually.
    // -------------------------------------------------------------------
    // 0 1 2 3 Value
    // -------------------------------------------------------------------
    // oxxxxxxx                            00000000 00000000 0xxxxxxx
    // 11oyyyyy 1oxxxxxx                   00000000 00000yyy yyxxxxxx
    // 111ozzzz 1oyyyyyy 1oxxxxxx          00000000 zzzzyyyy yyxxxxxx
    // 1111ouuu 1ouuzzzz 1oyyyyyy 1oxxxxxx 000uuuuu zzzzyyyy yyxxxxxx
    private static final int remainingBytes[] = {
            // 1owwwwww
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            // 11oyyyyy
            -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            // 111ozzzz
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            // 1111ouuu
            3, 3, 3, 3, 3, -1, -1, -1,
            // > 11110111
            -1, -1, -1, -1, -1, -1, -1, -1};
    private static final int remainingNumbers[] = {0, // 0 1 2 3
            4224, // (01o00000b << 6)+(1o000000b)
            401536, // (011o0000b << 12)+(1o000000b << 6)+(1o000000b)
            29892736 // (0111o000b << 18)+(1o000000b << 12)+(1o000000b <<
                     // 6)+(1o000000b)
    };
    private static final int lowerEncodingLimit[] = {-1, 0x80, 0x800, 0x10000};


    public Utf8Decoder() {
        super(B2CConverter.UTF_8, 1.0f, 1.0f);
    }


    @Override
    protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out) {
        if (in.hasArray() && out.hasArray()) {
            return decodeHasArray(in, out);
        }
        return decodeNotHasArray(in, out);
    }


    private CoderResult decodeNotHasArray(ByteBuffer in, CharBuffer out) {
        int outRemaining = out.remaining();
        int pos = in.position();
        int limit = in.limit();
        try {
            while (pos < limit) {
                if (outRemaining == 0) {
                    return CoderResult.OVERFLOW;
                }
                int jchar = in.get();
                if (jchar < 0) {
                    jchar = jchar & 0x7F;
                    int tail = remainingBytes[jchar];
                    if (tail == -1) {
                        return CoderResult.malformedForLength(1);
                    }
                    if (limit - pos < 1 + tail) {
                        // No early test for invalid sequences here as peeking
                        // at the next byte is harder
                        return CoderResult.UNDERFLOW;
                    }
                    int nextByte;
                    for (int i = 0; i < tail; i++) {
                        nextByte = in.get() & 0xFF;
                        if ((nextByte & 0xC0) != 0x80) {
                            return CoderResult.malformedForLength(1 + i);
                        }
                        jchar = (jchar << 6) + nextByte;
                    }
                    jchar -= remainingNumbers[tail];
                    if (jchar < lowerEncodingLimit[tail]) {
                        // Should have been encoded in a fewer octets
                        return CoderResult.malformedForLength(1);
                    }
                    pos += tail;
                }
                // Apache Tomcat added test
                if (jchar >= 0xD800 && jchar <= 0xDFFF) {
                    return CoderResult.unmappableForLength(3);
                }
                // Apache Tomcat added test
                if (jchar > 0x10FFFF) {
                    return CoderResult.unmappableForLength(4);
                }
                if (jchar <= 0xffff) {
                    out.put((char) jchar);
                    outRemaining--;
                } else {
                    if (outRemaining < 2) {
                        return CoderResult.OVERFLOW;
                    }
                    out.put((char) ((jchar >> 0xA) + 0xD7C0));
                    out.put((char) ((jchar & 0x3FF) + 0xDC00));
                    outRemaining -= 2;
                }
                pos++;
            }
            return CoderResult.UNDERFLOW;
        } finally {
            in.position(pos);
        }
    }


    private CoderResult decodeHasArray(ByteBuffer in, CharBuffer out) {
        int outRemaining = out.remaining();
        int pos = in.position();
        int limit = in.limit();
        final byte[] bArr = in.array();
        final char[] cArr = out.array();
        final int inIndexLimit = limit + in.arrayOffset();
        int inIndex = pos + in.arrayOffset();
        int outIndex = out.position() + out.arrayOffset();
        // if someone would change the limit in process,
        // he would face consequences
        for (; inIndex < inIndexLimit && outRemaining > 0; inIndex++) {
            int jchar = bArr[inIndex];
            if (jchar < 0) {
                jchar = jchar & 0x7F;
                // If first byte is invalid, tail will be set to -1
                int tail = remainingBytes[jchar];
                if (tail == -1) {
                    in.position(inIndex - in.arrayOffset());
                    out.position(outIndex - out.arrayOffset());
                    return CoderResult.malformedForLength(1);
                }
                // Additional checks to detect invalid sequences ASAP
                // Checks derived from Unicode 6.2, Chapter 3, Table 3-7
                // Check 2nd byte
                int tailAvailable = inIndexLimit - inIndex - 1;
                if (tailAvailable > 0) {
                    // First byte C2..DF, second byte 80..BF
                    if (jchar > 0x41 && jchar < 0x60 &&
                            (bArr[inIndex + 1] & 0xC0) != 0x80) {
                        in.position(inIndex - in.arrayOffset());
                        out.position(outIndex - out.arrayOffset());
                        return CoderResult.malformedForLength(1);
                    }
                    // First byte E0, second byte A0..BF
                    if (jchar == 0x60 && (bArr[inIndex + 1] & 0xE0) != 0xA0) {
                        in.position(inIndex - in.arrayOffset());
                        out.position(outIndex - out.arrayOffset());
                        return CoderResult.malformedForLength(1);
                    }
                    // First byte E1..EC, second byte 80..BF
                    if (jchar > 0x60 && jchar < 0x6D &&
                            (bArr[inIndex + 1] & 0xC0) != 0x80) {
                        in.position(inIndex - in.arrayOffset());
                        out.position(outIndex - out.arrayOffset());
                        return CoderResult.malformedForLength(1);
                    }
                    // First byte ED, second byte 80..9F
                    if (jchar == 0x6D && (bArr[inIndex + 1] & 0xE0) != 0x80) {
                        in.position(inIndex - in.arrayOffset());
                        out.position(outIndex - out.arrayOffset());
                        return CoderResult.malformedForLength(1);
                    }
                    // First byte EE..EF, second byte 80..BF
                    if (jchar > 0x6D && jchar < 0x70 &&
                            (bArr[inIndex + 1] & 0xC0) != 0x80) {
                        in.position(inIndex - in.arrayOffset());
                        out.position(outIndex - out.arrayOffset());
                        return CoderResult.malformedForLength(1);
                    }
                    // First byte F0, second byte 90..BF
                    if (jchar == 0x70 &&
                            ((bArr[inIndex + 1] & 0xFF) < 0x90 ||
                            (bArr[inIndex + 1] & 0xFF) > 0xBF)) {
                        in.position(inIndex - in.arrayOffset());
                        out.position(outIndex - out.arrayOffset());
                        return CoderResult.malformedForLength(1);
                    }
                    // First byte F1..F3, second byte 80..BF
                    if (jchar > 0x70 && jchar < 0x74 &&
                            (bArr[inIndex + 1] & 0xC0) != 0x80) {
                        in.position(inIndex - in.arrayOffset());
                        out.position(outIndex - out.arrayOffset());
                        return CoderResult.malformedForLength(1);
                    }
                    // First byte F4, second byte 80..8F
                    if (jchar == 0x74 &&
                            (bArr[inIndex + 1] & 0xF0) != 0x80) {
                        in.position(inIndex - in.arrayOffset());
                        out.position(outIndex - out.arrayOffset());
                        return CoderResult.malformedForLength(1);
                    }
                }
                // Check third byte if present and expected
                if (tailAvailable > 1 && tail > 1) {
                    if ((bArr[inIndex + 2] & 0xC0) != 0x80) {
                        in.position(inIndex - in.arrayOffset());
                        out.position(outIndex - out.arrayOffset());
                        return CoderResult.malformedForLength(2);
                    }
                }
                // Check fourth byte if present and expected
                if (tailAvailable > 2 && tail > 2) {
                    if ((bArr[inIndex + 3] & 0xC0) != 0x80) {
                        in.position(inIndex - in.arrayOffset());
                        out.position(outIndex - out.arrayOffset());
                        return CoderResult.malformedForLength(3);
                    }
                }
                if (tailAvailable < tail) {
                    break;
                }
                for (int i = 0; i < tail; i++) {
                    int nextByte = bArr[inIndex + i + 1] & 0xFF;
                    if ((nextByte & 0xC0) != 0x80) {
                        in.position(inIndex - in.arrayOffset());
                        out.position(outIndex - out.arrayOffset());
                        return CoderResult.malformedForLength(1 + i);
                    }
                    jchar = (jchar << 6) + nextByte;
                }
                jchar -= remainingNumbers[tail];
                if (jchar < lowerEncodingLimit[tail]) {
                    // Should have been encoded in fewer octets
                    in.position(inIndex - in.arrayOffset());
                    out.position(outIndex - out.arrayOffset());
                    return CoderResult.malformedForLength(1);
                }
                inIndex += tail;
            }
            // Apache Tomcat added test
            if (jchar >= 0xD800 && jchar <= 0xDFFF) {
                return CoderResult.unmappableForLength(3);
            }
            // Apache Tomcat added test
            if (jchar > 0x10FFFF) {
                return CoderResult.unmappableForLength(4);
            }
            if (jchar <= 0xffff) {
                cArr[outIndex++] = (char) jchar;
                outRemaining--;
            } else {
                if (outRemaining < 2) {
                    return CoderResult.OVERFLOW;
                }
                cArr[outIndex++] = (char) ((jchar >> 0xA) + 0xD7C0);
                cArr[outIndex++] = (char) ((jchar & 0x3FF) + 0xDC00);
                outRemaining -= 2;
            }
        }
        in.position(inIndex - in.arrayOffset());
        out.position(outIndex - out.arrayOffset());
        return (outRemaining == 0 && inIndex < inIndexLimit) ?
                CoderResult.OVERFLOW :
                CoderResult.UNDERFLOW;
    }
}
