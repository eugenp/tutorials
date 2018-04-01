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
package org.apache.catalina.core;

import java.util.Arrays;

import org.apache.catalina.AccessLog;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;

/**
 * A helper class that wraps several AccessLog instances.
 */
public class AccessLogAdapter implements AccessLog {

    private AccessLog[] logs;

    public AccessLogAdapter(AccessLog log) {
        if (log == null) {
            throw new NullPointerException();
        }
        logs = new AccessLog[] { log };
    }

    public void add(AccessLog log) {
        if (log == null) {
            throw new NullPointerException();
        }
        AccessLog newArray[] = Arrays.copyOf(logs, logs.length + 1);
        newArray[newArray.length - 1] = log;
        logs = newArray;
    }

    @Override
    public void log(Request request, Response response, long time) {
        for (AccessLog log: logs) {
            log.log(request, response, time);
        }
    }

    @Override
    public void setRequestAttributesEnabled(boolean requestAttributesEnabled) {
        // NOOP
    }

    @Override
    public boolean getRequestAttributesEnabled() {
        // NOOP. Could return logs[0].getRequestAttributesEnabled(), but I do
        // not see a use case for that.
        return false;
    }

}
