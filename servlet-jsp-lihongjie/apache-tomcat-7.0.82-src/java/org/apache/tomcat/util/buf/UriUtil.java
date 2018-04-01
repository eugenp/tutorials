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

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * Utility class for working with URIs and URLs.
 */
public final class UriUtil {

    private static Pattern PATTERN_EXCLAMATION_MARK = Pattern.compile("!/");
    private static Pattern PATTERN_CARET = Pattern.compile("\\^/");
    private static Pattern PATTERN_ASTERISK = Pattern.compile("\\*/");

    private UriUtil() {
        // Utility class. Hide default constructor
    }


    /**
     * Determine if the character is allowed in the scheme of a URI.
     * See RFC 2396, Section 3.1
     *
     * @param c The character to test
     *
     * @return {@code true} if a the character is allowed, otherwise {code
     *         @false}
     */
    private static boolean isSchemeChar(char c) {
        return Character.isLetterOrDigit(c) || c == '+' || c == '-' || c == '.';
    }


    /**
     * Determine if a URI string has a <code>scheme</code> component.
     *
     * @param uri The URI to test
     *
     * @return {@code true} if a scheme is present, otherwise {code @false}
     */
    public static boolean hasScheme(CharSequence uri) {
        int len = uri.length();
        for(int i=0; i < len ; i++) {
            char c = uri.charAt(i);
            if(c == ':') {
                return i > 0;
            } else if(!UriUtil.isSchemeChar(c)) {
                return false;
            }
        }
        return false;
    }


    public static URL buildJarUrl(File jarFile) throws MalformedURLException {
        return buildJarUrl(jarFile, null);
    }


    public static URL buildJarUrl(File jarFile, String entryPath) throws MalformedURLException {
        return buildJarUrl(jarFile.toURI().toString(), entryPath);
    }


    public static URL buildJarUrl(String fileUrlString) throws MalformedURLException {
        return buildJarUrl(fileUrlString, null);
    }


    public static URL buildJarUrl(String fileUrlString, String entryPath) throws MalformedURLException {
        String safeString = makeSafeForJarUrl(fileUrlString);
        StringBuilder sb = new StringBuilder();
        sb.append("jar:");
        sb.append(safeString);
        sb.append("!/");
        if (entryPath != null) {
            sb.append(makeSafeForJarUrl(entryPath));
        }
        return new URL(sb.toString());
    }


    public static URL buildJarSafeUrl(File file) throws MalformedURLException {
        String safe = makeSafeForJarUrl(file.toURI().toString());
        return new URL(safe);
    }


    /*
     * When testing on markt's desktop each iteration was taking ~1420ns when
     * using String.replaceAll().
     *
     * Switching the implementation to use pre-compiled patterns and
     * Pattern.matcher(input).replaceAll(replacement) reduced this by ~10%.
     *
     * Note: Given the very small absolute time of a single iteration, even for
     *       a web application with 1000 JARs this is only going to add ~3ms.
     *       It is therefore unlikely that further optimisation will be
     *       necessary.
     */
    /*
     * Pulled out into a separate method in case we need to handle other unusual
     * sequences in the future.
     */
    private static String makeSafeForJarUrl(String input) {
        // Since "!/" has a special meaning in a JAR URL, make sure that the
        // sequence is properly escaped if present.
        String tmp = PATTERN_EXCLAMATION_MARK.matcher(input).replaceAll("%21/");
        // Tomcat's custom jar:war: URL handling treats */ and ^/ as special
        tmp = PATTERN_CARET.matcher(tmp).replaceAll("%5e/");
        return PATTERN_ASTERISK.matcher(tmp).replaceAll("%2a/");
    }
}
