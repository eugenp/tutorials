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
package org.apache.tomcat.util.codec.binary;

import org.apache.tomcat.util.buf.HexUtils;
import org.apache.tomcat.util.codec.BinaryDecoder;
import org.apache.tomcat.util.codec.BinaryEncoder;
import org.apache.tomcat.util.codec.DecoderException;
import org.apache.tomcat.util.codec.EncoderException;

/**
 * Abstract superclass for Base-N encoders and decoders.
 *
 * <p>
 * This class is thread-safe.
 * </p>
 */
public abstract class BaseNCodec implements BinaryEncoder, BinaryDecoder {

    /**
     * Holds thread context so classes can be thread-safe.
     *
     * This class is not itself thread-safe; each thread must allocate its own copy.
     *
     * @since 1.7
     */
    static class Context {

        /**
         * Place holder for the bytes we're dealing with for our based logic.
         * Bitwise operations store and extract the encoding or decoding from this variable.
         */
        int ibitWorkArea;

        /**
         * Place holder for the bytes we're dealing with for our based logic.
         * Bitwise operations store and extract the encoding or decoding from this variable.
         */
        long lbitWorkArea;

        /**
         * Buffer for streaming.
         */
        byte[] buffer;

        /**
         * Position where next character should be written in the buffer.
         */
        int pos;

        /**
         * Position where next character should be read from the buffer.
         */
        int readPos;

        /**
         * Boolean flag to indicate the EOF has been reached. Once EOF has been reached, this object becomes useless,
         * and must be thrown away.
         */
        boolean eof;

        /**
         * Variable tracks how many characters have been written to the current line. Only used when encoding. We use
         * it to make sure each encoded line never goes beyond lineLength (if lineLength > 0).
         */
        int currentLinePos;

        /**
         * Writes to the buffer only occur after every 3/5 reads when encoding, and every 4/8 reads when decoding. This
         * variable helps track that.
         */
        int modulus;

        Context() {
        }

        /**
         * Returns a String useful for debugging (especially within a debugger.)
         *
         * @return a String useful for debugging.
         */
        @SuppressWarnings("boxing") // OK to ignore boxing here
        @Override
        public String toString() {
            return String.format("%s[buffer=%s, currentLinePos=%s, eof=%s, " +
                    "ibitWorkArea=%s, lbitWorkArea=%s, modulus=%s, pos=%s, " +
                    "readPos=%s]", this.getClass().getSimpleName(),
                    HexUtils.toHexString(buffer), currentLinePos, eof,
                    ibitWorkArea, lbitWorkArea, modulus, pos, readPos);
        }
    }

    /**
     * EOF
     *
     * @since 1.7
     */
    static final int EOF = -1;

    /**
     *  MIME chunk size per RFC 2045 section 6.8.
     *
     * <p>
     * The {@value} character limit does not count the trailing CRLF, but counts all other characters, including any
     * equal signs.
     * </p>
     *
     * @see <a href="http://www.ietf.org/rfc/rfc2045.txt">RFC 2045 section 6.8</a>
     */
    public static final int MIME_CHUNK_SIZE = 76;

    /**
     * PEM chunk size per RFC 1421 section 4.3.2.4.
     *
     * <p>
     * The {@value} character limit does not count the trailing CRLF, but counts all other characters, including any
     * equal signs.
     * </p>
     *
     * @see <a href="http://tools.ietf.org/html/rfc1421">RFC 1421 section 4.3.2.4</a>
     */
    public static final int PEM_CHUNK_SIZE = 64;

    private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;

    /**
     * Defines the default buffer size - currently {@value}
     * - must be large enough for at least one encoded block+separator
     */
    private static final int DEFAULT_BUFFER_SIZE = 128;

    /** Mask used to extract 8 bits, used in decoding bytes */
    protected static final int MASK_8BITS = 0xff;

    /**
     * Byte used to pad output.
     */
    protected static final byte PAD_DEFAULT = '='; // Allow static access to default

    protected final byte PAD = PAD_DEFAULT; // instance variable just in case it needs to vary later

    /** Number of bytes in each full block of unencoded data, e.g. 4 for Base64 and 5 for Base32 */
    private final int unencodedBlockSize;

    /** Number of bytes in each full block of encoded data, e.g. 3 for Base64 and 8 for Base32 */
    private final int encodedBlockSize;

    /**
     * Chunksize for encoding. Not used when decoding.
     * A value of zero or less implies no chunking of the encoded data.
     * Rounded down to nearest multiple of encodedBlockSize.
     */
    protected final int lineLength;

    /**
     * Size of chunk separator. Not used unless {@link #lineLength} > 0.
     */
    private final int chunkSeparatorLength;

    /**
     * Note <code>lineLength</code> is rounded down to the nearest multiple of {@link #encodedBlockSize}
     * If <code>chunkSeparatorLength</code> is zero, then chunking is disabled.
     * @param unencodedBlockSize the size of an unencoded block (e.g. Base64 = 3)
     * @param encodedBlockSize the size of an encoded block (e.g. Base64 = 4)
     * @param lineLength if &gt; 0, use chunking with a length <code>lineLength</code>
     * @param chunkSeparatorLength the chunk separator length, if relevant
     */
    protected BaseNCodec(final int unencodedBlockSize, final int encodedBlockSize,
                         final int lineLength, final int chunkSeparatorLength) {
        this.unencodedBlockSize = unencodedBlockSize;
        this.encodedBlockSize = encodedBlockSize;
        final boolean useChunking = lineLength > 0 && chunkSeparatorLength > 0;
        this.lineLength = useChunking ? (lineLength / encodedBlockSize) * encodedBlockSize : 0;
        this.chunkSeparatorLength = chunkSeparatorLength;
    }

    /**
     * Returns true if this object has buffered data for reading.
     *
     * @param context the context to be used
     * @return true if there is data still available for reading.
     */
    boolean hasData(final Context context) {  // package protected for access from I/O streams
        return context.buffer != null;
    }

    /**
     * Returns the amount of buffered data available for reading.
     *
     * @param context the context to be used
     * @return The amount of buffered data available for reading.
     */
    int available(final Context context) {  // package protected for access from I/O streams
        return context.buffer != null ? context.pos - context.readPos : 0;
    }

    /**
     * Get the default buffer size. Can be overridden.
     *
     * @return {@link #DEFAULT_BUFFER_SIZE}
     */
    protected int getDefaultBufferSize() {
        return DEFAULT_BUFFER_SIZE;
    }

    /**
     * Increases our buffer by the {@link #DEFAULT_BUFFER_RESIZE_FACTOR}.
     * @param context the context to be used
     */
    private byte[] resizeBuffer(final Context context) {
        if (context.buffer == null) {
            context.buffer = new byte[getDefaultBufferSize()];
            context.pos = 0;
            context.readPos = 0;
        } else {
            final byte[] b = new byte[context.buffer.length * DEFAULT_BUFFER_RESIZE_FACTOR];
            System.arraycopy(context.buffer, 0, b, 0, context.buffer.length);
            context.buffer = b;
        }
        return context.buffer;
    }

    /**
     * Ensure that the buffer has room for <code>size</code> bytes
     *
     * @param size minimum spare space required
     * @param context the context to be used
     * @return the buffer
     */
    protected byte[] ensureBufferSize(final int size, final Context context){
        if ((context.buffer == null) || (context.buffer.length < context.pos + size)){
            return resizeBuffer(context);
        }
        return context.buffer;
    }

    /**
     * Extracts buffered data into the provided byte[] array, starting at position bPos, up to a maximum of bAvail
     * bytes. Returns how many bytes were actually extracted.
     * <p>
     * Package protected for access from I/O streams.
     *
     * @param b
     *            byte[] array to extract the buffered data into.
     * @param bPos
     *            position in byte[] array to start extraction at.
     * @param bAvail
     *            amount of bytes we're allowed to extract. We may extract fewer (if fewer are available).
     * @param context
     *            the context to be used
     * @return The number of bytes successfully extracted into the provided byte[] array.
     */
    int readResults(final byte[] b, final int bPos, final int bAvail, final Context context) {
        if (context.buffer != null) {
            final int len = Math.min(available(context), bAvail);
            System.arraycopy(context.buffer, context.readPos, b, bPos, len);
            context.readPos += len;
            if (context.readPos >= context.pos) {
                context.buffer = null; // so hasData() will return false, and this method can return -1
            }
            return len;
        }
        return context.eof ? EOF : 0;
    }

    /**
     * Checks if a byte value is whitespace or not.
     * Whitespace is taken to mean: space, tab, CR, LF
     * @param byteToCheck
     *            the byte to check
     * @return true if byte is whitespace, false otherwise
     */
    protected static boolean isWhiteSpace(final byte byteToCheck) {
        switch (byteToCheck) {
            case ' ' :
            case '\n' :
            case '\r' :
            case '\t' :
                return true;
            default :
                return false;
        }
    }

    /**
     * Encodes an Object using the Base-N algorithm. This method is provided in order to satisfy the requirements of
     * the Encoder interface, and will throw an EncoderException if the supplied object is not of type byte[].
     *
     * @param obj
     *            Object to encode
     * @return An object (of type byte[]) containing the Base-N encoded data which corresponds to the byte[] supplied.
     * @throws EncoderException
     *             if the parameter supplied is not of type byte[]
     */
    @Override
    public Object encode(final Object obj) throws EncoderException {
        if (!(obj instanceof byte[])) {
            throw new EncoderException("Parameter supplied to Base-N encode is not a byte[]");
        }
        return encode((byte[]) obj);
    }

    /**
     * Encodes a byte[] containing binary data, into a String containing characters in the Base-N alphabet.
     * Uses UTF8 encoding.
     *
     * @param pArray
     *            a byte array containing binary data
     * @return A String containing only Base-N character data
     */
    public String encodeToString(final byte[] pArray) {
        return StringUtils.newStringUtf8(encode(pArray));
    }

    /**
     * Encodes a byte[] containing binary data, into a String containing characters in the appropriate alphabet.
     * Uses UTF8 encoding.
     *
     * @param pArray a byte array containing binary data
     * @return String containing only character data in the appropriate alphabet.
    */
    public String encodeAsString(final byte[] pArray){
        return StringUtils.newStringUtf8(encode(pArray));
    }

    /**
     * Decodes an Object using the Base-N algorithm. This method is provided in order to satisfy the requirements of
     * the Decoder interface, and will throw a DecoderException if the supplied object is not of type byte[] or String.
     *
     * @param obj
     *            Object to decode
     * @return An object (of type byte[]) containing the binary data which corresponds to the byte[] or String
     *         supplied.
     * @throws DecoderException
     *             if the parameter supplied is not of type byte[]
     */
    @Override
    public Object decode(final Object obj) throws DecoderException {
        if (obj instanceof byte[]) {
            return decode((byte[]) obj);
        } else if (obj instanceof String) {
            return decode((String) obj);
        } else {
            throw new DecoderException("Parameter supplied to Base-N decode is not a byte[] or a String");
        }
    }

    /**
     * Decodes a String containing characters in the Base-N alphabet.
     *
     * @param pArray
     *            A String containing Base-N character data
     * @return a byte array containing binary data
     */
    public byte[] decode(final String pArray) {
        return decode(StringUtils.getBytesUtf8(pArray));
    }

    /**
     * Decodes a byte[] containing characters in the Base-N alphabet.
     *
     * @param pArray
     *            A byte array containing Base-N character data
     * @return a byte array containing binary data
     */
    @Override
    public byte[] decode(final byte[] pArray) {
        return decode(pArray, 0, pArray.length);
    }

    public byte[] decode(final byte[] pArray, final int off, final int len) {
        if (pArray == null || len == 0) {
            return new byte[0];
        }
        final Context context = new Context();
        decode(pArray, off, len, context);
        decode(pArray, off, EOF, context); // Notify decoder of EOF.
        final byte[] result = new byte[context.pos];
        readResults(result, 0, result.length, context);
        return result;
    }

    /**
     * Encodes a byte[] containing binary data, into a byte[] containing characters in the alphabet.
     *
     * @param pArray
     *            a byte array containing binary data
     * @return A byte array containing only the basen alphabetic character data
     */
    @Override
    public byte[] encode(final byte[] pArray) {
        if (pArray == null || pArray.length == 0) {
            return pArray;
        }
        final Context context = new Context();
        encode(pArray, 0, pArray.length, context);
        encode(pArray, 0, EOF, context); // Notify encoder of EOF.
        final byte[] buf = new byte[context.pos - context.readPos];
        readResults(buf, 0, buf.length, context);
        return buf;
    }

    // package protected for access from I/O streams
    abstract void encode(byte[] pArray, int i, int length, Context context);

    // package protected for access from I/O streams
    abstract void decode(byte[] pArray, int i, int length, Context context);

    /**
     * Returns whether or not the <code>octet</code> is in the current alphabet.
     * Does not allow whitespace or pad.
     *
     * @param value The value to test
     *
     * @return <code>true</code> if the value is defined in the current alphabet, <code>false</code> otherwise.
     */
    protected abstract boolean isInAlphabet(byte value);

    /**
     * Tests a given byte array to see if it contains only valid characters within the alphabet.
     * The method optionally treats whitespace and pad as valid.
     *
     * @param arrayOctet byte array to test
     * @param allowWSPad if <code>true</code>, then whitespace and PAD are also allowed
     *
     * @return <code>true</code> if all bytes are valid characters in the alphabet or if the byte array is empty;
     *         <code>false</code>, otherwise
     */
    public boolean isInAlphabet(final byte[] arrayOctet, final boolean allowWSPad) {
        for (byte octet : arrayOctet) {
            if (!isInAlphabet(octet) &&
                    (!allowWSPad || (octet != PAD) && !isWhiteSpace(octet))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Tests a given String to see if it contains only valid characters within the alphabet.
     * The method treats whitespace and PAD as valid.
     *
     * @param basen String to test
     * @return <code>true</code> if all characters in the String are valid characters in the alphabet or if
     *         the String is empty; <code>false</code>, otherwise
     * @see #isInAlphabet(byte[], boolean)
     */
    public boolean isInAlphabet(final String basen) {
        return isInAlphabet(StringUtils.getBytesUtf8(basen), true);
    }

    /**
     * Tests a given byte array to see if it contains any characters within the alphabet or PAD.
     *
     * Intended for use in checking line-ending arrays
     *
     * @param arrayOctet
     *            byte array to test
     * @return <code>true</code> if any byte is a valid character in the alphabet or PAD; <code>false</code> otherwise
     */
    protected boolean containsAlphabetOrPad(final byte[] arrayOctet) {
        if (arrayOctet == null) {
            return false;
        }
        for (final byte element : arrayOctet) {
            if (PAD == element || isInAlphabet(element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the amount of space needed to encode the supplied array.
     *
     * @param pArray byte[] array which will later be encoded
     *
     * @return amount of space needed to encoded the supplied array.
     * Returns a long since a max-len array will require > Integer.MAX_VALUE
     */
    public long getEncodedLength(final byte[] pArray) {
        // Calculate non-chunked size - rounded up to allow for padding
        // cast to long is needed to avoid possibility of overflow
        long len = ((pArray.length + unencodedBlockSize-1)  / unencodedBlockSize) * (long) encodedBlockSize;
        if (lineLength > 0) { // We're using chunking
            // Round up to nearest multiple
            len += ((len + lineLength-1) / lineLength) * chunkSeparatorLength;
        }
        return len;
    }
}
