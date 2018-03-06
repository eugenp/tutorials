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
package org.apache.tomcat.util.http;


/**
 * Static constants for this package.
 */
public final class CookieSupport {

    // --------------------------------------------------------------- Constants
    /**
     * If set to true, we parse cookies strictly according to the servlet,
     * cookie and HTTP specs by default.
     */
    public static final boolean STRICT_SERVLET_COMPLIANCE;

    /**
     * If true, cookie values are allowed to contain an equals character without
     * being quoted.
     */
    public static final boolean ALLOW_EQUALS_IN_VALUE;

    /**
     * If true, separators that are not explicitly dis-allowed by the v0 cookie
     * spec but are disallowed by the HTTP spec will be allowed in v0 cookie
     * names and values. These characters are: \"()/:<=>?@[\\]{} Note that the
     * inclusion of / depends on the value of {@link #FWD_SLASH_IS_SEPARATOR}.
     */
    public static final boolean ALLOW_HTTP_SEPARATORS_IN_V0;

    /**
     * If set to false, we don't use the IE6/7 Max-Age/Expires work around.
     * Default is usually true. If STRICT_SERVLET_COMPLIANCE==true then default
     * is false. Explicitly setting always takes priority.
     */
    public static final boolean ALWAYS_ADD_EXPIRES;

    /**
     * If set to true, the <code>/</code> character will be treated as a
     * separator. Default is usually false. If STRICT_SERVLET_COMPLIANCE==true
     * then default is true. Explicitly setting always takes priority.
     */
    public static final boolean FWD_SLASH_IS_SEPARATOR;

    /**
     * If true, name only cookies will be permitted.
     */
    public static final boolean ALLOW_NAME_ONLY;

    /**
     * If set to true, the cookie header will be preserved. In most cases 
     * except debugging, this is not useful.
     */
    public static final boolean PRESERVE_COOKIE_HEADER;

    /**
     * The list of separators that apply to version 0 cookies. To quote the
     * spec, these are comma, semi-colon and white-space. The HTTP spec
     * definition of linear white space is [CRLF] 1*( SP | HT )
     */
    private static final char[] V0_SEPARATORS = {',', ';', ' ', '\t'};
    private static final boolean[] V0_SEPARATOR_FLAGS = new boolean[128];

    /**
     * The list of separators that apply to version 1 cookies. This may or may
     * not include '/' depending on the setting of
     * {@link #FWD_SLASH_IS_SEPARATOR}.
     */
    private static final char[] HTTP_SEPARATORS;
    private static final boolean[] HTTP_SEPARATOR_FLAGS = new boolean[128];

    static {
        STRICT_SERVLET_COMPLIANCE = Boolean.parseBoolean(System.getProperty(
                "org.apache.catalina.STRICT_SERVLET_COMPLIANCE",
                "false"));

        ALLOW_EQUALS_IN_VALUE = Boolean.parseBoolean(System.getProperty(
                "org.apache.tomcat.util.http.ServerCookie.ALLOW_EQUALS_IN_VALUE",
                "false"));

        ALLOW_HTTP_SEPARATORS_IN_V0 = Boolean.parseBoolean(System.getProperty(
                "org.apache.tomcat.util.http.ServerCookie.ALLOW_HTTP_SEPARATORS_IN_V0",
                "false"));

        String alwaysAddExpires = System.getProperty(
        "org.apache.tomcat.util.http.ServerCookie.ALWAYS_ADD_EXPIRES");
        if (alwaysAddExpires == null) {
            ALWAYS_ADD_EXPIRES = !STRICT_SERVLET_COMPLIANCE;
        } else {
            ALWAYS_ADD_EXPIRES = Boolean.parseBoolean(alwaysAddExpires);
        }

        String preserveCookieHeader = System.getProperty(
                "org.apache.tomcat.util.http.ServerCookie.PRESERVE_COOKIE_HEADER");
        if (preserveCookieHeader == null) {
            PRESERVE_COOKIE_HEADER = STRICT_SERVLET_COMPLIANCE;
        } else {
            PRESERVE_COOKIE_HEADER = Boolean.parseBoolean(preserveCookieHeader);
        }

        String  fwdSlashIsSeparator = System.getProperty(
                "org.apache.tomcat.util.http.ServerCookie.FWD_SLASH_IS_SEPARATOR");
        if (fwdSlashIsSeparator == null) {
            FWD_SLASH_IS_SEPARATOR = STRICT_SERVLET_COMPLIANCE;
        } else {
            FWD_SLASH_IS_SEPARATOR = Boolean.parseBoolean(fwdSlashIsSeparator);
        }

        ALLOW_NAME_ONLY = Boolean.parseBoolean(System.getProperty(
                "org.apache.tomcat.util.http.ServerCookie.ALLOW_NAME_ONLY",
                "false"));


        /*
        Excluding the '/' char by default violates the RFC, but
        it looks like a lot of people put '/'
        in unquoted values: '/': ; //47
        '\t':9 ' ':32 '\"':34 '(':40 ')':41 ',':44 ':':58 ';':59 '<':60
        '=':61 '>':62 '?':63 '@':64 '[':91 '\\':92 ']':93 '{':123 '}':125
        */
        if (CookieSupport.FWD_SLASH_IS_SEPARATOR) {
            HTTP_SEPARATORS = new char[] { '\t', ' ', '\"', '(', ')', ',', '/',
                    ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '{', '}' };
        } else {
            HTTP_SEPARATORS = new char[] { '\t', ' ', '\"', '(', ')', ',',
                    ':', ';', '<', '=', '>', '?', '@', '[', '\\', ']', '{', '}' };
        }
        for (int i = 0; i < 128; i++) {
            V0_SEPARATOR_FLAGS[i] = false;
            HTTP_SEPARATOR_FLAGS[i] = false;
        }
        for (int i = 0; i < V0_SEPARATORS.length; i++) {
            V0_SEPARATOR_FLAGS[V0_SEPARATORS[i]] = true;
        }
        for (int i = 0; i < HTTP_SEPARATORS.length; i++) {
            HTTP_SEPARATOR_FLAGS[HTTP_SEPARATORS[i]] = true;
        }

    }

    // ----------------------------------------------------------------- Methods

    /**
     * Returns true if the byte is a separator as defined by V0 of the cookie
     * spec.
     */
    public static final boolean isV0Separator(final char c) {
        if (c < 0x20 || c >= 0x7f) {
            if (c != 0x09) {
                throw new IllegalArgumentException(
                        "Control character in cookie value or attribute.");
            }
        }

        return V0_SEPARATOR_FLAGS[c];
    }

    public static boolean isV0Token(String value) {
        if( value==null) {
            return false;
        }

        int i = 0;
        int len = value.length();

        if (alreadyQuoted(value)) {
            i++;
            len--;
        }

        for (; i < len; i++) {
            char c = value.charAt(i);

            if (isV0Separator(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the byte is a separator as defined by V1 of the cookie
     * spec, RFC2109.
     * @throws IllegalArgumentException if a control character was supplied as
     *         input
     */
    public static final boolean isHttpSeparator(final char c) {
        if (c < 0x20 || c >= 0x7f) {
            if (c != 0x09) {
                throw new IllegalArgumentException(
                        "Control character in cookie value or attribute.");
            }
        }

        return HTTP_SEPARATOR_FLAGS[c];
    }

    public static boolean isHttpToken(String value) {
        if( value==null) {
            return false;
        }

        int i = 0;
        int len = value.length();

        if (alreadyQuoted(value)) {
            i++;
            len--;
        }

        for (; i < len; i++) {
            char c = value.charAt(i);

            if (isHttpSeparator(c)) {
                return true;
            }
        }
        return false;
    }

    public static boolean alreadyQuoted (String value) {
        if (value==null || value.length() < 2) {
            return false;
        }
        return (value.charAt(0)=='\"' && value.charAt(value.length()-1)=='\"');
    }


    // ------------------------------------------------------------- Constructor
    private CookieSupport() {
        // Utility class. Don't allow instances to be created.
    }
}
