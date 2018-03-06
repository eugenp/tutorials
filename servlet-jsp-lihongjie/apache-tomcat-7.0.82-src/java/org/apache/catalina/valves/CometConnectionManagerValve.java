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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.comet.CometProcessor;
import org.apache.catalina.connector.CometEventImpl;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;


/**
 * <p>Implementation of a Valve that tracks Comet connections, and closes them
 * when the associated session expires or the webapp is reloaded.</p>
 *
 * <p>This Valve should be attached to a Context.</p>
 *
 * @author Remy Maucherat
 */
public class CometConnectionManagerValve extends ValveBase
    implements HttpSessionListener, LifecycleListener {

    //------------------------------------------------------ Constructor
    public CometConnectionManagerValve() {
        super(false);
    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The descriptive information related to this implementation.
     */
    protected static final String info =
        "org.apache.catalina.valves.CometConnectionManagerValve/1.0";


    /**
     * List of current Comet connections.
     */
    protected final List<Request> cometRequests =
        Collections.synchronizedList(new ArrayList<Request>());


    /**
     * Name of session attribute used to store list of comet connections.
     */
    protected final String cometRequestsAttribute =
        "org.apache.tomcat.comet.connectionList";


    /**
     * Start this component and implement the requirements
     * of {@link org.apache.catalina.util.LifecycleBase#startInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected synchronized void startInternal() throws LifecycleException {

        if (container instanceof Context) {
            container.addLifecycleListener(this);
        }

        setState(LifecycleState.STARTING);
    }


    /**
     * Stop this component and implement the requirements
     * of {@link org.apache.catalina.util.LifecycleBase#stopInternal()}.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    @Override
    protected synchronized void stopInternal() throws LifecycleException {

        setState(LifecycleState.STOPPING);

        if (container instanceof Context) {
            container.removeLifecycleListener(this);
        }
    }


    @Override
    public void lifecycleEvent(LifecycleEvent event) {
        if (Lifecycle.BEFORE_STOP_EVENT.equals(event.getType())) {
            // The container is getting stopped, close all current connections
            Iterator<Request> iterator = cometRequests.iterator();
            while (iterator.hasNext()) {
                Request request = iterator.next();
                // Remove the session tracking attribute as it isn't
                // serializable or required.
                HttpSession session = request.getSession(false);
                if (session != null) {
                    session.removeAttribute(cometRequestsAttribute);
                }
                // Close the comet connection
                CometEventImpl cometEvent = request.getEvent();
                try {
                    cometEvent.setEventType(CometEvent.EventType.END);
                    cometEvent.setEventSubType(
                            CometEvent.EventSubType.WEBAPP_RELOAD);
                    getNext().event(request, request.getResponse(), cometEvent);
                } catch (Exception e) {
                    container.getLogger().warn(
                            sm.getString("cometConnectionManagerValve.event"),
                            e);
                } finally {
                    try {
                        cometEvent.close();
                    } catch (IOException e) {
                        container.getLogger().warn(sm.getString(
                                "cometConnectionManagerValve.event"), e);
                    }
                }
            }
            cometRequests.clear();
        }
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Return descriptive information about this Valve implementation.
     */
    @Override
    public String getInfo() {
        return (info);
    }


    /**
     * Register requests for tracking, whenever needed.
     *
     * @param request The servlet request to be processed
     * @param response The servlet response to be created
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void invoke(Request request, Response response)
        throws IOException, ServletException {
        // Perform the request
        getNext().invoke(request, response);

        if (request.isComet() && !response.isClosed()) {
            // Start tracking this connection, since this is a
            // begin event, and Comet mode is on
            HttpSession session = request.getSession(true);

            // Track the connection for webapp reload
            cometRequests.add(request);

            // Track the connection for session expiration
            synchronized (session) {
                Request[] requests = (Request[])
                        session.getAttribute(cometRequestsAttribute);
                if (requests == null) {
                    requests = new Request[1];
                    requests[0] = request;
                    session.setAttribute(cometRequestsAttribute,
                            requests);
                } else {
                    Request[] newRequests =
                        new Request[requests.length + 1];
                    for (int i = 0; i < requests.length; i++) {
                        newRequests[i] = requests[i];
                    }
                    newRequests[requests.length] = request;
                    session.setAttribute(cometRequestsAttribute, newRequests);
                }
            }
        }

    }


    /**
     * Use events to update the connection state.
     *
     * @param request The servlet request to be processed
     * @param response The servlet response to be created
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void event(Request request, Response response, CometEvent event)
        throws IOException, ServletException {

        // Perform the request
        boolean ok = false;
        try {
            getNext().event(request, response, event);
            ok = true;
        } finally {
            if (!ok || response.isClosed()
                    || (event.getEventType() == CometEvent.EventType.END)
                    || (event.getEventType() == CometEvent.EventType.ERROR
                            && !(event.getEventSubType() ==
                                CometEvent.EventSubType.TIMEOUT))) {

                // Remove the connection from webapp reload tracking
                cometRequests.remove(request);

                // Remove connection from session expiration tracking
                // Note: can't get the session if it has been invalidated but
                // OK since session listener will have done clean-up
                HttpSession session = request.getSession(false);
                if (session != null) {
                    synchronized (session) {
                        Request[] reqs = null;
                        try {
                             reqs = (Request[])
                                session.getAttribute(cometRequestsAttribute);
                        } catch (IllegalStateException ise) {
                            // Ignore - session has been invalidated
                            // Listener will have cleaned up
                        }
                        if (reqs != null) {
                            boolean found = false;
                            for (int i = 0; !found && (i < reqs.length); i++) {
                                found = (reqs[i] == request);
                            }
                            if (found) {
                                if (reqs.length > 1) {
                                    Request[] newConnectionInfos =
                                        new Request[reqs.length - 1];
                                    int pos = 0;
                                    for (int i = 0; i < reqs.length; i++) {
                                        if (reqs[i] != request) {
                                            newConnectionInfos[pos++] = reqs[i];
                                        }
                                    }
                                    try {
                                        session.setAttribute(
                                                cometRequestsAttribute,
                                                newConnectionInfos);
                                    } catch (IllegalStateException ise) {
                                        // Ignore - session has been invalidated
                                        // Listener will have cleaned up
                                    }
                                } else {
                                    try {
                                        session.removeAttribute(
                                                cometRequestsAttribute);
                                    } catch (IllegalStateException ise) {
                                        // Ignore - session has been invalidated
                                        // Listener will have cleaned up
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // NOOP
    }


    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        // Close all Comet connections associated with this session
        Request[] reqs = (Request[])
            se.getSession().getAttribute(cometRequestsAttribute);
        if (reqs != null) {
            for (int i = 0; i < reqs.length; i++) {
                Request req = reqs[i];
                try {
                    CometEventImpl event = req.getEvent();
                    event.setEventType(CometEvent.EventType.END);
                    event.setEventSubType(CometEvent.EventSubType.SESSION_END);
                    ((CometProcessor)
                            req.getWrapper().getServlet()).event(event);
                    event.close();
                } catch (Exception e) {
                    req.getWrapper().getParent().getLogger().warn(sm.getString(
                            "cometConnectionManagerValve.listenerEvent"), e);
                }
            }
        }
    }

}
