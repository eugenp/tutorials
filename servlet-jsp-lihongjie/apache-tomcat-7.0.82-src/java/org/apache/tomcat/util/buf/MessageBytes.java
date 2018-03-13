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
import java.nio.charset.Charset;
import java.util.Locale;

/**
 * This class is used to represent a subarray of bytes in an HTTP message.
 * It represents all request/response elements. The byte/char conversions are
 * delayed and cached. Everything is recyclable.
 *
 * The object can represent a byte[], a char[], or a (sub) String. All
 * operations can be made in case sensitive mode or not.
 *
 * @author dac@eng.sun.com
 * @author James Todd [gonzo@eng.sun.com]
 * @author Costin Manolache
 */
public final class MessageBytes implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;

    // primary type ( whatever is set as original value )
    private int type = T_NULL;

    public static final int T_NULL = 0;
    /** getType() is T_STR if the the object used to create the MessageBytes
        was a String */
    public static final int T_STR  = 1;
    /** getType() is T_BYTES if the the object used to create the MessageBytes
        was a byte[] */
    public static final int T_BYTES = 2;
    /** getType() is T_CHARS if the the object used to create the MessageBytes
        was a char[] */
    public static final int T_CHARS = 3;

    private int hashCode=0;
    // did we computed the hashcode ?
    private boolean hasHashCode=false;

    // Internal objects to represent array + offset, and specific methods
    private final ByteChunk byteC=new ByteChunk();
    private final CharChunk charC=new CharChunk();

    // String
    private String strValue;
    // true if a String value was computed. Probably not needed,
    // strValue!=null is the same
    private boolean hasStrValue=false;

    /**
     * Creates a new, uninitialized MessageBytes object.
     * Use static newInstance() in order to allow
     *   future hooks.
     */
    private MessageBytes() {
    }

    /** Construct a new MessageBytes instance
     */
    public static MessageBytes newInstance() {
        return factory.newInstance();
    }

    /**
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public MessageBytes getClone() {
        try {
            return (MessageBytes)this.clone();
        } catch( Exception ex) {
            return null;
        }
    }

    public boolean isNull() {
        // should we check also hasStrValue ???
        return byteC.isNull() && charC.isNull() && ! hasStrValue;
        // bytes==null && strValue==null;
    }

    /**
     * Resets the message bytes to an uninitialized (NULL) state.
     */
    public void recycle() {
        type=T_NULL;
        byteC.recycle();
        charC.recycle();

        strValue=null;

        hasStrValue=false;
        hasHashCode=false;
        hasIntValue=false;
    hasLongValue=false;
    }


    /**
     * Sets the content to the specified subarray of bytes.
     *
     * @param b the bytes
     * @param off the start offset of the bytes
     * @param len the length of the bytes
     */
    public void setBytes(byte[] b, int off, int len) {
        byteC.setBytes( b, off, len );
        type=T_BYTES;
        hasStrValue=false;
        hasHashCode=false;
        hasIntValue=false;
        hasLongValue=false;
    }

    /**
     * Sets the content to be a char[]
     *
     * @param c the bytes
     * @param off the start offset of the bytes
     * @param len the length of the bytes
     */
    public void setChars( char[] c, int off, int len ) {
        charC.setChars( c, off, len );
        type=T_CHARS;
        hasStrValue=false;
        hasHashCode=false;
        hasIntValue=false;
        hasLongValue=false;
    }

    /**
     * Set the content to be a string
     */
    public void setString( String s ) {
        strValue=s;
        hasHashCode=false;
        hasIntValue=false;
        hasLongValue=false;
        if (s == null) {
            hasStrValue=false;
            type=T_NULL;
        } else {
            hasStrValue=true;
            type=T_STR;
        }
    }

    // -------------------- Conversion and getters --------------------

    /** Compute the string value
     */
    @Override
    public String toString() {
        if( hasStrValue ) {
            return strValue;
        }

        switch (type) {
        case T_CHARS:
            strValue=charC.toString();
            hasStrValue=true;
            return strValue;
        case T_BYTES:
            strValue=byteC.toString();
            hasStrValue=true;
            return strValue;
        }
        return null;
    }

    //----------------------------------------
    /** Return the type of the original content. Can be
     *  T_STR, T_BYTES, T_CHARS or T_NULL
     */
    public int getType() {
        return type;
    }

    /**
     * Returns the byte chunk, representing the byte[] and offset/length.
     * Valid only if T_BYTES or after a conversion was made.
     */
    public ByteChunk getByteChunk() {
        return byteC;
    }

    /**
     * Returns the char chunk, representing the char[] and offset/length.
     * Valid only if T_CHARS or after a conversion was made.
     */
    public CharChunk getCharChunk() {
        return charC;
    }

    /**
     * Returns the string value.
     * Valid only if T_STR or after a conversion was made.
     */
    public String getString() {
        return strValue;
    }

    /**
     * Get the Charset used for string&lt;-&gt;byte conversions.
     */
    public Charset getCharset() {
        return byteC.getCharset();
    }

    /**
     * Set the Charset used for string&lt;-&gt;byte conversions.
     */
    public void setCharset(Charset charset) {
        byteC.setCharset(charset);
    }

    /** Do a char-&gt;byte conversion.
     */
    public void toBytes() {
        if (!byteC.isNull()) {
            type=T_BYTES;
            return;
        }
        toString();
        type=T_BYTES;
        Charset charset = byteC.getCharset();
        ByteBuffer result = charset.encode(strValue);
        byteC.setBytes(result.array(), result.arrayOffset(), result.limit());
    }

    /** Convert to char[] and fill the CharChunk.
     *  XXX Not optimized - it converts to String first.
     */
    public void toChars() {
        if( ! charC.isNull() ) {
            type=T_CHARS;
            return;
        }
        // inefficient
        toString();
        type=T_CHARS;
        char cc[]=strValue.toCharArray();
        charC.setChars(cc, 0, cc.length);
    }


    /**
     * Returns the length of the original buffer.
     * Note that the length in bytes may be different from the length
     * in chars.
     */
    public int getLength() {
        if(type==T_BYTES) {
            return byteC.getLength();
        }
        if(type==T_CHARS) {
            return charC.getLength();
        }
        if(type==T_STR) {
            return strValue.length();
        }
        toString();
        if( strValue==null ) {
            return 0;
        }
        return strValue.length();
    }

    // -------------------- equals --------------------

    /**
     * Compares the message bytes to the specified String object.
     * @param s the String to compare
     * @return true if the comparison succeeded, false otherwise
     */
    public boolean equals(String s) {
        switch (type) {
        case T_STR:
            if (strValue == null) {
                return s == null;
            }
            return strValue.equals( s );
        case T_CHARS:
            return charC.equals( s );
        case T_BYTES:
            return byteC.equals( s );
        default:
            return false;
        }
    }

    /**
     * Compares the message bytes to the specified String object.
     * @param s the String to compare
     * @return true if the comparison succeeded, false otherwise
     */
    public boolean equalsIgnoreCase(String s) {
        switch (type) {
        case T_STR:
            if (strValue == null) {
                return s == null;
            }
            return strValue.equalsIgnoreCase( s );
        case T_CHARS:
            return charC.equalsIgnoreCase( s );
        case T_BYTES:
            return byteC.equalsIgnoreCase( s );
        default:
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof MessageBytes) {
            return equals((MessageBytes) obj);
        }
        return false;
    }

    public boolean equals(MessageBytes mb) {
        switch (type) {
        case T_STR:
            return mb.equals( strValue );
        }

        if( mb.type != T_CHARS &&
            mb.type!= T_BYTES ) {
            // it's a string or int/date string value
            return equals( mb.toString() );
        }

        // mb is either CHARS or BYTES.
        // this is either CHARS or BYTES
        // Deal with the 4 cases ( in fact 3, one is symmetric)

        if( mb.type == T_CHARS && type==T_CHARS ) {
            return charC.equals( mb.charC );
        }
        if( mb.type==T_BYTES && type== T_BYTES ) {
            return byteC.equals( mb.byteC );
        }
        if( mb.type== T_CHARS && type== T_BYTES ) {
            return byteC.equals( mb.charC );
        }
        if( mb.type== T_BYTES && type== T_CHARS ) {
            return mb.byteC.equals( charC );
        }
        // can't happen
        return true;
    }


    /**
     * Returns true if the message bytes starts with the specified string.
     * @param s the string
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public boolean startsWith(String s) {
        switch (type) {
        case T_STR:
            return strValue.startsWith( s );
        case T_CHARS:
            return charC.startsWith( s );
        case T_BYTES:
            return byteC.startsWith( s );
        default:
            return false;
        }
    }

    /**
     * Returns true if the message bytes starts with the specified string.
     * @param s the string
     * @param pos The start position
     */
    public boolean startsWithIgnoreCase(String s, int pos) {
        switch (type) {
        case T_STR:
            if( strValue==null ) {
                return false;
            }
            if( strValue.length() < pos + s.length() ) {
                return false;
            }

            for( int i=0; i<s.length(); i++ ) {
                if( Ascii.toLower( s.charAt( i ) ) !=
                    Ascii.toLower( strValue.charAt( pos + i ))) {
                    return false;
                }
            }
            return true;
        case T_CHARS:
            return charC.startsWithIgnoreCase( s, pos );
        case T_BYTES:
            return byteC.startsWithIgnoreCase( s, pos );
        default:
            return false;
        }
    }


    // -------------------- Hash code  --------------------
    @Override
    public  int hashCode() {
        if( hasHashCode ) {
            return hashCode;
        }
        int code = 0;

        code=hash();
        hashCode=code;
        hasHashCode=true;
        return code;
    }

    // normal hash.
    private int hash() {
        int code=0;
        switch (type) {
        case T_STR:
            // We need to use the same hash function
            for (int i = 0; i < strValue.length(); i++) {
                code = code * 37 + strValue.charAt( i );
            }
            return code;
        case T_CHARS:
            return charC.hash();
        case T_BYTES:
            return byteC.hash();
        default:
            return 0;
        }
    }

    /**
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public int indexOf(char c) {
        return indexOf( c, 0);
    }

    // Inefficient initial implementation. Will be replaced on the next
    // round of tune-up
    public int indexOf(String s, int starting) {
        toString();
        return strValue.indexOf( s, starting );
    }

    // Inefficient initial implementation. Will be replaced on the next
    // round of tune-up
    public int indexOf(String s) {
        return indexOf( s, 0 );
    }

    public int indexOfIgnoreCase(String s, int starting) {
        toString();
        String upper=strValue.toUpperCase(Locale.ENGLISH);
        String sU=s.toUpperCase(Locale.ENGLISH);
        return upper.indexOf( sU, starting );
    }

    /**
     * Returns true if the message bytes starts with the specified string.
     * @param c the character
     * @param starting The start position
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public int indexOf(char c, int starting) {
        switch (type) {
        case T_STR:
            return strValue.indexOf( c, starting );
        case T_CHARS:
            return charC.indexOf( c, starting);
        case T_BYTES:
            return byteC.indexOf( c, starting );
        default:
            return -1;
        }
    }

    /** Copy the src into this MessageBytes, allocating more space if
     *  needed
     */
    public void duplicate( MessageBytes src ) throws IOException
    {
        switch( src.getType() ) {
        case MessageBytes.T_BYTES:
            type=T_BYTES;
            ByteChunk bc=src.getByteChunk();
            byteC.allocate( 2 * bc.getLength(), -1 );
            byteC.append( bc );
            break;
        case MessageBytes.T_CHARS:
            type=T_CHARS;
            CharChunk cc=src.getCharChunk();
            charC.allocate( 2 * cc.getLength(), -1 );
            charC.append( cc );
            break;
        case MessageBytes.T_STR:
            type=T_STR;
            String sc=src.getString();
            this.setString( sc );
            break;
        }
        setCharset(src.getCharset());
    }

    // -------------------- Deprecated code --------------------
    // efficient int, long and date
    // XXX used only for headers - shouldn't be
    // stored here.
    private int intValue;
    private boolean hasIntValue=false;
    private long longValue;
    private boolean hasLongValue=false;

    /**
     * Set the buffer to the representation of an int
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public void setInt(int i) {
        byteC.allocate(16, 32);
        int current = i;
        byte[] buf = byteC.getBuffer();
        int start = 0;
        int end = 0;
        if (i == 0) {
            buf[end++] = (byte) '0';
        }
        if (i < 0) {
            current = -i;
            buf[end++] = (byte) '-';
        }
        while (current > 0) {
            int digit = current % 10;
            current = current / 10;
            buf[end++] = HexUtils.getHex(digit);
        }
        byteC.setOffset(0);
        byteC.setEnd(end);
        // Inverting buffer
        end--;
        if (i < 0) {
            start++;
        }
        while (end > start) {
            byte temp = buf[start];
            buf[start] = buf[end];
            buf[end] = temp;
            start++;
            end--;
        }
        intValue=i;
        hasStrValue=false;
        hasHashCode=false;
        hasIntValue=true;
        hasLongValue=false;
        type=T_BYTES;
    }

    /** Set the buffer to the representation of an long
     */
    public void setLong(long l) {
        byteC.allocate(32, 64);
        long current = l;
        byte[] buf = byteC.getBuffer();
        int start = 0;
        int end = 0;
        if (l == 0) {
            buf[end++] = (byte) '0';
        }
        if (l < 0) {
            current = -l;
            buf[end++] = (byte) '-';
        }
        while (current > 0) {
            int digit = (int) (current % 10);
            current = current / 10;
            buf[end++] = HexUtils.getHex(digit);
        }
        byteC.setOffset(0);
        byteC.setEnd(end);
        // Inverting buffer
        end--;
        if (l < 0) {
            start++;
        }
        while (end > start) {
            byte temp = buf[start];
            buf[start] = buf[end];
            buf[end] = temp;
            start++;
            end--;
        }
        longValue=l;
        hasStrValue=false;
        hasHashCode=false;
        hasIntValue=false;
        hasLongValue=true;
        type=T_BYTES;
    }

    // Used for headers conversion
    /**
     * Convert the buffer to an int, cache the value
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public int getInt()
    {
        if( hasIntValue ) {
            return intValue;
        }

        switch (type) {
        case T_BYTES:
            intValue=byteC.getInt();
            break;
        default:
            intValue=Integer.parseInt(toString());
        }
        hasIntValue=true;
        return intValue;
    }

    // Used for headers conversion
    /** Convert the buffer to an long, cache the value
     */
    public long getLong() {
        if( hasLongValue ) {
            return longValue;
        }

        switch (type) {
        case T_BYTES:
            longValue=byteC.getLong();
            break;
        default:
            longValue=Long.parseLong(toString());
        }

        hasLongValue=true;
        return longValue;

     }

    // -------------------- Future may be different --------------------

    private static MessageBytesFactory factory=new MessageBytesFactory();

    /**
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public static void setFactory( MessageBytesFactory mbf ) {
        factory=mbf;
    }

    public static class MessageBytesFactory {
        protected MessageBytesFactory() {
        }
        public MessageBytes newInstance() {
            return new MessageBytes();
        }
    }
}
