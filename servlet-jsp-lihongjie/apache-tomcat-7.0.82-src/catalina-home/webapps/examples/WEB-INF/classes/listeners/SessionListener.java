/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package listeners;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Example listener for context-related application events, which were
 * introduced in the 2.3 version of the Servlet API. This listener merely
 * documents the occurrence of such events in the application log associated
 * with our servlet context.
 *
 * @author Craig R. McClanahan
 */
public final class SessionListener implements ServletContextListener,
        HttpSessionAttributeListener, HttpSessionListener {

    // ----------------------------------------------------- Instance Variables

    /**
     * The servlet context with which we are associated.
     */
    private ServletContext context = null;

    // --------------------------------------------------------- Public Methods

    /**
     * Record the fact that a servlet context attribute was added.
     *
     * @param event
     *            The session attribute event
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {

        log("attributeAdded('" + event.getSession().getId() + "', '"
                + event.getName() + "', '" + event.getValue() + "')");

    }

    /**
     * Record the fact that a servlet context attribute was removed.
     *
     * @param event
     *            The session attribute event
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {

        log("attributeRemoved('" + event.getSession().getId() + "', '"
                + event.getName() + "', '" + event.getValue() + "')");

    }

    /**
     * Record the fact that a servlet context attribute was replaced.
     *
     * @param event
     *            The session attribute event
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {

        log("attributeReplaced('" + event.getSession().getId() + "', '"
                + event.getName() + "', '" + event.getValue() + "')");

    }

    /**
     * Record the fact that this web application has been destroyed.
     *
     * @param event
     *            The servlet context event
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {

        log("contextDestroyed()");
        this.context = null;

    }

    /**
     * Record the fact that this web application has been initialized.
     *
     * @param event
     *            The servlet context event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {

        this.context = event.getServletContext();
        log("contextInitialized()");

    }

    /**
     * Record the fact that a session has been created.
     *
     * @param event
     *            The session event
     */
    @Override
    public void sessionCreated(HttpSessionEvent event) {

        log("sessionCreated('" + event.getSession().getId() + "')");

    }

    /**
     * Record the fact that a session has been destroyed.
     *
     * @param event
     *            The session event
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {

        log("sessionDestroyed('" + event.getSession().getId() + "')");

    }

    // -------------------------------------------------------- Private Methods

    /**
     * Log a message to the servlet context application log.
     *
     * @param message
     *            Message to be logged
     */
    private void log(String message) {

        if (context != null)
            context.log("SessionListener: " + message);
        else
            System.out.println("SessionListener: " + message);

    }

}
