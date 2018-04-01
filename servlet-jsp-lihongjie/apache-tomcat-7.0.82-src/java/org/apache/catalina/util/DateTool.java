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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 *  Common place for date utils.
 *
 * @author dac@eng.sun.com
 * @author Jason Hunter [jch@eng.sun.com]
 * @author James Todd [gonzo@eng.sun.com]
 * @author Costin Manolache
 * @author fhanik
 *
 * @deprecated  Mostly unused and will be removed in 8.0.x onwards
 */
@Deprecated
public class DateTool {

    /**
     * US locale - all HTTP dates are in English
     * @deprecated Use {@link Locale#US}
     */
    @Deprecated
    public static final Locale LOCALE_US = Locale.US;

    /**
     * GMT timezone - all HTTP dates are on GMT
     */
    public static final TimeZone GMT_ZONE = TimeZone.getTimeZone("GMT");

    /**
     * format for RFC 1123 date string -- "Sun, 06 Nov 1994 08:49:37 GMT"
     */
    public static final String RFC1123_PATTERN =
        "EEE, dd MMM yyyyy HH:mm:ss z";

    /** 
     * Format for http response header date field
     */
    public static final String HTTP_RESPONSE_DATE_HEADER =
        "EEE, dd MMM yyyy HH:mm:ss zzz";

    // format for RFC 1036 date string -- "Sunday, 06-Nov-94 08:49:37 GMT"
    private static final String rfc1036Pattern =
        "EEEEEEEEE, dd-MMM-yy HH:mm:ss z";

    // format for C asctime() date string -- "Sun Nov  6 08:49:37 1994"
    private static final String asctimePattern =
        "EEE MMM d HH:mm:ss yyyyy";

    /**
     * Pattern used for old cookies
     */
    public static final String OLD_COOKIE_PATTERN = "EEE, dd-MMM-yyyy HH:mm:ss z";

    /**
     * DateFormat to be used to format dates
     */
    public static final ThreadLocal<DateFormat> rfc1123Format = new ThreadLocal<DateFormat>() {
        @Override
        public DateFormat initialValue() {
            DateFormat result = new SimpleDateFormat(RFC1123_PATTERN, Locale.US);
            result.setTimeZone(GMT_ZONE);
            return result;
        }
    };

    /**
     * DateFormat to be used to format old netscape cookies
     */
    public static final ThreadLocal<DateFormat> oldCookieFormat = new ThreadLocal<DateFormat>() {
        @Override
        public DateFormat initialValue() {
            DateFormat result = new SimpleDateFormat(OLD_COOKIE_PATTERN, Locale.US);
            result.setTimeZone(GMT_ZONE);
            return result;
        }
    };


    public static final ThreadLocal<DateFormat> rfc1036Format = new ThreadLocal<DateFormat>() {
        @Override
        public DateFormat initialValue() {
            DateFormat result = new SimpleDateFormat(rfc1036Pattern, Locale.US);
            result.setTimeZone(GMT_ZONE);
            return result;
        }
    };

    public static final ThreadLocal<DateFormat> asctimeFormat = new ThreadLocal<DateFormat>() {
        @Override
        public DateFormat initialValue() {
            DateFormat result = new SimpleDateFormat(asctimePattern, Locale.US);
            result.setTimeZone(GMT_ZONE);
            return result;
        }
    };

}
