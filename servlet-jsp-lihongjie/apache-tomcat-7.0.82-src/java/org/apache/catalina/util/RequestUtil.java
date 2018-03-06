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

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.buf.B2CConverter;
import org.apache.tomcat.util.res.StringManager;


/**
 * General purpose request parsing and encoding utility methods.
 *
 * @author Craig R. McClanahan
 * @author Tim Tye
 */
public final class RequestUtil {


    private static final Log log = LogFactory.getLog(RequestUtil.class);

    /**
     * The string resources for this package.
     */
    private static final StringManager sm =
        StringManager.getManager("org.apache.catalina.util");


    /**
     * Filter the specified message string for characters that are sensitive
     * in HTML.  This avoids potential attacks caused by including JavaScript
     * codes in the request URL that is often reported in error messages.
     *
     * @param message The message string to be filtered
     */
    public static String filter(String message) {

        if (message == null)
            return (null);

        char content[] = new char[message.length()];
        message.getChars(0, message.length(), content, 0);
        StringBuilder result = new StringBuilder(content.length + 50);
        for (int i = 0; i < content.length; i++) {
            switch (content[i]) {
            case '<':
                result.append("&lt;");
                break;
            case '>':
                result.append("&gt;");
                break;
            case '&':
                result.append("&amp;");
                break;
            case '"':
                result.append("&quot;");
                break;
            default:
                result.append(content[i]);
            }
        }
        return (result.toString());

    }


    /**
     * Normalize a relative URI path that may have relative values ("/./",
     * "/../", and so on ) it it.  <strong>WARNING</strong> - This method is
     * useful only for normalizing application-generated paths.  It does not
     * try to perform security checks for malicious input.
     *
     * @param path Relative path to be normalized
     *
     * @deprecated Deprecated to resolve a circular package dependency and will
     *             be removed in Tomcat 8.0.x. Use {@link
     *             org.apache.tomcat.util.http.RequestUtil#normalize(String)} as
     *             a replacement.
     */
    @Deprecated
    public static String normalize(String path) {
        return org.apache.tomcat.util.http.RequestUtil.normalize(path);
    }


    /**
     * Normalize a relative URI path that may have relative values ("/./",
     * "/../", and so on ) it it.  <strong>WARNING</strong> - This method is
     * useful only for normalizing application-generated paths.  It does not
     * try to perform security checks for malicious input.
     *
     * @param path Relative path to be normalized
     * @param replaceBackSlash Should '\\' be replaced with '/'
     *
     * @deprecated Deprecated to resolve a circular package dependency and will
     *             be removed in Tomcat 8.0.x. Use {@link
     *             org.apache.tomcat.util.http.RequestUtil#normalize(String,
     *             boolean)} as a replacement.
     */
    @Deprecated
    public static String normalize(String path, boolean replaceBackSlash) {
        return org.apache.tomcat.util.http.RequestUtil.normalize(path,
                replaceBackSlash);
    }


    /**
     * Append request parameters from the specified String to the specified
     * Map.  It is presumed that the specified Map is not accessed from any
     * other thread, so no synchronization is performed.
     * <p>
     * <strong>IMPLEMENTATION NOTE</strong>:  URL decoding is performed
     * individually on the parsed name and value elements, rather than on
     * the entire query string ahead of time, to properly deal with the case
     * where the name or value includes an encoded "=" or "&" character
     * that would otherwise be interpreted as a delimiter.
     *
     * @param map Map that accumulates the resulting parameters
     * @param data Input string containing request parameters
     * @param encoding The encoding to use; encoding must not be null.
     * If an unsupported encoding is specified the parameters will not be
     * parsed and the map will not be modified
     */
    public static void parseParameters(Map<String,String[]> map, String data,
            String encoding) {

        if ((data != null) && (data.length() > 0)) {

            // use the specified encoding to extract bytes out of the
            // given string so that the encoding is not lost.
            byte[] bytes = null;
            try {
                bytes = data.getBytes(B2CConverter.getCharset(encoding));
                parseParameters(map, bytes, encoding);
            } catch (UnsupportedEncodingException uee) {
                if (log.isDebugEnabled()) {
                    log.debug(sm.getString("requestUtil.parseParameters.uee",
                            encoding), uee);
                }
            }

        }

    }


    /**
     * Decode and return the specified URL-encoded String.
     * When the byte array is converted to a string, the system default
     * character encoding is used...  This may be different than some other
     * servers. It is assumed the string is not a query string.
     *
     * @param str The url-encoded string
     *
     * @exception IllegalArgumentException if a '%' character is not followed
     * by a valid 2-digit hexadecimal number
     */
    public static String URLDecode(String str) {
        return URLDecode(str, null);
    }
    
    
    /**
     * Decode and return the specified URL-encoded String. It is assumed the
     * string is not a query string.
     *
     * @param str The url-encoded string
     * @param enc The encoding to use; if null, the default encoding is used. If
     * an unsupported encoding is specified null will be returned
     * @exception IllegalArgumentException if a '%' character is not followed
     * by a valid 2-digit hexadecimal number
     */
    public static String URLDecode(String str, String enc) {
        return URLDecode(str, enc, false);
    }
    
    /**
     * Decode and return the specified URL-encoded String.
     *
     * @param str The url-encoded string
     * @param enc The encoding to use; if null, the default encoding is used. If
     * an unsupported encoding is specified null will be returned
     * @param isQuery Is this a query string being processed
     * @exception IllegalArgumentException if a '%' character is not followed
     * by a valid 2-digit hexadecimal number
     */
    public static String URLDecode(String str, String enc, boolean isQuery) {
        if (str == null)
            return (null);

        // use the specified encoding to extract bytes out of the
        // given string so that the encoding is not lost. If an
        // encoding is not specified, let it use platform default
        byte[] bytes = null;
        try {
            if (enc == null) {
                bytes = str.getBytes(Charset.defaultCharset());
            } else {
                bytes = str.getBytes(B2CConverter.getCharset(enc));
            }
        } catch (UnsupportedEncodingException uee) {
            if (log.isDebugEnabled()) {
                log.debug(sm.getString("requestUtil.urlDecode.uee", enc), uee);
            }
        }

        return URLDecode(bytes, enc, isQuery);

    }


    /**
     * Decode and return the specified URL-encoded byte array. It is assumed
     * the string is not a query string.
     *
     * @param bytes The url-encoded byte array
     * @exception IllegalArgumentException if a '%' character is not followed
     * by a valid 2-digit hexadecimal number
     */
    public static String URLDecode(byte[] bytes) {
        return URLDecode(bytes, null);
    }


    /**
     * Decode and return the specified URL-encoded byte array. It is assumed
     * the string is not a query string.
     *
     * @param bytes The url-encoded byte array
     * @param enc The encoding to use; if null, the default encoding is used
     * @exception IllegalArgumentException if a '%' character is not followed
     * by a valid 2-digit hexadecimal number
     *
     * @deprecated  Unused - will be removed in 8.0.x
     */
    @Deprecated
    public static String URLDecode(byte[] bytes, String enc) {
        return URLDecode(bytes, enc, false);
    }

    /**
     * Decode and return the specified URL-encoded byte array.
     *
     * @param bytes The url-encoded byte array
     * @param enc The encoding to use; if null, the default encoding is used. If
     * an unsupported encoding is specified null will be returned
     * @param isQuery Is this a query string being processed
     * @exception IllegalArgumentException if a '%' character is not followed
     * by a valid 2-digit hexadecimal number
     */
    public static String URLDecode(byte[] bytes, String enc, boolean isQuery) {
    
        if (bytes == null)
            return null;

        int len = bytes.length;
        int ix = 0;
        int ox = 0;
        while (ix < len) {
            byte b = bytes[ix++];     // Get byte to test
            if (b == '+' && isQuery) {
                b = (byte)' ';
            } else if (b == '%') {
                if (ix + 2 > len) {
                    throw new IllegalArgumentException(
                            sm.getString("requestUtil.urlDecode.missingDigit"));
                }
                b = (byte) ((convertHexDigit(bytes[ix++]) << 4)
                            + convertHexDigit(bytes[ix++]));
            }
            bytes[ox++] = b;
        }
        if (enc != null) {
            try {
                return new String(bytes, 0, ox, B2CConverter.getCharset(enc));
            } catch (UnsupportedEncodingException uee) {
                if (log.isDebugEnabled()) {
                    log.debug(sm.getString("requestUtil.urlDecode.uee", enc), uee);
                }
                return null;
            }
        }
        return new String(bytes, 0, ox);

    }


    /**
     * Convert a byte character value to hexadecimal digit value.
     *
     * @param b the character value byte
     */
    private static byte convertHexDigit( byte b ) {
        if ((b >= '0') && (b <= '9')) return (byte)(b - '0');
        if ((b >= 'a') && (b <= 'f')) return (byte)(b - 'a' + 10);
        if ((b >= 'A') && (b <= 'F')) return (byte)(b - 'A' + 10);
        throw new IllegalArgumentException(
                sm.getString("requestUtil.convertHexDigit.notHex",
                        Character.valueOf((char)b)));
    }


    /**
     * Put name and value pair in map.  When name already exist, add value
     * to array of values.
     *
     * @param map The map to populate
     * @param name The parameter name
     * @param value The parameter value
     */
    private static void putMapEntry( Map<String,String[]> map, String name,
            String value) {
        String[] newValues = null;
        String[] oldValues = map.get(name);
        if (oldValues == null) {
            newValues = new String[1];
            newValues[0] = value;
        } else {
            newValues = new String[oldValues.length + 1];
            System.arraycopy(oldValues, 0, newValues, 0, oldValues.length);
            newValues[oldValues.length] = value;
        }
        map.put(name, newValues);
    }


    /**
     * Append request parameters from the specified String to the specified
     * Map.  It is presumed that the specified Map is not accessed from any
     * other thread, so no synchronization is performed.
     * <p>
     * <strong>IMPLEMENTATION NOTE</strong>:  URL decoding is performed
     * individually on the parsed name and value elements, rather than on
     * the entire query string ahead of time, to properly deal with the case
     * where the name or value includes an encoded "=" or "&" character
     * that would otherwise be interpreted as a delimiter.
     *
     * NOTE: byte array data is modified by this method.  Caller beware.
     *
     * @param map Map that accumulates the resulting parameters
     * @param data Input string containing request parameters
     * @param encoding The encoding to use; if null, the default encoding is
     * used
     *
     * @exception UnsupportedEncodingException if the requested encoding is not
     * supported.
     */
    public static void parseParameters(Map<String,String[]> map, byte[] data,
            String encoding) throws UnsupportedEncodingException {

        Charset charset = B2CConverter.getCharset(encoding);
        
        if (data != null && data.length > 0) {
            int    ix = 0;
            int    ox = 0;
            String key = null;
            String value = null;
            while (ix < data.length) {
                byte c = data[ix++];
                switch ((char) c) {
                case '&':
                    value = new String(data, 0, ox, charset);
                    if (key != null) {
                        putMapEntry(map, key, value);
                        key = null;
                    }
                    ox = 0;
                    break;
                case '=':
                    if (key == null) {
                        key = new String(data, 0, ox, charset);
                        ox = 0;
                    } else {
                        data[ox++] = c;
                    }                   
                    break;  
                case '+':
                    data[ox++] = (byte)' ';
                    break;
                case '%':
                    data[ox++] = (byte)((convertHexDigit(data[ix++]) << 4)
                                    + convertHexDigit(data[ix++]));
                    break;
                default:
                    data[ox++] = c;
                }
            }
            //The last value does not end in '&'.  So save it now.
            if (key != null) {
                value = new String(data, 0, ox, charset);
                putMapEntry(map, key, value);
            }
        }

    }
}
