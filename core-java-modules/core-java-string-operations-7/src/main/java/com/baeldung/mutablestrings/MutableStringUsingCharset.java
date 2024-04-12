package com.baeldung.mutablestrings;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.util.concurrent.atomic.AtomicReference;

public class MutableStringUsingCharset {

    private final AtomicReference<CharBuffer> cbRef = new AtomicReference<>();
    private final Charset myCharset = new Charset("mycharset", null) {
        @Override
        public boolean contains(Charset cs) {
            return false;
        }

        @Override
        public CharsetDecoder newDecoder() {
            return new CharsetDecoder(this, 1.0f, 1.0f) {
                @Override
                protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out) {
                    cbRef.set(out);
                    while (in.remaining() > 0) {
                        out.append((char) in.get());
                    }
                    return CoderResult.UNDERFLOW;
                }
            };
        }

        @Override
        public CharsetEncoder newEncoder() {
            CharsetEncoder cd = new CharsetEncoder(this, 1.0f, 1.0f) {
                @Override
                protected CoderResult encodeLoop(CharBuffer in, ByteBuffer out) {
                    while (in.hasRemaining()) {
                        if (!out.hasRemaining()) {
                            return CoderResult.OVERFLOW;
                        }
                        char currentChar = in.get();
                        if (currentChar > 127) {
                            return CoderResult.unmappableForLength(1);
                        }
                        out.put((byte) currentChar);
                    }
                    return CoderResult.UNDERFLOW;
                }
            };
            return cd;
        }
    };

    public String createModifiableString(String s) {
        return new String(s.getBytes(), myCharset);
    }

    public void modifyString() {
        CharBuffer cb = cbRef.get();
        cb.position(0);
        cb.put("xyz");
    }
}
