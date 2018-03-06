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
package org.apache.naming.resources;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Queue;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * A thread safe wrapper around {@link SimpleDateFormat} that does not make use
 * of ThreadLocal and - broadly - only creates enough SimpleDateFormat objects
 * to satisfy the concurrency requirements.
 */
public class ConcurrentDateFormat {

    private final String format;
    private final Locale locale;
    private final TimeZone timezone;
    private final Queue<SimpleDateFormat> queue = new ConcurrentLinkedQueue<SimpleDateFormat>();

    public static final String RFC1123_DATE = "EEE, dd MMM yyyy HH:mm:ss zzz";
    public static final TimeZone GMT = TimeZone.getTimeZone("GMT");

    private static final ConcurrentDateFormat FORMAT_RFC1123;

    static {
        FORMAT_RFC1123 = new ConcurrentDateFormat(RFC1123_DATE, Locale.US, GMT);
    }

    public static String formatRfc1123(Date date) {
        return FORMAT_RFC1123.format(date);
    }

    public ConcurrentDateFormat(String format, Locale locale,
            TimeZone timezone) {
        this.format = format;
        this.locale = locale;
        this.timezone = timezone;
        SimpleDateFormat initial = createInstance();
        queue.add(initial);
    }

    public String format(Date date) {
        SimpleDateFormat sdf = queue.poll();
        if (sdf == null) {
            sdf = createInstance();
        }
        String result = sdf.format(date);
        queue.add(sdf);
        return result;
    }

    public Date parse(String source) throws ParseException {
        SimpleDateFormat sdf = queue.poll();
        if (sdf == null) {
            sdf = createInstance();
        }
        Date result = sdf.parse(source);
        queue.add(sdf);
        return result;
    }

    private SimpleDateFormat createInstance() {
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        sdf.setTimeZone(timezone);
        return sdf;
    }
}
