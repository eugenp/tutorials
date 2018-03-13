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
package org.apache.tomcat.util.http.fileupload.util.mime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;

/**
 * Utility class to decode MIME texts.
 *
 * @since 1.3
 */
public final class MimeUtility {

    /**
     * The {@code US-ASCII} charset identifier constant.
     */
    private static final String US_ASCII_CHARSET = "US-ASCII";

    /**
     * The marker to indicate text is encoded with BASE64 algorithm.
     */
    private static final String BASE64_ENCODING_MARKER = "B";

    /**
     * The marker to indicate text is encoded with QuotedPrintable algorithm.
     */
    private static final String QUOTEDPRINTABLE_ENCODING_MARKER = "Q";

    /**
     * If the text contains any encoded tokens, those tokens will be marked with "=?".
     */
    private static final String ENCODED_TOKEN_MARKER = "=?";

    /**
     * If the text contains any encoded tokens, those tokens will terminate with "=?".
     */
    private static final String ENCODED_TOKEN_FINISHER = "?=";

    /**
     * The linear whitespace chars sequence.
     */
    private static final String LINEAR_WHITESPACE = " \t\r\n";

    /**
     * Mappings between MIME and Java charset.
     */
    private static final Map<String,String> MIME2JAVA =
            new HashMap<String,String>();

    static {
        MIME2JAVA.put("iso-2022-cn", "ISO2022CN");
        MIME2JAVA.put("iso-2022-kr", "ISO2022KR");
        MIME2JAVA.put("utf-8", "UTF8");
        MIME2JAVA.put("utf8", "UTF8");
        MIME2JAVA.put("ja_jp.iso2022-7", "ISO2022JP");
        MIME2JAVA.put("ja_jp.eucjp", "EUCJIS");
        MIME2JAVA.put("euc-kr", "KSC5601");
        MIME2JAVA.put("euckr", "KSC5601");
        MIME2JAVA.put("us-ascii", "ISO-8859-1");
        MIME2JAVA.put("x-us-ascii", "ISO-8859-1");
    }

    /**
     * Hidden constructor, this class must not be instantiated.
     */
    private MimeUtility() {
        // do nothing
    }

    /**
     * Decode a string of text obtained from a mail header into
     * its proper form.  The text generally will consist of a
     * string of tokens, some of which may be encoded using
     * base64 encoding.
     *
     * @param text   The text to decode.
     *
     * @return The decoded text string.
     * @throws UnsupportedEncodingException if the detected encoding in the input text is not supported.
     */
    public static String decodeText(String text) throws UnsupportedEncodingException {
        // if the text contains any encoded tokens, those tokens will be marked with "=?".  If the
        // source string doesn't contain that sequent, no decoding is required.
        if (text.indexOf(ENCODED_TOKEN_MARKER) < 0) {
            return text;
        }

        int offset = 0;
        int endOffset = text.length();

        int startWhiteSpace = -1;
        int endWhiteSpace = -1;

        StringBuilder decodedText = new StringBuilder(text.length());

        boolean previousTokenEncoded = false;

        while (offset < endOffset) {
            char ch = text.charAt(offset);

            // is this a whitespace character?
            if (LINEAR_WHITESPACE.indexOf(ch) != -1) { // whitespace found
                startWhiteSpace = offset;
                while (offset < endOffset) {
                    // step over the white space characters.
                    ch = text.charAt(offset);
                    if (LINEAR_WHITESPACE.indexOf(ch) != -1) { // whitespace found
                        offset++;
                    } else {
                        // record the location of the first non lwsp and drop down to process the
                        // token characters.
                        endWhiteSpace = offset;
                        break;
                    }
                }
            } else {
                // we have a word token.  We need to scan over the word and then try to parse it.
                int wordStart = offset;

                while (offset < endOffset) {
                    // step over the non white space characters.
                    ch = text.charAt(offset);
                    if (LINEAR_WHITESPACE.indexOf(ch) == -1) { // not white space
                        offset++;
                    } else {
                        break;
                    }

                    //NB:  Trailing whitespace on these header strings will just be discarded.
                }
                // pull out the word token.
                String word = text.substring(wordStart, offset);
                // is the token encoded?  decode the word
                if (word.startsWith(ENCODED_TOKEN_MARKER)) {
                    try {
                        // if this gives a parsing failure, treat it like a non-encoded word.
                        String decodedWord = decodeWord(word);

                        // are any whitespace characters significant?  Append 'em if we've got 'em.
                        if (!previousTokenEncoded && startWhiteSpace != -1) {
                            decodedText.append(text.substring(startWhiteSpace, endWhiteSpace));
                            startWhiteSpace = -1;
                        }
                        // this is definitely a decoded token.
                        previousTokenEncoded = true;
                        // and add this to the text.
                        decodedText.append(decodedWord);
                        // we continue parsing from here...we allow parsing errors to fall through
                        // and get handled as normal text.
                        continue;

                    } catch (ParseException e) {
                        // just ignore it, skip to next word
                    }
                }
                // this is a normal token, so it doesn't matter what the previous token was.  Add the white space
                // if we have it.
                if (startWhiteSpace != -1) {
                    decodedText.append(text.substring(startWhiteSpace, endWhiteSpace));
                    startWhiteSpace = -1;
                }
                // this is not a decoded token.
                previousTokenEncoded = false;
                decodedText.append(word);
            }
        }

        return decodedText.toString();
    }

    /**
     * Parse a string using the RFC 2047 rules for an "encoded-word"
     * type.  This encoding has the syntax:
     *
     * encoded-word = "=?" charset "?" encoding "?" encoded-text "?="
     *
     * @param word   The possibly encoded word value.
     *
     * @return The decoded word.
     * @throws ParseException
     * @throws UnsupportedEncodingException
     */
    private static String decodeWord(String word) throws ParseException, UnsupportedEncodingException {
        // encoded words start with the characters "=?".  If this not an encoded word, we throw a
        // ParseException for the caller.

        if (!word.startsWith(ENCODED_TOKEN_MARKER)) {
            throw new ParseException("Invalid RFC 2047 encoded-word: " + word);
        }

        int charsetPos = word.indexOf('?', 2);
        if (charsetPos == -1) {
            throw new ParseException("Missing charset in RFC 2047 encoded-word: " + word);
        }

        // pull out the character set information (this is the MIME name at this point).
        String charset = word.substring(2, charsetPos).toLowerCase(Locale.ENGLISH);

        // now pull out the encoding token the same way.
        int encodingPos = word.indexOf('?', charsetPos + 1);
        if (encodingPos == -1) {
            throw new ParseException("Missing encoding in RFC 2047 encoded-word: " + word);
        }

        String encoding = word.substring(charsetPos + 1, encodingPos);

        // and finally the encoded text.
        int encodedTextPos = word.indexOf(ENCODED_TOKEN_FINISHER, encodingPos + 1);
        if (encodedTextPos == -1) {
            throw new ParseException("Missing encoded text in RFC 2047 encoded-word: " + word);
        }

        String encodedText = word.substring(encodingPos + 1, encodedTextPos);

        // seems a bit silly to encode a null string, but easy to deal with.
        if (encodedText.length() == 0) {
            return "";
        }

        try {
            // the decoder writes directly to an output stream.
            ByteArrayOutputStream out = new ByteArrayOutputStream(encodedText.length());

            byte[] decodedData;
            // Base64 encoded?
            if (encoding.equals(BASE64_ENCODING_MARKER)) {
                decodedData = Base64.decodeBase64(encodedText);
            } else if (encoding.equals(QUOTEDPRINTABLE_ENCODING_MARKER)) { // maybe quoted printable.
                byte[] encodedData = encodedText.getBytes(US_ASCII_CHARSET);
                QuotedPrintableDecoder.decode(encodedData, out);
                decodedData = out.toByteArray();
            } else {
                throw new UnsupportedEncodingException("Unknown RFC 2047 encoding: " + encoding);
            }
            // Convert decoded byte data into a string.
            return new String(decodedData, javaCharset(charset));
        } catch (IOException e) {
            throw new UnsupportedEncodingException("Invalid RFC 2047 encoding");
        }
    }

    /**
     * Translate a MIME standard character set name into the Java
     * equivalent.
     *
     * @param charset The MIME standard name.
     *
     * @return The Java equivalent for this name.
     */
    private static String javaCharset(String charset) {
        // nothing in, nothing out.
        if (charset == null) {
            return null;
        }

        String mappedCharset = MIME2JAVA.get(charset.toLowerCase(Locale.ENGLISH));
        // if there is no mapping, then the original name is used.  Many of the MIME character set
        // names map directly back into Java.  The reverse isn't necessarily true.
        if (mappedCharset == null) {
            return charset;
        }
        return mappedCharset;
    }

}
