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

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogRecord;
/**
 * A {@link FileHandler} implementation that uses a queue of log entries.
 *
 * <p>Configuration properties are inherited from the {@link FileHandler}
 * class. This class does not add its own configuration properties for the
 * logging configuration, but relies on the following system properties
 * instead:</p>
 * 
 * <ul>
 *   <li><code>org.apache.juli.AsyncOverflowDropType</code>
 *    Default value: <code>1</code></li>
 *   <li><code>org.apache.juli.AsyncMaxRecordCount</code>
 *    Default value: <code>10000</code></li>
 *   <li><code>org.apache.juli.AsyncLoggerPollInterval</code>
 *    Default value: <code>1000</code></li>
 * </ul>
 * 
 * <p>See the System Properties page in the configuration reference of Tomcat.</p>
 * 
 * @author Filip Hanik
 *
 */
public class AsyncFileHandler extends FileHandler {

    public static final int OVERFLOW_DROP_LAST = 1;
    public static final int OVERFLOW_DROP_FIRST = 2;
    public static final int OVERFLOW_DROP_FLUSH = 3;
    public static final int OVERFLOW_DROP_CURRENT = 4;

    public static final int OVERFLOW_DROP_TYPE = Integer.parseInt(System.getProperty("org.apache.juli.AsyncOverflowDropType", "1"));
    public static final int DEFAULT_MAX_RECORDS = Integer.parseInt(System.getProperty("org.apache.juli.AsyncMaxRecordCount", "10000"));
    public static final int LOGGER_SLEEP_TIME = Integer.parseInt(System.getProperty("org.apache.juli.AsyncLoggerPollInterval", "1000"));

    protected static LinkedBlockingDeque<LogEntry> queue = new LinkedBlockingDeque<LogEntry>(DEFAULT_MAX_RECORDS);
    
    protected static LoggerThread logger = new LoggerThread();

    static {
        logger.start();
    }

    protected volatile boolean closed = false;

    public AsyncFileHandler() {
        this(null, null, null, DEFAULT_MAX_DAYS);
    }

    public AsyncFileHandler(String directory, String prefix, String suffix) {
        this(directory, prefix, suffix, DEFAULT_MAX_DAYS);
    }

    public AsyncFileHandler(String directory, String prefix, String suffix, int maxDays) {
        super(directory, prefix, suffix, maxDays);
        open();
    }

    @Override
    public void close() {
        if (closed) {
            return;
        }
        closed = true;
        super.close();
    }

    @Override
    protected void open() {
        if (!closed) {
            return;
        }
        closed = false;
        super.open();
    }


    @Override
    public void publish(LogRecord record) {
        if (!isLoggable(record)) {
            return;
        }
        LogEntry entry = new LogEntry(record, this);
        boolean added = false;
        try {
            while (!added && !queue.offer(entry)) {
                switch (OVERFLOW_DROP_TYPE) {
                    case OVERFLOW_DROP_LAST: {
                        //remove the last added element
                        queue.pollLast(); 
                        break;
                    }
                    case OVERFLOW_DROP_FIRST: {
                        //remove the first element in the queue
                        queue.pollFirst();
                        break;
                    }
                    case OVERFLOW_DROP_FLUSH: {
                        added = queue.offer(entry, 1000, TimeUnit.MILLISECONDS);
                        break;
                    }
                    case OVERFLOW_DROP_CURRENT: {
                        added = true;
                        break;
                    }
                }//switch
            }//while
        } catch (InterruptedException x) {
            //allow thread to be interrupted and back out of the publish operation
            //after this we clear the flag
            Thread.interrupted();
        }

    }

    protected void publishInternal(LogRecord record) {
        super.publish(record);
    }

    protected static class LoggerThread extends Thread {
        protected boolean run = true;
        public LoggerThread() {
            this.setDaemon(true);
            this.setName("AsyncFileHandlerWriter-" + System.identityHashCode(this));
        }

        @Override
        public void run() {
            while (run) {
                try {
                    LogEntry entry = queue.poll(LOGGER_SLEEP_TIME, TimeUnit.MILLISECONDS);
                    if (entry != null) {
                        entry.flush();
                    }
                } catch (InterruptedException x) {
                    Thread.interrupted();
                } catch (Exception x) {
                    x.printStackTrace();
                }
            }//while
        }
    }

    protected static class LogEntry {
        private LogRecord record;
        private AsyncFileHandler handler;
        public LogEntry(LogRecord record, AsyncFileHandler handler) {
            super();
            this.record = record;
            this.handler = handler;
        }

        public boolean flush() {
            if (handler.closed) {
                return false;
            } else {
                handler.publishInternal(record);
                return true;
            }
        }

    }

}
