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
package org.apache.catalina.valves;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.catalina.AccessLog;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;

public class TesterAccessLogValve extends ValveBase implements AccessLog {

    private static final boolean RELAX_TIMING = Boolean.getBoolean("tomcat.test.relaxTiming");

    // Timing tests need an error margin to prevent failures.
    private static final long ERROR_MARGIN = RELAX_TIMING ? 2000 : 100;

    private final Queue<Entry> entries = new ConcurrentLinkedQueue<Entry>();

    public TesterAccessLogValve() {
        // Async requests are supported
        super(true);
    }

    @Override
    public void log(Request request, Response response, long time) {
        (new Exception("Do log")).printStackTrace();
        entries.add(new Entry(request.getRequestURI(), response.getStatus(),
                time));
    }

    @Override
    public void setRequestAttributesEnabled(boolean requestAttributesEnabled) {
        // NOOP - test code
    }

    @Override
    public boolean getRequestAttributesEnabled() {
        // Always false - test code
        return false;
    }

    @Override
    public void invoke(Request request, Response response) throws IOException,
            ServletException {
        // Just invoke next - access logging happens via log() method
        getNext().invoke(request, response);
    }

    public void validateAccessLog(int count, int status, long minTime,
            long maxTime) throws Exception {

        // Wait (but not too long) until all expected entries appear (access log
        // entry will be made after response has been returned to user)
        for (int i = 0; i < 10 && entries.size() < count; i++) {
            Thread.sleep(100);
        }

        assertEquals(count, entries.size());
        for (Entry entry : entries) {
            assertEquals(status, entry.getStatus());
            assertTrue(entry.toString() + " duration is not >= " + (minTime - ERROR_MARGIN),
                    entry.getTime() >= minTime - ERROR_MARGIN);
            assertTrue(entry.toString() + " duration is not < " + (maxTime + ERROR_MARGIN),
                    entry.getTime() < maxTime + ERROR_MARGIN);
        }
    }

    public static class Entry {
        private final String uri;
        private final int status;
        private final long time;

        public Entry(String uri, int status, long time) {
            this.uri = uri;
            this.status = status;
            this.time = time;
        }

        public String getUri() {
            return uri;
        }

        public int getStatus() {
            return status;
        }

        public long getTime() {
            return time;
        }

        @Override
        public String toString() {
            return "Uri: " + uri + ", Status: " + status + ", Time: " + time;
        }
    }
}
