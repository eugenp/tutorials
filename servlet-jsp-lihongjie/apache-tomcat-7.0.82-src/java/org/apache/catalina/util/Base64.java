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

import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.buf.CharChunk;

/**
 * This class provides encode/decode for RFC 2045 Base64 as defined by
 * RFC 2045, N. Freed and N. Borenstein.  <a
 * href="http://www.ietf.org/rfc/rfc2045.txt">RFC 2045</a>:
 * Multipurpose Internet Mail Extensions (MIME) Part One: Format of
 * Internet Message Bodies. Reference 1996
 *
 * @author Jeffrey Rodriguez
 *
 * @deprecated  Use {@link org.apache.tomcat.util.codec.binary.Base64}
 *              This class will be removed in Tomcat 8.
 */
@Deprecated
public final class  Base64
{
    private static final int  BASELENGTH              = 255;
    private static final int  LOOKUPLENGTH            = 64;
    private static final int  FOURBYTE                = 4;
    private static final byte PAD                     = (byte) '=';
    private static final byte [] base64Alphabet       = new byte[BASELENGTH];
    private static final byte [] lookUpBase64Alphabet = new byte[LOOKUPLENGTH];

    static
    {
        for (int i = 0; i < BASELENGTH; i++ )
        {
            base64Alphabet[i] = -1;
        }
        for (int i = 'Z'; i >= 'A'; i--)
        {
            base64Alphabet[i] = (byte) (i - 'A');
        }
        for (int i = 'z'; i>= 'a'; i--)
        {
            base64Alphabet[i] = (byte) (i - 'a' + 26);
        }
        for (int i = '9'; i >= '0'; i--)
        {
            base64Alphabet[i] = (byte) (i - '0' + 52);
        }

        base64Alphabet['+']  = 62;
        base64Alphabet['/']  = 63;

        for (int i = 0; i <= 25; i++ )
            lookUpBase64Alphabet[i] = (byte) ('A' + i);

        for (int i = 26,  j = 0; i <= 51; i++, j++ )
            lookUpBase64Alphabet[i] = (byte) ('a'+ j);

        for (int i = 52,  j = 0; i <= 61; i++, j++ )
            lookUpBase64Alphabet[i] = (byte) ('0' + j);

        lookUpBase64Alphabet[62] = (byte) '+';
        lookUpBase64Alphabet[63] = (byte) '/';
    }

    /**
     * Encodes hex octets into Base64.
     *
     * @param binaryData Array containing binary data to encode.
     * @return Base64-encoded data.
     *
     * @deprecated  Use {@link DatatypeConverter#printBase64Binary(byte[])}.
     *              This method will be removed in Tomcat 8.0.x.
     */
    @Deprecated
    public static String encode(byte[] binaryData) {
        return DatatypeConverter.printBase64Binary(binaryData);
    }

    /**
     * Decodes Base64 data into octets
     *
     * @param base64DataBC Byte array containing Base64 data
     * @param decodedDataCC The decoded data chars
     */
    public static void decode( ByteChunk base64DataBC, CharChunk decodedDataCC)
    {
        int start = base64DataBC.getStart();
        int end = base64DataBC.getEnd();
        byte[] base64Data = base64DataBC.getBuffer();
        
        decodedDataCC.recycle();
        
        // handle the edge case, so we don't have to worry about it later
        if(end - start == 0) { return; }

        int      numberQuadruple    = (end - start)/FOURBYTE;
        byte     b1=0,b2=0,b3=0, b4=0, marker0=0, marker1=0;

        // Throw away anything not in base64Data

        int encodedIndex = 0;
        int dataIndex = start;
        char[] decodedData = null;
        
        {
            // this sizes the output array properly - rlw
            int lastData = end - start;
            // ignore the '=' padding
            while (base64Data[start+lastData-1] == PAD)
            {
                if (--lastData == 0)
                {
                    return;
                }
            }
            decodedDataCC.allocate(lastData - numberQuadruple, -1);
            decodedDataCC.setEnd(lastData - numberQuadruple);
            decodedData = decodedDataCC.getBuffer();
        }

        for (int i = 0; i < numberQuadruple; i++)
        {
            dataIndex = start + i * 4;
            marker0   = base64Data[dataIndex + 2];
            marker1   = base64Data[dataIndex + 3];

            b1 = base64Alphabet[base64Data[dataIndex]];
            b2 = base64Alphabet[base64Data[dataIndex +1]];

            if (marker0 != PAD && marker1 != PAD)
            {
                //No PAD e.g 3cQl
                b3 = base64Alphabet[ marker0 ];
                b4 = base64Alphabet[ marker1 ];

                decodedData[encodedIndex]   = (char) ((  b1 <<2 | b2>>4 ) & 0xff);
                decodedData[encodedIndex + 1] =
                    (char) ((((b2 & 0xf)<<4 ) |( (b3>>2) & 0xf) ) & 0xff);
                decodedData[encodedIndex + 2] = (char) (( b3<<6 | b4 ) & 0xff);
            }
            else if (marker0 == PAD)
            {
                //Two PAD e.g. 3c[Pad][Pad]
                decodedData[encodedIndex]   = (char) ((  b1 <<2 | b2>>4 ) & 0xff);
            }
            else if (marker1 == PAD)
            {
                //One PAD e.g. 3cQ[Pad]
                b3 = base64Alphabet[ marker0 ];

                decodedData[encodedIndex]   = (char) ((  b1 <<2 | b2>>4 ) & 0xff);
                decodedData[encodedIndex + 1] =
                    (char) ((((b2 & 0xf)<<4 ) |( (b3>>2) & 0xf) ) & 0xff);
            }
            encodedIndex += 3;
        }
    }


    /**
     * Decodes Base64 data into octets
     *
     * @param base64DataBC Byte array containing Base64 data
     * @param decodedDataBC The decoded data bytes
     */
    public static void decode( ByteChunk base64DataBC, ByteChunk decodedDataBC)
    {
        int start = base64DataBC.getStart();
        int end = base64DataBC.getEnd();
        byte[] base64Data = base64DataBC.getBuffer();
        
        decodedDataBC.recycle();
        
        // handle the edge case, so we don't have to worry about it later
        if(end - start == 0) { return; }

        int      numberQuadruple    = (end - start)/FOURBYTE;
        byte     b1=0,b2=0,b3=0, b4=0, marker0=0, marker1=0;

        // Throw away anything not in base64Data

        int encodedIndex = 0;
        int dataIndex = start;
        byte[] decodedData = null;
        
        {
            // this sizes the output array properly - rlw
            int lastData = end - start;
            // ignore the '=' padding
            while (base64Data[start+lastData-1] == PAD)
            {
                if (--lastData == 0)
                {
                    return;
                }
            }
            decodedDataBC.allocate(lastData - numberQuadruple, -1);
            decodedDataBC.setEnd(lastData - numberQuadruple);
            decodedData = decodedDataBC.getBuffer();
        }

        for (int i = 0; i < numberQuadruple; i++)
        {
            dataIndex = start + i * 4;
            marker0   = base64Data[dataIndex + 2];
            marker1   = base64Data[dataIndex + 3];

            b1 = base64Alphabet[base64Data[dataIndex]];
            b2 = base64Alphabet[base64Data[dataIndex +1]];

            if (marker0 != PAD && marker1 != PAD)
            {
                //No PAD e.g 3cQl
                b3 = base64Alphabet[ marker0 ];
                b4 = base64Alphabet[ marker1 ];

                decodedData[encodedIndex]   = (byte) ((  b1 <<2 | b2>>4 ) & 0xff);
                decodedData[encodedIndex + 1] =
                    (byte) ((((b2 & 0xf)<<4 ) |( (b3>>2) & 0xf) ) & 0xff);
                decodedData[encodedIndex + 2] = (byte) (( b3<<6 | b4 ) & 0xff);
            }
            else if (marker0 == PAD)
            {
                //Two PAD e.g. 3c[Pad][Pad]
                decodedData[encodedIndex]   = (byte) ((  b1 <<2 | b2>>4 ) & 0xff);
            }
            else if (marker1 == PAD)
            {
                //One PAD e.g. 3cQ[Pad]
                b3 = base64Alphabet[ marker0 ];

                decodedData[encodedIndex]   = (byte) ((  b1 <<2 | b2>>4 ) & 0xff);
                decodedData[encodedIndex + 1] =
                    (byte) ((((b2 & 0xf)<<4 ) |( (b3>>2) & 0xf) ) & 0xff);
            }
            encodedIndex += 3;
        }
    }


}
