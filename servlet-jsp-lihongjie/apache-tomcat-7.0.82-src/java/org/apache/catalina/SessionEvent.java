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


package org.apache.catalina;


import java.util.EventObject;


/**
 * General event for notifying listeners of significant changes on a Session.
 *
 * @author Craig R. McClanahan
 */
public final class SessionEvent extends EventObject {

    private static final long serialVersionUID = 1L;


    /**
     * The event data associated with this event.
     */
    private Object data = null;


    /**
     * The Session on which this event occurred.
     */
    private Session session = null;


    /**
     * The event type this instance represents.
     */
    private String type = null;


    /**
     * Construct a new SessionEvent with the specified parameters.
     *
     * @param session Session on which this event occurred
     * @param type Event type
     * @param data Event data
     */
    public SessionEvent(Session session, String type, Object data) {

        super(session);
        this.session = session;
        this.type = type;
        this.data = data;

    }


    /**
     * Return the event data of this event.
     */
    public Object getData() {

        return (this.data);

    }


    /**
     * Return the Session on which this event occurred.
     */
    public Session getSession() {

        return (this.session);

    }


    /**
     * Return the event type of this event.
     */
    public String getType() {

        return (this.type);

    }


    /**
     * Return a string representation of this event.
     */
    @Override
    public String toString() {

        return ("SessionEvent['" + getSession() + "','" +
                getType() + "']");

    }


}
