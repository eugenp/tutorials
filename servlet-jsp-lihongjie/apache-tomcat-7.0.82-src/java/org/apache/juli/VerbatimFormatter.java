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

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Outputs the just the log message with no additional elements. Stack traces
 * are not logged. Log messages are separated by
 * <code>System.getProperty("line.separator")</code>. This is intended for use
 * by access logs and the like that need complete control over the output
 * format.
 */
public class VerbatimFormatter extends Formatter {

    private static final String LINE_SEP = System.getProperty("line.separator");

    @Override
    public String format(LogRecord record) {
        // Timestamp
        StringBuilder sb = new StringBuilder(record.getMessage());
        
        // New line for next record
        sb.append(LINE_SEP);

        return sb.toString();
    }

}
