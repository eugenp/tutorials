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

import java.io.CharConversionException;
import java.io.IOException;

/**
 *  All URL decoding happens here. This way we can reuse, review, optimize
 *  without adding complexity to the buffers.
 *
 *  The conversion will modify the original buffer.
 *
 *  @author Costin Manolache
 */
public final class UDecoder {

    public static final boolean ALLOW_ENCODED_SLASH =
        Boolean.parseBoolean(System.getProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "false"));

    private static class DecodeException extends CharConversionException {
        private static final long serialVersionUID = 1L;
        public DecodeException(String s) {
            super(s);
        }

        @Override
        public synchronized Throwable fillInStackTrace() {
            // This class does not provide a stack trace
            return this;
        }
    }

    /** Unexpected end of data. */
    private static final IOException EXCEPTION_EOF = new DecodeException("EOF");

    /** %xx with not-hex digit */
    private static final IOException EXCEPTION_NOT_HEX_DIGIT = new DecodeException(
            "isHexDigit");

    /** %-encoded slash is forbidden in resource path */
    private static final IOException EXCEPTION_SLASH = new DecodeException(
            "noSlash");

    public UDecoder()
    {
    }

    /** URLDecode, will modify the source.  Includes converting
     *  '+' to ' '.
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public void convert( ByteChunk mb )
        throws IOException
    {
        convert(mb, true);
    }

    /** URLDecode, will modify the source.
     */
    public void convert( ByteChunk mb, boolean query )
        throws IOException
    {
        int start=mb.getOffset();
        byte buff[]=mb.getBytes();
        int end=mb.getEnd();

        int idx= ByteChunk.findByte( buff, start, end, (byte) '%' );
        int idx2=-1;
        if( query ) {
            idx2= ByteChunk.findByte( buff, start, (idx >= 0 ? idx : end), (byte) '+' );
        }
        if( idx<0 && idx2<0 ) {
            return;
        }

        // idx will be the smallest positive index ( first % or + )
        if( (idx2 >= 0 && idx2 < idx) || idx < 0 ) {
            idx=idx2;
        }

        final boolean noSlash = !(ALLOW_ENCODED_SLASH || query);

        for( int j=idx; j<end; j++, idx++ ) {
            if( buff[ j ] == '+' && query) {
                buff[idx]= (byte)' ' ;
            } else if( buff[ j ] != '%' ) {
                buff[idx]= buff[j];
            } else {
                // read next 2 digits
                if( j+2 >= end ) {
                    throw EXCEPTION_EOF;
                }
                byte b1= buff[j+1];
                byte b2=buff[j+2];
                if( !isHexDigit( b1 ) || ! isHexDigit(b2 )) {
                    throw EXCEPTION_NOT_HEX_DIGIT;
                }

                j+=2;
                int res=x2c( b1, b2 );
                if (noSlash && (res == '/')) {
                    throw EXCEPTION_SLASH;
                }
                buff[idx]=(byte)res;
            }
        }

        mb.setEnd( idx );

        return;
    }

    // -------------------- Additional methods --------------------
    // XXX What do we do about charset ????

    /** In-buffer processing - the buffer will be modified
     *  Includes converting  '+' to ' '.
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public void convert( CharChunk mb )
        throws IOException
    {
        convert(mb, true);
    }

    /** In-buffer processing - the buffer will be modified
     */
    public void convert( CharChunk mb, boolean query )
        throws IOException
    {
        //        log( "Converting a char chunk ");
        int start=mb.getOffset();
        char buff[]=mb.getBuffer();
        int cend=mb.getEnd();

        int idx= CharChunk.indexOf( buff, start, cend, '%' );
        int idx2=-1;
        if( query ) {
            idx2= CharChunk.indexOf( buff, start, (idx >= 0 ? idx : cend), '+' );
        }
        if( idx<0 && idx2<0 ) {
            return;
        }

        // idx will be the smallest positive index ( first % or + )
        if( (idx2 >= 0 && idx2 < idx) || idx < 0 ) {
            idx=idx2;
        }

        final boolean noSlash = !(ALLOW_ENCODED_SLASH || query);

        for( int j=idx; j<cend; j++, idx++ ) {
            if( buff[ j ] == '+' && query ) {
                buff[idx]=( ' ' );
            } else if( buff[ j ] != '%' ) {
                buff[idx]=buff[j];
            } else {
                // read next 2 digits
                if( j+2 >= cend ) {
                    // invalid
                    throw EXCEPTION_EOF;
                }
                char b1= buff[j+1];
                char b2=buff[j+2];
                if( !isHexDigit( b1 ) || ! isHexDigit(b2 )) {
                    throw EXCEPTION_NOT_HEX_DIGIT;
                }

                j+=2;
                int res=x2c( b1, b2 );
                if (noSlash && (res == '/')) {
                    throw EXCEPTION_SLASH;
                }
                buff[idx]=(char)res;
            }
        }
        mb.setEnd( idx );
    }

    /** URLDecode, will modify the source
     *  Includes converting  '+' to ' '.
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public void convert(MessageBytes mb)
        throws IOException
    {
        convert(mb, true);
    }

    /** URLDecode, will modify the source
     */
    public void convert(MessageBytes mb, boolean query)
        throws IOException
    {

        switch (mb.getType()) {
        case MessageBytes.T_STR:
            String strValue=mb.toString();
            if( strValue==null ) {
                return;
            }
            try {
                mb.setString( convert( strValue, query ));
            } catch (RuntimeException ex) {
                throw new DecodeException(ex.getMessage());
            }
            break;
        case MessageBytes.T_CHARS:
            CharChunk charC=mb.getCharChunk();
            convert( charC, query );
            break;
        case MessageBytes.T_BYTES:
            ByteChunk bytesC=mb.getByteChunk();
            convert( bytesC, query );
            break;
        }
    }

    // XXX Old code, needs to be replaced !!!!
    //
    /**
     * @deprecated Unused. Will be removed in Tomcat 8.0.x onwards.
     */
    @Deprecated
    public final String convert(String str)
    {
        return convert(str, true);
    }

    public final String convert(String str, boolean query)
    {
        if (str == null) {
            return  null;
        }

        if( (!query || str.indexOf( '+' ) < 0) && str.indexOf( '%' ) < 0 ) {
            return str;
        }

        final boolean noSlash = !(ALLOW_ENCODED_SLASH || query);

        StringBuilder dec = new StringBuilder();    // decoded string output
        int strPos = 0;
        int strLen = str.length();

        dec.ensureCapacity(str.length());
        while (strPos < strLen) {
            int laPos;        // lookahead position

            // look ahead to next URLencoded metacharacter, if any
            for (laPos = strPos; laPos < strLen; laPos++) {
                char laChar = str.charAt(laPos);
                if ((laChar == '+' && query) || (laChar == '%')) {
                    break;
                }
            }

            // if there were non-metacharacters, copy them all as a block
            if (laPos > strPos) {
                dec.append(str.substring(strPos,laPos));
                strPos = laPos;
            }

            // shortcut out of here if we're at the end of the string
            if (strPos >= strLen) {
                break;
            }

            // process next metacharacter
            char metaChar = str.charAt(strPos);
            if (metaChar == '+') {
                dec.append(' ');
                strPos++;
                continue;
            } else if (metaChar == '%') {
                // We throw the original exception - the super will deal with
                // it
                //                try {
                char res = (char) Integer.parseInt(
                        str.substring(strPos + 1, strPos + 3), 16);
                if (noSlash && (res == '/')) {
                    throw new IllegalArgumentException("noSlash");
                }
                dec.append(res);
                strPos += 3;
            }
        }

        return dec.toString();
    }



    private static boolean isHexDigit( int c ) {
        return ( ( c>='0' && c<='9' ) ||
                 ( c>='a' && c<='f' ) ||
                 ( c>='A' && c<='F' ));
    }

    private static int x2c( byte b1, byte b2 ) {
        int digit= (b1>='A') ? ( (b1 & 0xDF)-'A') + 10 :
            (b1 -'0');
        digit*=16;
        digit +=(b2>='A') ? ( (b2 & 0xDF)-'A') + 10 :
            (b2 -'0');
        return digit;
    }

    private static int x2c( char b1, char b2 ) {
        int digit= (b1>='A') ? ( (b1 & 0xDF)-'A') + 10 :
            (b1 -'0');
        digit*=16;
        digit +=(b2>='A') ? ( (b2 & 0xDF)-'A') + 10 :
            (b2 -'0');
        return digit;
    }

}
