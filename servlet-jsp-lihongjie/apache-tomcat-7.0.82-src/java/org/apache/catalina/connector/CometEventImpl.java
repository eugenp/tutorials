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
package org.apache.catalina.connector;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Globals;
import org.apache.catalina.comet.CometEvent;
import org.apache.tomcat.util.res.StringManager;

public class CometEventImpl implements CometEvent {


    /**
     * The string manager for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);


    public CometEventImpl(Request request, Response response) {
        this.request = request;
        this.response = response;
    }


    // ----------------------------------------------------- Instance Variables


    /**
     * Associated request.
     */
    protected Request request = null;


    /**
     * Associated response.
     */
    protected Response response = null;


    /**
     * Event type.
     */
    protected EventType eventType = EventType.BEGIN;


    /**
     * Event sub type.
     */
    protected EventSubType eventSubType = null;


    // --------------------------------------------------------- Public Methods

    /**
     * Clear the event.
     */
    public void clear() {
        request = null;
        response = null;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public void setEventSubType(EventSubType eventSubType) {
        this.eventSubType = eventSubType;
    }

    @Override
    public void close() throws IOException {
        if (request == null) {
            throw new IllegalStateException(sm.getString("cometEvent.nullRequest"));
        }
        request.finishRequest();
        response.finishResponse();
        if (request.isComet()) {
            request.cometClose();
        }
    }

    @Override
    public EventSubType getEventSubType() {
        return eventSubType;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }

    @Override
    public HttpServletRequest getHttpServletRequest() {
        return request.getRequest();
    }

    @Override
    public HttpServletResponse getHttpServletResponse() {
        return response.getResponse();
    }

    @Override
    public void setTimeout(int timeout) throws IOException, ServletException,
            UnsupportedOperationException {
        if (Boolean.TRUE.equals(request.getAttribute(Globals.COMET_TIMEOUT_SUPPORTED_ATTR))) {
            request.setAttribute(Globals.COMET_TIMEOUT_ATTR,
                    Integer.valueOf(timeout));
            if (request.isComet()) {
                request.setCometTimeout(timeout);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(super.toString());
        buf.append("[EventType:");
        buf.append(eventType);
        buf.append(", EventSubType:");
        buf.append(eventSubType);
        buf.append("]");
        return buf.toString();
    }

}
