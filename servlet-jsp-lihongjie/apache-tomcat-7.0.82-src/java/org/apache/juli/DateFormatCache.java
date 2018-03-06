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


package org.apache.juli;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * <p>Cache structure for SimpleDateFormat formatted timestamps based on
 * seconds.</p>
 *
 * <p>Millisecond formatting using S is not supported. You should add the
 * millisecond information after getting back the second formatting.</p>
 *
 * <p>The cache consists of entries for a consecutive range of
 * seconds. The length of the range is configurable. It is
 * implemented based on a cyclic buffer. New entries shift the range.</p>
 *
 * <p>The cache is not threadsafe. It can be used without synchronization
 * via thread local instances, or with synchronization as a global cache.</p>
 *
 * <p>The cache can be created with a parent cache to build a cache hierarchy.
 * Access to the parent cache is threadsafe.</p>
 */
public class DateFormatCache {

    private static final String msecPattern = "#";

    /* Timestamp format */
    private final String format;

    /* Number of cached entries */
    private int cacheSize = 0;

    private Cache cache;

    /**
     * Replace the millisecond formatting character 'S' by
     * some dummy characters in order to make the resulting
     * formatted time stamps cacheable. Our consumer might
     * choose to replace the dummy chars with the actual
     * milliseconds because that's relatively cheap.
     */
    private String tidyFormat(String format) {
        boolean escape = false;
        StringBuilder result = new StringBuilder();
        int len = format.length();
        char x;
        for (int i = 0; i < len; i++) {
            x = format.charAt(i);
            if (escape || x != 'S') {
                result.append(x);
            } else {
                result.append(msecPattern);
            }
            if (x == '\'') {
                escape = !escape;
            }
        }
        return result.toString();
    }

    public DateFormatCache(int size, String format, DateFormatCache parent) {
        cacheSize = size;
        this.format = tidyFormat(format);
        Cache parentCache = null;
        if (parent != null) {
            synchronized(parent) {
                parentCache = parent.cache;
            }
        }
        cache = new Cache(parentCache);
    }

    public String getFormat(long time) {
        return cache.getFormat(time);
    }

    private class Cache {

        /* Second formatted in most recent invocation */
        private long previousSeconds = Long.MIN_VALUE;
        /* Formatted timestamp generated in most recent invocation */
        private String previousFormat = "";

        /* First second contained in cache */
        private long first = Long.MIN_VALUE;
        /* Last second contained in cache */
        private long last = Long.MIN_VALUE;
        /* Index of "first" in the cyclic cache */
        private int offset = 0;
        /* Helper object to be able to call SimpleDateFormat.format(). */
        private final Date currentDate = new Date();

        private String cache[];
        private SimpleDateFormat formatter;

        private Cache parent = null;

        private Cache(Cache parent) {
            cache = new String[cacheSize];
            for (int i = 0; i < cacheSize; i++) {
                cache[i] = null;
            }
            formatter = new SimpleDateFormat(format, Locale.US);
            formatter.setTimeZone(TimeZone.getDefault());
            this.parent = parent;
        }

        private String getFormat(long time) {

            long seconds = time / 1000;

            /* First step: if we have seen this timestamp
               during the previous call, return the previous value. */
            if (seconds == previousSeconds) {
                return previousFormat;
            }

            /* Second step: Try to locate in cache */
            previousSeconds = seconds;
            int index = (offset + (int)(seconds - first)) % cacheSize;
            if (index < 0) {
                index += cacheSize;
            }
            if (seconds >= first && seconds <= last) {
                if (cache[index] != null) {
                    /* Found, so remember for next call and return.*/
                    previousFormat = cache[index];
                    return previousFormat;
                }

            /* Third step: not found in cache, adjust cache and add item */
            } else if (seconds >= last + cacheSize || seconds <= first - cacheSize) {
                first = seconds;
                last = first + cacheSize - 1;
                index = 0;
                offset = 0;
                for (int i = 1; i < cacheSize; i++) {
                    cache[i] = null;
                }
            } else if (seconds > last) {
                for (int i = 1; i < seconds - last; i++) {
                    cache[(index + cacheSize - i) % cacheSize] = null;
                }
                first = seconds - (cacheSize - 1);
                last = seconds;
                offset = (index + 1) % cacheSize;
            } else if (seconds < first) {
                for (int i = 1; i < first - seconds; i++) {
                    cache[(index + i) % cacheSize] = null;
                }
                first = seconds;
                last = seconds + (cacheSize - 1);
                offset = index;
            }

            /* Last step: format new timestamp either using
             * parent cache or locally. */
            if (parent != null) {
                synchronized(parent) {
                    previousFormat = parent.getFormat(time);
                }
            } else {
                currentDate.setTime(time);
                previousFormat = formatter.format(currentDate);
            }
            cache[index] = previousFormat;
            return previousFormat;
        }
    }
}
