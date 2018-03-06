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
package org.apache.tomcat.util.http;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.tomcat.util.buf.MessageBytes;


/**
 *  Server-side cookie representation.
 *  Allows recycling and uses MessageBytes as low-level
 *  representation ( and thus the byte-> char conversion can be delayed
 *  until we know the charset ).
 *
 *  Tomcat.core uses this recyclable object to represent cookies,
 *  and the facade will convert it to the external representation.
 */
public class ServerCookie implements Serializable {

    private static final long serialVersionUID = 1L;

    // Version 0 (Netscape) attributes
    private final MessageBytes name=MessageBytes.newInstance();
    private final MessageBytes value=MessageBytes.newInstance();
    // Expires - Not stored explicitly. Generated from Max-Age (see V1)
    private final MessageBytes path=MessageBytes.newInstance();
    private final MessageBytes domain=MessageBytes.newInstance();
    private boolean secure;

    // Version 1 (RFC2109) attributes
    private final MessageBytes comment=MessageBytes.newInstance();
    private int maxAge = -1;
    private int version = 0;

    // Other fields
    private static final String OLD_COOKIE_PATTERN =
        "EEE, dd-MMM-yyyy HH:mm:ss z";
    private static final ThreadLocal<DateFormat> OLD_COOKIE_FORMAT =
        new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            DateFormat df =
                new SimpleDateFormat(OLD_COOKIE_PATTERN, Locale.US);
            df.setTimeZone(TimeZone.getTimeZone("GMT"));
            return df;
        }
    };
    private static final String ancientDate;

    static {
        ancientDate = OLD_COOKIE_FORMAT.get().format(new Date(10000));
    }

    // Note: Servlet Spec =< 3.0 only refers to Netscape and RFC2109,
    // not RFC2965

    // Version 2 (RFC2965) attributes that would need to be added to support
    // v2 cookies
    // CommentURL
    // Discard - implied by maxAge <0
    // Port

    public ServerCookie() {
        // NOOP
    }

    public void recycle() {
        name.recycle();
        value.recycle();
        comment.recycle();
        maxAge=-1;
        path.recycle();
        domain.recycle();
        version=0;
        secure=false;
    }

    public MessageBytes getComment() {
        return comment;
    }

    public MessageBytes getDomain() {
        return domain;
    }

    public void setMaxAge(int expiry) {
        maxAge = expiry;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public MessageBytes getPath() {
        return path;
    }

    public void setSecure(boolean flag) {
        secure = flag;
    }

    public boolean getSecure() {
        return secure;
    }

    public MessageBytes getName() {
        return name;
    }

    public MessageBytes getValue() {
        return value;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int v) {
        version = v;
    }


    // -------------------- utils --------------------

    @Override
    public String toString() {
        return "Cookie " + getName() + "=" + getValue() + " ; "
            + getVersion() + " " + getPath() + " " + getDomain();
    }

    // -------------------- Cookie parsing tools


    public static void appendCookieValue( StringBuffer headerBuf,
                                          int version,
                                          String name,
                                          String value,
                                          String path,
                                          String domain,
                                          String comment,
                                          int maxAge,
                                          boolean isSecure,
                                          boolean isHttpOnly)
    {
        StringBuffer buf = new StringBuffer();
        // Servlet implementation checks name
        buf.append( name );
        buf.append("=");
        // Servlet implementation does not check anything else

        /*
         * The spec allows some latitude on when to send the version attribute
         * with a Set-Cookie header. To be nice to clients, we'll make sure the
         * version attribute is first. That means checking the various things
         * that can cause us to switch to a v1 cookie first.
         *
         * Note that by checking for tokens we will also throw an exception if a
         * control character is encountered.
         */
        // Start by using the version we were asked for
        int newVersion = version;

        // If it is v0, check if we need to switch
        if (newVersion == 0 &&
                (!CookieSupport.ALLOW_HTTP_SEPARATORS_IN_V0 &&
                 CookieSupport.isHttpToken(value) ||
                 CookieSupport.ALLOW_HTTP_SEPARATORS_IN_V0 &&
                 CookieSupport.isV0Token(value))) {
            // HTTP token in value - need to use v1
            newVersion = 1;
        }

        if (newVersion == 0 && comment != null) {
            // Using a comment makes it a v1 cookie
           newVersion = 1;
        }

        if (newVersion == 0 &&
                (!CookieSupport.ALLOW_HTTP_SEPARATORS_IN_V0 &&
                 CookieSupport.isHttpToken(path) ||
                 CookieSupport.ALLOW_HTTP_SEPARATORS_IN_V0 &&
                 CookieSupport.isV0Token(path))) {
            // HTTP token in path - need to use v1
            newVersion = 1;
        }

        if (newVersion == 0 &&
                (!CookieSupport.ALLOW_HTTP_SEPARATORS_IN_V0 &&
                 CookieSupport.isHttpToken(domain) ||
                 CookieSupport.ALLOW_HTTP_SEPARATORS_IN_V0 &&
                 CookieSupport.isV0Token(domain))) {
            // HTTP token in domain - need to use v1
            newVersion = 1;
        }

        // Now build the cookie header
        // Value
        maybeQuote(buf, value);
        // Add version 1 specific information
        if (newVersion == 1) {
            // Version=1 ... required
            buf.append ("; Version=1");

            // Comment=comment
            if ( comment!=null ) {
                buf.append ("; Comment=");
                maybeQuote(buf, comment);
            }
        }

        // Add domain information, if present
        if (domain!=null) {
            buf.append("; Domain=");
            maybeQuote(buf, domain);
        }

        // Max-Age=secs ... or use old "Expires" format
        if (maxAge >= 0) {
            if (newVersion > 0) {
                buf.append ("; Max-Age=");
                buf.append (maxAge);
            }
            // IE6, IE7 and possibly other browsers don't understand Max-Age.
            // They do understand Expires, even with V1 cookies!
            if (newVersion == 0 || CookieSupport.ALWAYS_ADD_EXPIRES) {
                // Wdy, DD-Mon-YY HH:MM:SS GMT ( Expires Netscape format )
                buf.append ("; Expires=");
                // To expire immediately we need to set the time in past
                if (maxAge == 0) {
                    buf.append( ancientDate );
                } else {
                    OLD_COOKIE_FORMAT.get().format(
                            new Date(System.currentTimeMillis() +
                                    maxAge*1000L),
                            buf, new FieldPosition(0));
                }
            }
        }

        // Path=path
        if (path!=null) {
            buf.append ("; Path=");
            maybeQuote(buf, path);
        }

        // Secure
        if (isSecure) {
          buf.append ("; Secure");
        }

        // HttpOnly
        if (isHttpOnly) {
            buf.append("; HttpOnly");
        }
        headerBuf.append(buf);
    }

    /**
     * Quotes values if required.
     * @param buf
     * @param value
     */
    private static void maybeQuote (StringBuffer buf, String value) {
        if (value==null || value.length()==0) {
            buf.append("\"\"");
        } else if (CookieSupport.alreadyQuoted(value)) {
            buf.append('"');
            buf.append(escapeDoubleQuotes(value,1,value.length()-1));
            buf.append('"');
        } else if (CookieSupport.isHttpToken(value) &&
                !CookieSupport.ALLOW_HTTP_SEPARATORS_IN_V0 ||
                CookieSupport.isV0Token(value) &&
                CookieSupport.ALLOW_HTTP_SEPARATORS_IN_V0) {
            buf.append('"');
            buf.append(escapeDoubleQuotes(value,0,value.length()));
            buf.append('"');
        } else {
            buf.append(value);
        }
    }


    /**
     * Escapes any double quotes in the given string.
     *
     * @param s the input string
     * @param beginIndex start index inclusive
     * @param endIndex exclusive
     * @return The (possibly) escaped string
     */
    private static String escapeDoubleQuotes(String s, int beginIndex, int endIndex) {

        if (s == null || s.length() == 0 || s.indexOf('"') == -1) {
            return s;
        }

        StringBuffer b = new StringBuffer();
        for (int i = beginIndex; i < endIndex; i++) {
            char c = s.charAt(i);
            if (c == '\\' ) {
                b.append(c);
                //ignore the character after an escape, just append it
                if (++i>=endIndex) {
                    throw new IllegalArgumentException("Invalid escape character in cookie value.");
                }
                b.append(s.charAt(i));
            } else if (c == '"') {
                b.append('\\').append('"');
            } else {
                b.append(c);
            }
        }

        return b.toString();
    }

}

