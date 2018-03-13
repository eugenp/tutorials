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
 *
 */

package org.apache.tomcat.util.file;

import java.util.Set;

/**
 * <p>This is a utility class to match file globs.
 * The class has been derived from
 * org.apache.tools.ant.types.selectors.SelectorUtils.
 * </p>
 * <p>All methods are static.</p>
 */
public final class Matcher {

    /**
     * Tests whether or not a given file name matches any file name pattern in
     * the given set. The match is performed case-sensitively.
     *
     * @see #match(String, String, boolean)
     *
     * @param patternSet The pattern set to match against. Must not be
     *                <code>null</code>.
     * @param fileName The file name to match, as a String. Must not be
     *                <code>null</code>. It must be just a file name, without
     *                a path.
     *
     * @return <code>true</code> if any pattern in the set matches against the
     *         file name, or <code>false</code> otherwise.
     */
    public static boolean matchName(Set<String> patternSet, String fileName) {
        char[] fileNameArray = fileName.toCharArray();
        for (String pattern: patternSet) {
            if (match(pattern, fileNameArray, true)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Tests whether or not a string matches against a pattern.
     * The pattern may contain two special characters:<br>
     * '*' means zero or more characters<br>
     * '?' means one and only one character
     *
     * @param pattern The pattern to match against.
     *                Must not be <code>null</code>.
     * @param str     The string which must be matched against the
     *                pattern. Must not be <code>null</code>.
     * @param caseSensitive Whether or not matching should be performed
     *                        case sensitively.
     *
     *
     * @return <code>true</code> if the string matches against the pattern,
     *         or <code>false</code> otherwise.
     */
    public static boolean match(String pattern, String str,
            boolean caseSensitive) {

        return match(pattern, str.toCharArray(), caseSensitive);
    }


    /**
     * Tests whether or not a string matches against a pattern.
     * The pattern may contain two special characters:<br>
     * '*' means zero or more characters<br>
     * '?' means one and only one character
     *
     * @param pattern The pattern to match against.
     *                Must not be <code>null</code>.
     * @param strArr  The character array which must be matched against the
     *                pattern. Must not be <code>null</code>.
     * @param caseSensitive Whether or not matching should be performed
     *                        case sensitively.
     *
     *
     * @return <code>true</code> if the string matches against the pattern,
     *         or <code>false</code> otherwise.
     */
    private static boolean match(String pattern, char[] strArr,
                                boolean caseSensitive) {
        char[] patArr = pattern.toCharArray();
        int patIdxStart = 0;
        int patIdxEnd = patArr.length - 1;
        int strIdxStart = 0;
        int strIdxEnd = strArr.length - 1;
        char ch;

        boolean containsStar = false;
        for (int i = 0; i < patArr.length; i++) {
            if (patArr[i] == '*') {
                containsStar = true;
                break;
            }
        }

        if (!containsStar) {
            // No '*'s, so we make a shortcut
            if (patIdxEnd != strIdxEnd) {
                return false; // Pattern and string do not have the same size
            }
            for (int i = 0; i <= patIdxEnd; i++) {
                ch = patArr[i];
                if (ch != '?') {
                    if (different(caseSensitive, ch, strArr[i])) {
                        return false; // Character mismatch
                    }
                }
            }
            return true; // String matches against pattern
        }

        if (patIdxEnd == 0) {
            return true; // Pattern contains only '*', which matches anything
        }

        // Process characters before first star
        while (true) {
            ch = patArr[patIdxStart];
            if (ch == '*' || strIdxStart > strIdxEnd) {
                break;
            }
            if (ch != '?') {
                if (different(caseSensitive, ch, strArr[strIdxStart])) {
                    return false; // Character mismatch
                }
            }
            patIdxStart++;
            strIdxStart++;
        }
        if (strIdxStart > strIdxEnd) {
            // All characters in the string are used. Check if only '*'s are
            // left in the pattern. If so, we succeeded. Otherwise failure.
            return allStars(patArr, patIdxStart, patIdxEnd);
        }

        // Process characters after last star
        while (true) {
            ch = patArr[patIdxEnd];
            if (ch == '*' || strIdxStart > strIdxEnd) {
                break;
            }
            if (ch != '?') {
                if (different(caseSensitive, ch, strArr[strIdxEnd])) {
                    return false; // Character mismatch
                }
            }
            patIdxEnd--;
            strIdxEnd--;
        }
        if (strIdxStart > strIdxEnd) {
            // All characters in the string are used. Check if only '*'s are
            // left in the pattern. If so, we succeeded. Otherwise failure.
            return allStars(patArr, patIdxStart, patIdxEnd);
        }

        // process pattern between stars. padIdxStart and patIdxEnd point
        // always to a '*'.
        while (patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
            int patIdxTmp = -1;
            for (int i = patIdxStart + 1; i <= patIdxEnd; i++) {
                if (patArr[i] == '*') {
                    patIdxTmp = i;
                    break;
                }
            }
            if (patIdxTmp == patIdxStart + 1) {
                // Two stars next to each other, skip the first one.
                patIdxStart++;
                continue;
            }
            // Find the pattern between padIdxStart & padIdxTmp in str between
            // strIdxStart & strIdxEnd
            int patLength = (patIdxTmp - patIdxStart - 1);
            int strLength = (strIdxEnd - strIdxStart + 1);
            int foundIdx = -1;
            strLoop:
            for (int i = 0; i <= strLength - patLength; i++) {
                for (int j = 0; j < patLength; j++) {
                    ch = patArr[patIdxStart + j + 1];
                    if (ch != '?') {
                        if (different(caseSensitive, ch,
                                      strArr[strIdxStart + i + j])) {
                            continue strLoop;
                        }
                    }
                }

                foundIdx = strIdxStart + i;
                break;
            }

            if (foundIdx == -1) {
                return false;
            }

            patIdxStart = patIdxTmp;
            strIdxStart = foundIdx + patLength;
        }

        // All characters in the string are used. Check if only '*'s are left
        // in the pattern. If so, we succeeded. Otherwise failure.
        return allStars(patArr, patIdxStart, patIdxEnd);
    }

    private static boolean allStars(char[] chars, int start, int end) {
        for (int i = start; i <= end; ++i) {
            if (chars[i] != '*') {
                return false;
            }
        }
        return true;
    }

    private static boolean different(
        boolean caseSensitive, char ch, char other) {
        return caseSensitive
            ? ch != other
            : Character.toUpperCase(ch) != Character.toUpperCase(other);
    }

}
