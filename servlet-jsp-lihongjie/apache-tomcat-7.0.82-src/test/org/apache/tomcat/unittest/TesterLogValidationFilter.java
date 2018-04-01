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
package org.apache.tomcat.unittest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;

public class TesterLogValidationFilter implements Filter {

    private final Level targetLevel;
    private final String targetMessage;
    private final String targetThrowableString;
    private final AtomicInteger messageCount = new AtomicInteger(0);


    public TesterLogValidationFilter(Level targetLevel, String targetMessage,
            String targetThrowableString) {
        this.targetLevel = targetLevel;
        this.targetMessage = targetMessage;
        this.targetThrowableString = targetThrowableString;
    }


    public int getMessageCount() {
        return messageCount.get();
    }


    @Override
    public boolean isLoggable(LogRecord record) {
        if (targetLevel != null) {
            Level level = record.getLevel();
            if (targetLevel != level) {
                return true;
            }
        }

        if (targetMessage != null) {
            String msg = record.getMessage();
            if (msg == null || !msg.contains(targetMessage)) {
                return true;
            }
        }

        if (targetThrowableString != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            record.getThrown().printStackTrace(pw);
            pw.close();
            String throwableString = sw.toString();
            if (!throwableString.contains(targetThrowableString)) {
                return true;
            }


        }

        messageCount.incrementAndGet();

        return true;
    }


    public static TesterLogValidationFilter add(Level targetLevel, String targetMessage,
            String targetThrowableString, String loggerName) {
        TesterLogValidationFilter f = new TesterLogValidationFilter(targetLevel, targetMessage,
                targetThrowableString);
        LogManager.getLogManager().getLogger(loggerName).setFilter(f);
        return f;
    }
}
