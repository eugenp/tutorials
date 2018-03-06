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


package org.apache.catalina.util;


import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.catalina.InstanceEvent;
import org.apache.catalina.InstanceListener;
import org.apache.catalina.Wrapper;


/**
 * Support class to assist in firing InstanceEvent notifications to
 * registered InstanceListeners.
 *
 * @author Craig R. McClanahan
 */
public final class InstanceSupport {


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new InstanceSupport object associated with the specified
     * Instance component.
     *
     * @param wrapper The component that will be the source
     *  of events that we fire
     */
    public InstanceSupport(Wrapper wrapper) {

        super();
        this.wrapper = wrapper;

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The set of registered InstanceListeners for event notifications.
     */
    private InstanceListener listeners[] = new InstanceListener[0];
    
    private final Object listenersLock = new Object(); // Lock object for changes to listeners


    /**
     * The source component for instance events that we will fire.
     */
    private Wrapper wrapper = null;


    // ------------------------------------------------------------- Properties


    /**
     * Return the Wrapper with which we are associated.
     */
    public Wrapper getWrapper() {

        return (this.wrapper);

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Add a lifecycle event listener to this component.
     *
     * @param listener The listener to add
     */
    public void addInstanceListener(InstanceListener listener) {

      synchronized (listenersLock) {
          InstanceListener results[] =
            new InstanceListener[listeners.length + 1];
          for (int i = 0; i < listeners.length; i++)
              results[i] = listeners[i];
          results[listeners.length] = listener;
          listeners = results;
      }

    }


    /**
     * Notify all lifecycle event listeners that a particular event has
     * occurred for this Container.  The default implementation performs
     * this notification synchronously using the calling thread.
     *
     * @param type Event type
     * @param filter The relevant Filter for this event
     */
    public void fireInstanceEvent(String type, Filter filter) {

        if (listeners.length == 0)
            return;

        InstanceEvent event = new InstanceEvent(wrapper, filter, type);
        InstanceListener interested[] = listeners;
        for (int i = 0; i < interested.length; i++)
            interested[i].instanceEvent(event);

    }


    /**
     * Notify all lifecycle event listeners that a particular event has
     * occurred for this Container.  The default implementation performs
     * this notification synchronously using the calling thread.
     *
     * @param type Event type
     * @param filter The relevant Filter for this event
     * @param exception Exception that occurred
     */
    public void fireInstanceEvent(String type, Filter filter,
                                  Throwable exception) {

        if (listeners.length == 0)
            return;

        InstanceEvent event = new InstanceEvent(wrapper, filter, type,
                                                exception);
        InstanceListener interested[] = listeners;
        for (int i = 0; i < interested.length; i++)
            interested[i].instanceEvent(event);

    }


    /**
     * Notify all lifecycle event listeners that a particular event has
     * occurred for this Container.  The default implementation performs
     * this notification synchronously using the calling thread.
     *
     * @param type Event type
     * @param filter The relevant Filter for this event
     * @param request The servlet request we are processing
     * @param response The servlet response we are processing
     */
    public void fireInstanceEvent(String type, Filter filter,
                                  ServletRequest request,
                                  ServletResponse response) {

        if (listeners.length == 0)
            return;

        InstanceEvent event = new InstanceEvent(wrapper, filter, type,
                                                request, response);
        InstanceListener interested[] = listeners;
        for (int i = 0; i < interested.length; i++)
            interested[i].instanceEvent(event);

    }


    /**
     * Notify all lifecycle event listeners that a particular event has
     * occurred for this Container.  The default implementation performs
     * this notification synchronously using the calling thread.
     *
     * @param type Event type
     * @param filter The relevant Filter for this event
     * @param request The servlet request we are processing
     * @param response The servlet response we are processing
     * @param exception Exception that occurred
     */
    public void fireInstanceEvent(String type, Filter filter,
                                  ServletRequest request,
                                  ServletResponse response,
                                  Throwable exception) {

        if (listeners.length == 0)
            return;

        InstanceEvent event = new InstanceEvent(wrapper, filter, type,
                                                request, response, exception);
        InstanceListener interested[] = listeners;
        for (int i = 0; i < interested.length; i++)
            interested[i].instanceEvent(event);

    }


    /**
     * Notify all lifecycle event listeners that a particular event has
     * occurred for this Container.  The default implementation performs
     * this notification synchronously using the calling thread.
     *
     * @param type Event type
     * @param servlet The relevant Servlet for this event
     */
    public void fireInstanceEvent(String type, Servlet servlet) {

        if (listeners.length == 0)
            return;

        InstanceEvent event = new InstanceEvent(wrapper, servlet, type);
        InstanceListener interested[] = listeners;
        for (int i = 0; i < interested.length; i++)
            interested[i].instanceEvent(event);

    }


    /**
     * Notify all lifecycle event listeners that a particular event has
     * occurred for this Container.  The default implementation performs
     * this notification synchronously using the calling thread.
     *
     * @param type Event type
     * @param servlet The relevant Servlet for this event
     * @param exception Exception that occurred
     */
    public void fireInstanceEvent(String type, Servlet servlet,
                                  Throwable exception) {

        if (listeners.length == 0)
            return;

        InstanceEvent event = new InstanceEvent(wrapper, servlet, type,
                                                exception);
        InstanceListener interested[] = listeners;
        for (int i = 0; i < interested.length; i++)
            interested[i].instanceEvent(event);

    }


    /**
     * Notify all lifecycle event listeners that a particular event has
     * occurred for this Container.  The default implementation performs
     * this notification synchronously using the calling thread.
     *
     * @param type Event type
     * @param servlet The relevant Servlet for this event
     * @param request The servlet request we are processing
     * @param response The servlet response we are processing
     */
    public void fireInstanceEvent(String type, Servlet servlet,
                                  ServletRequest request,
                                  ServletResponse response) {

        if (listeners.length == 0)
            return;

        InstanceEvent event = new InstanceEvent(wrapper, servlet, type,
                                                request, response);
        InstanceListener interested[] = listeners;
        for (int i = 0; i < interested.length; i++)
            interested[i].instanceEvent(event);

    }


    /**
     * Notify all lifecycle event listeners that a particular event has
     * occurred for this Container.  The default implementation performs
     * this notification synchronously using the calling thread.
     *
     * @param type Event type
     * @param servlet The relevant Servlet for this event
     * @param request The servlet request we are processing
     * @param response The servlet response we are processing
     * @param exception Exception that occurred
     */
    public void fireInstanceEvent(String type, Servlet servlet,
                                  ServletRequest request,
                                  ServletResponse response,
                                  Throwable exception) {

        if (listeners.length == 0)
            return;

        InstanceEvent event = new InstanceEvent(wrapper, servlet, type,
                                                request, response, exception);
        InstanceListener interested[] = listeners;
        for (int i = 0; i < interested.length; i++)
            interested[i].instanceEvent(event);

    }


    /**
     * Remove a lifecycle event listener from this component.
     *
     * @param listener The listener to remove
     */
    public void removeInstanceListener(InstanceListener listener) {

        synchronized (listenersLock) {
            int n = -1;
            for (int i = 0; i < listeners.length; i++) {
                if (listeners[i] == listener) {
                    n = i;
                    break;
                }
            }
            if (n < 0)
                return;
            InstanceListener results[] =
              new InstanceListener[listeners.length - 1];
            int j = 0;
            for (int i = 0; i < listeners.length; i++) {
                if (i != n)
                    results[j++] = listeners[i];
            }
            listeners = results;
        }

    }


}
