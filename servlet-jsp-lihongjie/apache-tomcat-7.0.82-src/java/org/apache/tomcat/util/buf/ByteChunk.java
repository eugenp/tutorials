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
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/*
 * In a server it is very important to be able to operate on
 * the original byte[] without converting everything to chars.
 * Some protocols are ASCII only, and some allow different
 * non-UNICODE encodings. The encoding is not known beforehand,
 * and can even change during the execution of the protocol.
 * ( for example a multipart message may have parts with different
 *  encoding )
 *
 * For HTTP it is not very clear how the encoding of RequestURI
 * and mime values can be determined, but it is a great advantage
 * to be able to parse the request without converting to string.
 */

// TODO: This class could either extend ByteBuffer, or better a ByteBuffer
// inside this way it could provide the search/etc on ByteBuffer, as a helper.

/**
 * This class is used to represent a chunk of bytes, and
 * utilities to manipulate byte[].
 *
 * The buffer can be modified and used for both input and output.
 *
 * There are 2 modes: The chunk can be associated with a sink - ByteInputChannel
 * or ByteOutputChannel, which will be used when the buffer is empty (on input)
 * or filled (on output).
 * For output, it can also grow. This operating mode is selected by calling
 * setLimit() or allocate(initial, limit) with limit != -1.
 *
 * Various search and append method are defined - similar with String and
 * StringBuffer, but operating on bytes.
 *
 * This is important because it allows processing the http headers directly on
 * the received bytes, without converting to chars and Strings until the strings
 * are needed. In addition, the charset is determined later, from headers or
 * user code.
 *
 * @author dac@sun.com
 * @author James Todd [gonzo@sun.com]
 * @author Costin Manolache
 * @author Remy Maucherat
 */
public final class ByteChunk implements Cloneable, Serializable {

    private static final long serialVersionUID = 1L;

    /** Input interface, used when the buffer is empty
     *
     * Same as java.nio.channel.ReadableByteChannel
     */
    public static interface ByteInputChannel {
        /**
         * Read new bytes ( usually the internal conversion buffer ).
         * The implementation is allowed to ignore the parameters,
         * and mutate the chunk if it wishes to implement its own buffering.
         */
        public int realReadBytes(byte cbuf[], int off, int len)
            throws IOException;
    }

    /** Same as java.nio.channel.WritableByteChannel.
     */
    public static interface ByteOutputChannel {
        /**
         * Send the bytes ( usually the internal conversion buffer ).
         * Expect 8k output if the buffer is full.
         */
        public void realWriteBytes(byte cbuf[], int off, int len)
            throws IOException;
    }

    // --------------------

    /** Default encoding used to convert to strings. It should be UTF8,
        as most standards seem to converge, but the servlet API requires
        8859_1, and this object is used mostly for servlets.
    */
    public static final Charset DEFAULT_CHARSET = B2CConverter.ISO_8859_1;

    private int hashCode=0;
    // did we compute the hashcode ?
    private boolean hasHashCode = false;

    // byte[]
    private byte[] buff;

    private int start=0;
    private int end;

    private Charset charset;

    private boolean isSet=false; // XXX

    // How much can it grow, when data is added
    private int limit=-1;

    private ByteInputChannel in = null;
    private ByteOutputChannel out = null;

    private boolean optimizedWrite=true;

    /**
     * Creates a new, uninitialized ByteChunk object.
     */
    public ByteChunk() {
        // NO-OP
    }

    public ByteChunk( int initial ) {
        allocate( initial, -1 );
    }

    /**
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public ByteChunk getClone() {
        try {
            return (ByteChunk)this.clone();
        } catch( Exception ex) {
            return null;
        }
    }

    public boolean isNull() {
        return ! isSet; // buff==null;
    }

    /**
     * Resets the message buff to an uninitialized state.
     */
    public void recycle() {
        //        buff = null;
        charset=null;
        start=0;
        end=0;
        isSet=false;
        hasHashCode = false;
    }

    public void reset() {
        buff=null;
    }

    // -------------------- Setup --------------------

    public void allocate( int initial, int limit  ) {
        if( buff==null || buff.length < initial ) {
            buff=new byte[initial];
        }
        this.limit=limit;
        start=0;
        end=0;
        isSet=true;
        hasHashCode = false;
    }

    /**
     * Sets the message bytes to the specified subarray of bytes.
     *
     * @param b the ascii bytes
     * @param off the start offset of the bytes
     * @param len the length of the bytes
     */
    public void setBytes(byte[] b, int off, int len) {
        buff = b;
        start = off;
        end = start+ len;
        isSet=true;
        hasHashCode = false;
    }

    /**
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public void setOptimizedWrite(boolean optimizedWrite) {
        this.optimizedWrite = optimizedWrite;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public Charset getCharset() {
        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }
        return charset;
    }

    /**
     * Returns the message bytes.
     */
    public byte[] getBytes() {
        return getBuffer();
    }

    /**
     * Returns the message bytes.
     */
    public byte[] getBuffer() {
        return buff;
    }

    /**
     * Returns the start offset of the bytes.
     * For output this is the end of the buffer.
     */
    public int getStart() {
        return start;
    }

    public int getOffset() {
        return start;
    }

    public void setOffset(int off) {
        if (end < off ) {
            end=off;
        }
        start=off;
    }

    /**
     * Returns the length of the bytes.
     * XXX need to clean this up
     */
    public int getLength() {
        return end-start;
    }

    /** Maximum amount of data in this buffer.
     *
     *  If -1 or not set, the buffer will grow indefinitely.
     *  Can be smaller than the current buffer size ( which will not shrink ).
     *  When the limit is reached, the buffer will be flushed ( if out is set )
     *  or throw exception.
     */
    public void setLimit(int limit) {
        this.limit=limit;
    }

    public int getLimit() {
        return limit;
    }

    /**
     * When the buffer is empty, read the data from the input channel.
     */
    public void setByteInputChannel(ByteInputChannel in) {
        this.in = in;
    }

    /** When the buffer is full, write the data to the output channel.
     *         Also used when large amount of data is appended.
     *
     *  If not set, the buffer will grow to the limit.
     */
    public void setByteOutputChannel(ByteOutputChannel out) {
        this.out=out;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd( int i ) {
        end=i;
    }

    // -------------------- Adding data to the buffer --------------------
    /** Append a char, by casting it to byte. This IS NOT intended for unicode.
     *
     * @param c
     * @throws IOException
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public void append( char c )
        throws IOException
    {
        append( (byte)c);
    }

    public void append( byte b )
        throws IOException
    {
        makeSpace( 1 );

        // couldn't make space
        if( limit >0 && end >= limit ) {
            flushBuffer();
        }
        buff[end++]=b;
    }

    public void append( ByteChunk src )
        throws IOException
    {
        append( src.getBytes(), src.getStart(), src.getLength());
    }

    /** Add data to the buffer
     */
    public void append( byte src[], int off, int len )
        throws IOException
    {
        // will grow, up to limit
        makeSpace( len );

        // if we don't have limit: makeSpace can grow as it wants
        if( limit < 0 ) {
            // assert: makeSpace made enough space
            System.arraycopy( src, off, buff, end, len );
            end+=len;
            return;
        }

        // Optimize on a common case.
        // If the buffer is empty and the source is going to fill up all the
        // space in buffer, may as well write it directly to the output,
        // and avoid an extra copy
        if ( optimizedWrite && len == limit && end == start && out != null ) {
            out.realWriteBytes( src, off, len );
            return;
        }
        // if we have limit and we're below
        if( len <= limit - end ) {
            // makeSpace will grow the buffer to the limit,
            // so we have space
            System.arraycopy( src, off, buff, end, len );
            end+=len;
            return;
        }

        // need more space than we can afford, need to flush
        // buffer

        // the buffer is already at ( or bigger than ) limit

        // We chunk the data into slices fitting in the buffer limit, although
        // if the data is written directly if it doesn't fit

        int avail=limit-end;
        System.arraycopy(src, off, buff, end, avail);
        end += avail;

        flushBuffer();

        int remain = len - avail;

        while (remain > (limit - end)) {
            out.realWriteBytes( src, (off + len) - remain, limit - end );
            remain = remain - (limit - end);
        }

        System.arraycopy(src, (off + len) - remain, buff, end, remain);
        end += remain;

    }


    // -------------------- Removing data from the buffer --------------------

    public int substract()
        throws IOException {

        if ((end - start) == 0) {
            if (in == null) {
                return -1;
            }
            int n = in.realReadBytes( buff, 0, buff.length );
            if (n < 0) {
                return -1;
            }
        }

        return (buff[start++] & 0xFF);

    }


    /**
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public int substract(ByteChunk src)
        throws IOException {

        if ((end - start) == 0) {
            if (in == null) {
                return -1;
            }
            int n = in.realReadBytes( buff, 0, buff.length );
            if (n < 0) {
                return -1;
            }
        }

        int len = getLength();
        src.append(buff, start, len);
        start = end;
        return len;

    }


    public byte substractB()
        throws IOException {

        if ((end - start) == 0) {
            if (in == null)
                return -1;
            int n = in.realReadBytes( buff, 0, buff.length );
            if (n < 0)
                return -1;
        }

        return (buff[start++]);

    }


    public int substract( byte src[], int off, int len )
        throws IOException {

        if ((end - start) == 0) {
            if (in == null) {
                return -1;
            }
            int n = in.realReadBytes( buff, 0, buff.length );
            if (n < 0) {
                return -1;
            }
        }

        int n = len;
        if (len > getLength()) {
            n = getLength();
        }
        System.arraycopy(buff, start, src, off, n);
        start += n;
        return n;

    }


    /**
     * Send the buffer to the sink. Called by append() when the limit is
     * reached. You can also call it explicitly to force the data to be written.
     *
     * @throws IOException
     */
    public void flushBuffer()
        throws IOException
    {
        //assert out!=null
        if( out==null ) {
            throw new IOException( "Buffer overflow, no sink " + limit + " " +
                                   buff.length  );
        }
        out.realWriteBytes( buff, start, end-start );
        end=start;
    }

    /**
     * Make space for len chars. If len is small, allocate a reserve space too.
     * Never grow bigger than limit.
     */
    public void makeSpace(int count) {
        byte[] tmp = null;

        int newSize;
        int desiredSize=end + count;

        // Can't grow above the limit
        if( limit > 0 &&
            desiredSize > limit) {
            desiredSize=limit;
        }

        if( buff==null ) {
            if( desiredSize < 256 )
             {
                desiredSize=256; // take a minimum
            }
            buff=new byte[desiredSize];
        }

        // limit < buf.length ( the buffer is already big )
        // or we already have space XXX
        if( desiredSize <= buff.length ) {
            return;
        }
        // grow in larger chunks
        if( desiredSize < 2 * buff.length ) {
            newSize= buff.length * 2;
            if( limit >0 &&
                newSize > limit ) {
                newSize=limit;
            }
            tmp=new byte[newSize];
        } else {
            newSize= buff.length * 2 + count ;
            if( limit > 0 &&
                newSize > limit ) {
                newSize=limit;
            }
            tmp=new byte[newSize];
        }

        System.arraycopy(buff, start, tmp, 0, end-start);
        buff = tmp;
        tmp = null;
        end=end-start;
        start=0;
    }

    // -------------------- Conversion and getters --------------------

    @Override
    public String toString() {
        if (null == buff) {
            return null;
        } else if (end-start == 0) {
            return "";
        }
        return StringCache.toString(this);
    }

    public String toStringInternal() {
        if (charset == null) {
            charset = DEFAULT_CHARSET;
        }
        // new String(byte[], int, int, Charset) takes a defensive copy of the
        // entire byte array. This is expensive if only a small subset of the
        // bytes will be used. The code below is from Apache Harmony.
        CharBuffer cb;
        cb = charset.decode(ByteBuffer.wrap(buff, start, end-start));
        return new String(cb.array(), cb.arrayOffset(), cb.length());
    }

    /**
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public int getInt()
    {
        return Ascii.parseInt(buff, start,end-start);
    }

    public long getLong() {
        return Ascii.parseLong(buff, start,end-start);
    }


    // -------------------- equals --------------------

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ByteChunk) {
            return equals((ByteChunk) obj);
        }
        return false;
    }

    /**
     * Compares the message bytes to the specified String object.
     * @param s the String to compare
     * @return true if the comparison succeeded, false otherwise
     */
    public boolean equals(String s) {
        // XXX ENCODING - this only works if encoding is UTF8-compat
        // ( ok for tomcat, where we compare ascii - header names, etc )!!!

        byte[] b = buff;
        int blen = end-start;
        if (b == null || blen != s.length()) {
            return false;
        }
        int boff = start;
        for (int i = 0; i < blen; i++) {
            if (b[boff++] != s.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compares the message bytes to the specified String object.
     * @param s the String to compare
     * @return true if the comparison succeeded, false otherwise
     */
    public boolean equalsIgnoreCase(String s) {
        byte[] b = buff;
        int blen = end-start;
        if (b == null || blen != s.length()) {
            return false;
        }
        int boff = start;
        for (int i = 0; i < blen; i++) {
            if (Ascii.toLower(b[boff++]) != Ascii.toLower(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean equals( ByteChunk bb ) {
        return equals( bb.getBytes(), bb.getStart(), bb.getLength());
    }

    public boolean equals( byte b2[], int off2, int len2) {
        byte b1[]=buff;
        if( b1==null && b2==null ) {
            return true;
        }

        int len=end-start;
        if ( len2 != len || b1==null || b2==null ) {
            return false;
        }

        int off1 = start;

        while ( len-- > 0) {
            if (b1[off1++] != b2[off2++]) {
                return false;
            }
        }
        return true;
    }

    public boolean equals( CharChunk cc ) {
        return equals( cc.getChars(), cc.getStart(), cc.getLength());
    }

    public boolean equals( char c2[], int off2, int len2) {
        // XXX works only for enc compatible with ASCII/UTF !!!
        byte b1[]=buff;
        if( c2==null && b1==null ) {
            return true;
        }

        if (b1== null || c2==null || end-start != len2 ) {
            return false;
        }
        int off1 = start;
        int len=end-start;

        while ( len-- > 0) {
            if ( (char)b1[off1++] != c2[off2++]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the message bytes starts with the specified string.
     * @param s the string
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public boolean startsWith(String s) {
        // Works only if enc==UTF
        byte[] b = buff;
        int blen = s.length();
        if (b == null || blen > end-start) {
            return false;
        }
        int boff = start;
        for (int i = 0; i < blen; i++) {
            if (b[boff++] != s.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the message bytes start with the specified byte array.
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public boolean startsWith(byte[] b2) {
        byte[] b1 = buff;
        if (b1 == null && b2 == null) {
            return true;
        }

        int len = end - start;
        if (b1 == null || b2 == null || b2.length > len) {
            return false;
        }
        for (int i = start, j = 0; i < end && j < b2.length;) {
            if (b1[i++] != b2[j++]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns true if the message bytes starts with the specified string.
     * @param s the string
     * @param pos The position
     */
    public boolean startsWithIgnoreCase(String s, int pos) {
        byte[] b = buff;
        int len = s.length();
        if (b == null || len+pos > end-start) {
            return false;
        }
        int off = start+pos;
        for (int i = 0; i < len; i++) {
            if (Ascii.toLower( b[off++] ) != Ascii.toLower( s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public int indexOf( String src, int srcOff, int srcLen, int myOff ) {
        char first=src.charAt( srcOff );

        // Look for first char
        int srcEnd = srcOff + srcLen;

        mainLoop:
        for( int i=myOff+start; i <= (end - srcLen); i++ ) {
            if( buff[i] != first ) {
                continue;
            }
            // found first char, now look for a match
            int myPos=i+1;
            for( int srcPos=srcOff + 1; srcPos< srcEnd;) {
                if( buff[myPos++] != src.charAt( srcPos++ )) {
                    continue mainLoop;
                }
            }
            return i-start; // found it
        }
        return -1;
    }

    // -------------------- Hash code  --------------------

    @Override
    public int hashCode() {
        if (hasHashCode) {
            return hashCode;
        }
        int code = 0;

        code = hash();
        hashCode = code;
        hasHashCode = true;
        return code;
    }

    // normal hash.
    public int hash() {
        return hashBytes( buff, start, end-start);
    }

    /**
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public int hashIgnoreCase() {
        return hashBytesIC( buff, start, end-start );
    }

    private static int hashBytes( byte buff[], int start, int bytesLen ) {
        int max=start+bytesLen;
        byte bb[]=buff;
        int code=0;
        for (int i = start; i < max ; i++) {
            code = code * 37 + bb[i];
        }
        return code;
    }

    private static int hashBytesIC( byte bytes[], int start,
                                    int bytesLen )
    {
        int max=start+bytesLen;
        byte bb[]=bytes;
        int code=0;
        for (int i = start; i < max ; i++) {
            code = code * 37 + Ascii.toLower(bb[i]);
        }
        return code;
    }

    /**
     * Returns the first instance of the given character in this ByteChunk
     * starting at the specified byte. If the character is not found, -1 is
     * returned.
     * <br>
     * NOTE: This only works for characters in the range 0-127.
     *
     * @param c         The character
     * @param starting  The start position
     * @return          The position of the first instance of the character or
     *                      -1 if the character is not found.
     */
    public int indexOf(char c, int starting) {
        int ret = indexOf(buff, start + starting, end, c);
        return (ret >= start) ? ret - start : -1;
    }

    /**
     * Returns the first instance of the given character in the given byte array
     * between the specified start and end.
     * <br>
     * NOTE: This only works for characters in the range 0-127.
     *
     * @param bytes The byte array to search
     * @param start The point to start searching from in the byte array
     * @param end   The point to stop searching in the byte array
     * @param c     The character to search for
     * @return      The position of the first instance of the character or -1
     *                  if the character is not found.
     */
    public static int indexOf(byte bytes[], int start, int end, char c) {
        int offset = start;

        while (offset < end) {
            byte b=bytes[offset];
            if (b == c) {
                return offset;
            }
            offset++;
        }
        return -1;
    }

    /**
     * Returns the first instance of the given byte in the byte array between
     * the specified start and end.
     *
     * @param bytes The byte array to search
     * @param start The point to start searching from in the byte array
     * @param end   The point to stop searching in the byte array
     * @param b     The byte to search for
     * @return      The position of the first instance of the byte or -1 if the
     *                  byte is not found.
     */
    public static int findByte(byte bytes[], int start, int end, byte b) {
        int offset = start;
        while (offset < end) {
            if (bytes[offset] == b) {
                return offset;
            }
            offset++;
        }
        return -1;
    }

    /**
     * Returns the first instance of any of the given bytes in the byte array
     * between the specified start and end.
     *
     * @param bytes The byte array to search
     * @param start The point to start searching from in the byte array
     * @param end   The point to stop searching in the byte array
     * @param b     The array of bytes to search for
     * @return      The position of the first instance of the byte or -1 if the
     *                  byte is not found.
     */
    public static int findBytes(byte bytes[], int start, int end, byte b[]) {
        int blen = b.length;
        int offset = start;
        while (offset < end) {
            for (int i = 0;  i < blen; i++) {
                if (bytes[offset] == b[i]) {
                    return offset;
                }
            }
            offset++;
        }
        return -1;
    }

    /**
     * Returns the first instance of any byte that is not one of the given bytes
     * in the byte array between the specified start and end.
     *
     * @param bytes The byte array to search
     * @param start The point to start searching from in the byte array
     * @param end   The point to stop searching in the byte array
     * @param b     The list of bytes to search for
     * @return      The position of the first instance a byte that is not
     *                  in the list of bytes to search for or -1 if no such byte
     *                  is found.
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public static int findNotBytes(byte bytes[], int start, int end, byte b[]) {
        int blen = b.length;
        int offset = start;
        boolean found;

        while (offset < end) {
            found = true;
            for (int i = 0; i < blen; i++) {
                if (bytes[offset] == b[i]) {
                    found=false;
                    break;
                }
            }
            if (found) {
                return offset;
            }
            offset++;
        }
        return -1;
    }


    /**
     * Convert specified String to a byte array. This ONLY WORKS for ascii, UTF
     * chars will be truncated.
     *
     * @param value to convert to byte array
     * @return the byte array value
     */
    public static final byte[] convertToBytes(String value) {
        byte[] result = new byte[value.length()];
        for (int i = 0; i < value.length(); i++) {
            result[i] = (byte) value.charAt(i);
        }
        return result;
    }
}
