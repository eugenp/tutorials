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
package org.apache.tomcat.util.http.parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.res.StringManager;

/**
 * HTTP header value parser implementation. Parsing HTTP headers as per RFC2616
 * is not always as simple as it first appears. For headers that only use tokens
 * the simple approach will normally be sufficient. However, for the other
 * headers, while simple code meets 99.9% of cases, there are often some edge
 * cases that make things far more complicated.
 *
 * The purpose of this parser is to let the parser worry about the edge cases.
 * It provides tolerant (where safe to do so) parsing of HTTP header values
 * assuming that wrapped header lines have already been unwrapped. (The Tomcat
 * header processing code does the unwrapping.)
 *
 * Provides parsing of the following HTTP header values as per RFC 2616:
 * - Authorization for DIGEST authentication
 * - MediaType (used for Content-Type header)
 *
 * Support for additional headers will be provided as required.
 */
public class HttpParser {

    @SuppressWarnings("unused")  // Unused due to buggy client implementations
    private static final Integer FIELD_TYPE_TOKEN = Integer.valueOf(0);
    private static final Integer FIELD_TYPE_QUOTED_STRING = Integer.valueOf(1);
    private static final Integer FIELD_TYPE_TOKEN_OR_QUOTED_STRING = Integer.valueOf(2);
    private static final Integer FIELD_TYPE_LHEX = Integer.valueOf(3);
    private static final Integer FIELD_TYPE_QUOTED_TOKEN = Integer.valueOf(4);

    private static final Map<String,Integer> fieldTypes =
            new HashMap<String,Integer>();

    private static final StringManager sm = StringManager.getManager(HttpParser.class);

    private static final Log log = LogFactory.getLog(HttpParser.class);

    private static final int ARRAY_SIZE = 128;

    private static final boolean[] IS_CONTROL = new boolean[ARRAY_SIZE];
    private static final boolean[] IS_SEPARATOR = new boolean[ARRAY_SIZE];
    private static final boolean[] IS_TOKEN = new boolean[ARRAY_SIZE];
    private static final boolean[] IS_HEX = new boolean[ARRAY_SIZE];
    private static final boolean[] IS_NOT_REQUEST_TARGET = new boolean[ARRAY_SIZE];
    private static final boolean[] IS_HTTP_PROTOCOL = new boolean[ARRAY_SIZE];
    private static final boolean[] REQUEST_TARGET_ALLOW = new boolean[ARRAY_SIZE];

    static {
        // Digest field types.
        // Note: These are more relaxed than RFC2617. This adheres to the
        //       recommendation of RFC2616 that servers are tolerant of buggy
        //       clients when they can be so without ambiguity.
        fieldTypes.put("username", FIELD_TYPE_QUOTED_STRING);
        fieldTypes.put("realm", FIELD_TYPE_QUOTED_STRING);
        fieldTypes.put("nonce", FIELD_TYPE_QUOTED_STRING);
        fieldTypes.put("digest-uri", FIELD_TYPE_QUOTED_STRING);
        // RFC2617 says response is <">32LHEX<">. 32LHEX will also be accepted
        fieldTypes.put("response", FIELD_TYPE_LHEX);
        // RFC2617 says algorithm is token. <">token<"> will also be accepted
        fieldTypes.put("algorithm", FIELD_TYPE_QUOTED_TOKEN);
        fieldTypes.put("cnonce", FIELD_TYPE_QUOTED_STRING);
        fieldTypes.put("opaque", FIELD_TYPE_QUOTED_STRING);
        // RFC2617 says qop is token. <">token<"> will also be accepted
        fieldTypes.put("qop", FIELD_TYPE_QUOTED_TOKEN);
        // RFC2617 says nc is 8LHEX. <">8LHEX<"> will also be accepted
        fieldTypes.put("nc", FIELD_TYPE_LHEX);

        String prop = System.getProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow");
        if (prop != null) {
            for (int i = 0; i < prop.length(); i++) {
                char c = prop.charAt(i);
                if (c == '{' || c == '}' || c == '|') {
                    REQUEST_TARGET_ALLOW[c] = true;
                } else {
                    log.warn(sm.getString("httpparser.invalidRequestTargetCharacter",
                            Character.valueOf(c)));
                }
            }
        }

        for (int i = 0; i < ARRAY_SIZE; i++) {
            // Control> 0-31, 127
            if (i < 32 || i == 127) {
                IS_CONTROL[i] = true;
            }

            // Separator
            if (    i == '(' || i == ')' || i == '<' || i == '>'  || i == '@'  ||
                    i == ',' || i == ';' || i == ':' || i == '\\' || i == '\"' ||
                    i == '/' || i == '[' || i == ']' || i == '?'  || i == '='  ||
                    i == '{' || i == '}' || i == ' ' || i == '\t') {
                IS_SEPARATOR[i] = true;
            }

            // Token: Anything 0-127 that is not a control and not a separator
            if (!IS_CONTROL[i] && !IS_SEPARATOR[i] && i < 128) {
                IS_TOKEN[i] = true;
            }

            // Hex: 0-9, a-f, A-F
            if ((i >= '0' && i <='9') || (i >= 'a' && i <= 'f') || (i >= 'A' && i <= 'F')) {
                IS_HEX[i] = true;
            }

            // Not valid for request target.
            // Combination of multiple rules from RFC7230 and RFC 3986. Must be
            // ASCII, no controls plus a few additional characters excluded
            if (IS_CONTROL[i] || i > 127 ||
                    i == ' ' || i == '\"' || i == '#' || i == '<' || i == '>' || i == '\\' ||
                    i == '^' || i == '`'  || i == '{' || i == '|' || i == '}') {
                if (!REQUEST_TARGET_ALLOW[i]) {
                    IS_NOT_REQUEST_TARGET[i] = true;
                }
            }

            // Not valid for HTTP protocol
            // "HTTP/" DIGIT "." DIGIT
            if (i == 'H' || i == 'T' || i == 'P' || i == '/' || i == '.' || (i >= '0' && i <= '9')) {
                IS_HTTP_PROTOCOL[i] = true;
            }
        }
    }

    /**
     * Parses an HTTP Authorization header for DIGEST authentication as per RFC
     * 2617 section 3.2.2.
     *
     * @param input The header value to parse
     *
     * @return  A map of directives and values as {@link String}s or
     *          <code>null</code> if a parsing error occurs. Although the
     *          values returned are {@link String}s they will have been
     *          validated to ensure that they conform to RFC 2617.
     *
     * @throws IllegalArgumentException If the header does not conform to RFC
     *                                  2617
     * @throws IOException If an error occurs while reading the input
     */
    public static Map<String,String> parseAuthorizationDigest (
            StringReader input) throws IllegalArgumentException, IOException {

        Map<String,String> result = new HashMap<String,String>();

        if (skipConstant(input, "Digest") != SkipConstantResult.FOUND) {
            return null;
        }
        // All field names are valid tokens
        String field = readToken(input);
        if (field == null) {
            return null;
        }
        while (!field.equals("")) {
            if (skipConstant(input, "=") != SkipConstantResult.FOUND) {
                return null;
            }
            String value = null;
            Integer type = fieldTypes.get(field.toLowerCase(Locale.ENGLISH));
            if (type == null) {
                // auth-param = token "=" ( token | quoted-string )
                type = FIELD_TYPE_TOKEN_OR_QUOTED_STRING;
            }
            switch (type.intValue()) {
                case 0:
                    // FIELD_TYPE_TOKEN
                    value = readToken(input);
                    break;
                case 1:
                    // FIELD_TYPE_QUOTED_STRING
                    value = readQuotedString(input, false);
                    break;
                case 2:
                    // FIELD_TYPE_TOKEN_OR_QUOTED_STRING
                    value = readTokenOrQuotedString(input, false);
                    break;
                case 3:
                    // FIELD_TYPE_LHEX
                    value = readLhex(input);
                    break;
                case 4:
                    // FIELD_TYPE_QUOTED_TOKEN
                    value = readQuotedToken(input);
                    break;
                default:
                    // Error
                    throw new IllegalArgumentException(
                            "TODO i18n: Unsupported type");
            }

            if (value == null) {
                return null;
            }
            result.put(field, value);

            if (skipConstant(input, ",") == SkipConstantResult.NOT_FOUND) {
                return null;
            }
            field = readToken(input);
            if (field == null) {
                return null;
            }
        }

        return result;
    }

    public static MediaType parseMediaType(StringReader input)
            throws IOException {

        // Type (required)
        String type = readToken(input);
        if (type == null || type.length() == 0) {
            return null;
        }

        if (skipConstant(input, "/") == SkipConstantResult.NOT_FOUND) {
            return null;
        }

        // Subtype (required)
        String subtype = readToken(input);
        if (subtype == null || subtype.length() == 0) {
            return null;
        }

        LinkedHashMap<String,String> parameters =
                new LinkedHashMap<String,String>();

        SkipConstantResult lookForSemiColon = skipConstant(input, ";");
        if (lookForSemiColon == SkipConstantResult.NOT_FOUND) {
            return null;
        }
        while (lookForSemiColon == SkipConstantResult.FOUND) {
            String attribute = readToken(input);

            String value = "";
            if (skipConstant(input, "=") == SkipConstantResult.FOUND) {
                value = readTokenOrQuotedString(input, true);
            }

            if (attribute != null) {
                parameters.put(attribute.toLowerCase(Locale.ENGLISH), value);
            }

            lookForSemiColon = skipConstant(input, ";");
            if (lookForSemiColon == SkipConstantResult.NOT_FOUND) {
                return null;
            }
        }

        return new MediaType(type, subtype, parameters);
    }


    public static String unquote(String input) {
        if (input == null || input.length() < 2) {
            return input;
        }

        int start;
        int end;

        // Skip surrounding quotes if there are any
        if (input.charAt(0) == '"') {
            start = 1;
            end = input.length() - 1;
        } else {
            start = 0;
            end = input.length();
        }

        StringBuilder result = new StringBuilder();
        for (int i = start ; i < end; i++) {
            char c = input.charAt(i);
            if (input.charAt(i) == '\\') {
                i++;
                result.append(input.charAt(i));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }


    public static boolean isToken(int c) {
        // Fast for correct values, slower for incorrect ones
        try {
            return IS_TOKEN[c];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
    }


    public static boolean isHex(int c) {
        // Fast for correct values, slower for some incorrect ones
        try {
            return IS_HEX[c];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
    }


    public static boolean isNotRequestTarget(int c) {
        // Fast for valid request target characters, slower for some incorrect
        // ones
        try {
            return IS_NOT_REQUEST_TARGET[c];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return true;
        }
    }


    public static boolean isHttpProtocol(int c) {
        // Fast for valid HTTP protocol characters, slower for some incorrect
        // ones
        try {
            return IS_HTTP_PROTOCOL[c];
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
    }


    // Skip any LWS and return the next char
    private static int skipLws(StringReader input, boolean withReset)
            throws IOException {

        if (withReset) {
            input.mark(1);
        }
        int c = input.read();

        while (c == 32 || c == 9 || c == 10 || c == 13) {
            if (withReset) {
                input.mark(1);
            }
            c = input.read();
        }

        if (withReset) {
            input.reset();
        }
        return c;
    }

    private static SkipConstantResult skipConstant(StringReader input,
            String constant) throws IOException {
        int len = constant.length();

        int c = skipLws(input, false);

        for (int i = 0; i < len; i++) {
            if (i == 0 && c == -1) {
                return SkipConstantResult.EOF;
            }
            if (c != constant.charAt(i)) {
                input.skip(-(i + 1));
                return SkipConstantResult.NOT_FOUND;
            }
            if (i != (len - 1)) {
                c = input.read();
            }
        }
        return SkipConstantResult.FOUND;
    }

    /**
     * @return  the token if one was found, the empty string if no data was
     *          available to read or <code>null</code> if data other than a
     *          token was found
     */
    private static String readToken(StringReader input) throws IOException {
        StringBuilder result = new StringBuilder();

        int c = skipLws(input, false);

        while (c != -1 && isToken(c)) {
            result.append((char) c);
            c = input.read();
        }
        // Skip back so non-token character is available for next read
        input.skip(-1);

        if (c != -1 && result.length() == 0) {
            return null;
        } else {
            return result.toString();
        }
    }

    /**
     * @return the quoted string if one was found, null if data other than a
     *         quoted string was found or null if the end of data was reached
     *         before the quoted string was terminated
     */
    private static String readQuotedString(StringReader input,
            boolean returnQuoted) throws IOException {

        int c = skipLws(input, false);

        if (c != '"') {
            return null;
        }

        StringBuilder result = new StringBuilder();
        if (returnQuoted) {
            result.append('\"');
        }
        c = input.read();

        while (c != '"') {
            if (c == -1) {
                return null;
            } else if (c == '\\') {
                c = input.read();
                if (returnQuoted) {
                    result.append('\\');
                }
                result.append(c);
            } else {
                result.append((char) c);
            }
            c = input.read();
        }
        if (returnQuoted) {
            result.append('\"');
        }

        return result.toString();
    }

    private static String readTokenOrQuotedString(StringReader input,
            boolean returnQuoted) throws IOException {

        // Go back so first non-LWS character is available to be read again
        int c = skipLws(input, true);

        if (c == '"') {
            return readQuotedString(input, returnQuoted);
        } else {
            return readToken(input);
        }
    }

    /**
     * Token can be read unambiguously with or without surrounding quotes so
     * this parsing method for token permits optional surrounding double quotes.
     * This is not defined in any RFC. It is a special case to handle data from
     * buggy clients (known buggy clients for DIGEST auth include Microsoft IE 8
     * &amp; 9, Apple Safari for OSX and iOS) that add quotes to values that
     * should be tokens.
     *
     * @return the token if one was found, null if data other than a token or
     *         quoted token was found or null if the end of data was reached
     *         before a quoted token was terminated
     */
    private static String readQuotedToken(StringReader input)
            throws IOException {

        StringBuilder result = new StringBuilder();
        boolean quoted = false;

        int c = skipLws(input, false);

        if (c == '"') {
            quoted = true;
        } else if (c == -1 || !isToken(c)) {
            return null;
        } else {
            result.append((char) c);
        }
        c = input.read();

        while (c != -1 && isToken(c)) {
            result.append((char) c);
            c = input.read();
        }

        if (quoted) {
            if (c != '"') {
                return null;
            }
        } else {
            // Skip back so non-token character is available for next read
            input.skip(-1);
        }

        if (c != -1 && result.length() == 0) {
            return null;
        } else {
            return result.toString();
        }
    }

    /**
     * LHEX can be read unambiguously with or without surrounding quotes so this
     * parsing method for LHEX permits optional surrounding double quotes. Some
     * buggy clients (libwww-perl for DIGEST auth) are known to send quoted LHEX
     * when the specification requires just LHEX.
     *
     * <p>
     * LHEX are, literally, lower-case hexadecimal digits. This implementation
     * allows for upper-case digits as well, converting the returned value to
     * lower-case.
     *
     * @return  the sequence of LHEX (minus any surrounding quotes) if any was
     *          found, or <code>null</code> if data other LHEX was found
     */
    private static String readLhex(StringReader input)
            throws IOException {

        StringBuilder result = new StringBuilder();
        boolean quoted = false;

        int c = skipLws(input, false);

        if (c == '"') {
            quoted = true;
        } else if (c == -1 || !isHex(c)) {
            return null;
        } else {
            if ('A' <= c && c <= 'F') {
                c -= ('A' - 'a');
            }
            result.append((char) c);
        }
        c = input.read();

        while (c != -1 && isHex(c)) {
            if ('A' <= c && c <= 'F') {
                c -= ('A' - 'a');
            }
            result.append((char) c);
            c = input.read();
        }

        if (quoted) {
            if (c != '"') {
                return null;
            }
        } else {
            // Skip back so non-hex character is available for next read
            input.skip(-1);
        }

        if (c != -1 && result.length() == 0) {
            return null;
        } else {
            return result.toString();
        }
    }

    private static enum SkipConstantResult {
        FOUND,
        NOT_FOUND,
        EOF
    }
}
