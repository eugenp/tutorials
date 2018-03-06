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

package org.apache.catalina.manager;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.catalina.Session;
import org.apache.catalina.manager.util.SessionUtils;


/**
 * Helper JavaBean for JSPs, because JSTL 1.1/EL 2.0 is too dumb to
 * to what I need (call methods with parameters), or I am too dumb to use it correctly. :)
 * @author C&eacute;drik LIME
 */
public class JspHelper {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Public constructor, so that this class can be considered a JavaBean
     */
    private JspHelper() {
        super();
    }

    /**
     * Try to get user locale from the session, if possible.
     * IMPLEMENTATION NOTE: this method has explicit support for Tapestry 3 and Struts 1.x
     * @param in_session
     * @return String
     */
    public static String guessDisplayLocaleFromSession(Session in_session) {
        return localeToString(SessionUtils.guessLocaleFromSession(in_session));
    }
    private static String localeToString(Locale locale) {
        if (locale != null) {
            return escapeXml(locale.toString());//locale.getDisplayName();
        } else {
            return "";
        }
    }

    /**
     * Try to get user name from the session, if possible.
     * @param in_session
     * @return String
     */
    public static String guessDisplayUserFromSession(Session in_session) {
        Object user = SessionUtils.guessUserFromSession(in_session);
        return escapeXml(user);
    }


    public static String getDisplayCreationTimeForSession(Session in_session) {
        try {
            if (in_session.getCreationTime() == 0) {
                return "";
            }
            DateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
            return formatter.format(new Date(in_session.getCreationTime()));
        } catch (IllegalStateException ise) {
            //ignore: invalidated session
            return "";
        }
    }

    public static String getDisplayLastAccessedTimeForSession(Session in_session) {
        try {
            if (in_session.getLastAccessedTime() == 0) {
                return "";
            }
            DateFormat formatter = new SimpleDateFormat(DATE_TIME_FORMAT);
            return formatter.format(new Date(in_session.getLastAccessedTime()));
        } catch (IllegalStateException ise) {
            //ignore: invalidated session
            return "";
        }
    }

    public static String getDisplayUsedTimeForSession(Session in_session) {
        try {
            if (in_session.getCreationTime() == 0) {
                return "";
            }
        } catch (IllegalStateException ise) {
            //ignore: invalidated session
            return "";
        }
        return secondsToTimeString(SessionUtils.getUsedTimeForSession(in_session)/1000);
    }

    public static String getDisplayTTLForSession(Session in_session) {
        try {
            if (in_session.getCreationTime() == 0) {
                return "";
            }
        } catch (IllegalStateException ise) {
            //ignore: invalidated session
            return "";
        }
        return secondsToTimeString(SessionUtils.getTTLForSession(in_session)/1000);
    }

    public static String getDisplayInactiveTimeForSession(Session in_session) {
        try {
            if (in_session.getCreationTime() == 0) {
                return "";
            }
        } catch (IllegalStateException ise) {
            //ignore: invalidated session
            return "";
        }
        return secondsToTimeString(SessionUtils.getInactiveTimeForSession(in_session)/1000);
    }

    public static String secondsToTimeString(long in_seconds) {
        StringBuilder buff = new StringBuilder(9);
        if (in_seconds < 0) {
            buff.append('-');
            in_seconds = -in_seconds;
        }
        long rest = in_seconds;
        long hour = rest / 3600;
        rest = rest % 3600;
        long minute = rest / 60;
        rest = rest % 60;
        long second = rest;
        if (hour < 10) {
            buff.append('0');
        }
        buff.append(hour);
        buff.append(':');
        if (minute < 10) {
            buff.append('0');
        }
        buff.append(minute);
        buff.append(':');
        if (second < 10) {
            buff.append('0');
        }
        buff.append(second);
        return buff.toString();
    }


    /*
     * Following copied from org.apache.taglibs.standard.tag.common.core.Util v1.1.2
     */

    private static final int HIGHEST_SPECIAL = '>';
    private static char[][] specialCharactersRepresentation = new char[HIGHEST_SPECIAL + 1][];
    static {
        specialCharactersRepresentation['&'] = "&amp;".toCharArray();
        specialCharactersRepresentation['<'] = "&lt;".toCharArray();
        specialCharactersRepresentation['>'] = "&gt;".toCharArray();
        specialCharactersRepresentation['"'] = "&#034;".toCharArray();
        specialCharactersRepresentation['\''] = "&#039;".toCharArray();
    }

    public static String escapeXml(Object obj) {
        String value = null;
        try {
            value = (obj == null) ? null : obj.toString();
        } catch (Exception e) {
            // Ignore
        }
        return escapeXml(value);
    }

    /**
     * Performs the following substring replacements
     * (to facilitate output to XML/HTML pages):
     *
     *    & -> &amp;
     *    < -> &lt;
     *    > -> &gt;
     *    " -> &#034;
     *    ' -> &#039;
     *
     * See also OutSupport.writeEscapedXml().
     */
    @SuppressWarnings("null") // escapedBuffer cannot be null
    public static String escapeXml(String buffer) {
        if (buffer == null) {
            return "";
        }
        int start = 0;
        int length = buffer.length();
        char[] arrayBuffer = buffer.toCharArray();
        StringBuilder escapedBuffer = null;

        for (int i = 0; i < length; i++) {
            char c = arrayBuffer[i];
            if (c <= HIGHEST_SPECIAL) {
                char[] escaped = specialCharactersRepresentation[c];
                if (escaped != null) {
                    // create StringBuilder to hold escaped xml string
                    if (start == 0) {
                        escapedBuffer = new StringBuilder(length + 5);
                    }
                    // add unescaped portion
                    if (start < i) {
                        escapedBuffer.append(arrayBuffer,start,i-start);
                    }
                    start = i + 1;
                    // add escaped xml
                    escapedBuffer.append(escaped);
                }
            }
        }
        // no xml escaping was necessary
        if (start == 0) {
            return buffer;
        }
        // add rest of unescaped portion
        if (start < length) {
            escapedBuffer.append(arrayBuffer,start,length-start);
        }
        return escapedBuffer.toString();
    }

    public static String formatNumber(long number) {
        return NumberFormat.getNumberInstance().format(number);
    }
}
