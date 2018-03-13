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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Provides same information as default log format but on a single line to make
 * it easier to grep the logs. The only exception is stacktraces which are
 * always preceded by whitespace to make it simple to skip them.
 */
/*
 * Date processing based on AccessLogValve.
 */
public class OneLineFormatter extends Formatter {

    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final String ST_SEP = LINE_SEP + " ";

    /* Timestamp format */
    private static final String timeFormat = "dd-MMM-yyyy HH:mm:ss";

    /**
     * The size of our global date format cache
     */
    private static final int globalCacheSize = 30;

    /**
     * The size of our thread local date format cache
     */
    private static final int localCacheSize = 5;

    /**
     * Global date format cache.
     */
    private static final DateFormatCache globalDateCache =
            new DateFormatCache(globalCacheSize, timeFormat, null);

    /**
     * Thread local date format cache.
     */
    private static final ThreadLocal<DateFormatCache> localDateCache =
            new ThreadLocal<DateFormatCache>() {
        @Override
        protected DateFormatCache initialValue() {
            return new DateFormatCache(localCacheSize, timeFormat, globalDateCache);
        }
    };

    @Override
    public String format(LogRecord record) {
        StringBuilder sb = new StringBuilder();

        // Timestamp
        addTimestamp(sb, record.getMillis());

        // Severity
        sb.append(' ');
        sb.append(record.getLevel().getLocalizedName());

        // Thread
        sb.append(' ');
        sb.append('[');
        sb.append(Thread.currentThread().getName());
        sb.append(']');

        // Source
        sb.append(' ');
        sb.append(record.getSourceClassName());
        sb.append('.');
        sb.append(record.getSourceMethodName());

        // Message
        sb.append(' ');
        sb.append(formatMessage(record));

        // Stack trace
        if (record.getThrown() != null) {
            sb.append(ST_SEP);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            record.getThrown().printStackTrace(pw);
            pw.close();
            sb.append(sw.getBuffer());
        }

        // New line for next record
        sb.append(LINE_SEP);

        return sb.toString();
    }

    protected void addTimestamp(StringBuilder buf, long timestamp) {
        buf.append(localDateCache.get().getFormat(timestamp));
        long frac = timestamp % 1000;
        buf.append('.');
        if (frac < 100) {
            if (frac < 10) {
                buf.append('0');
                buf.append('0');
            } else {
                buf.append('0');
            }
        }
        buf.append(frac);
    }
}
