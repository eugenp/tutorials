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
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * Example listener for context-related application events, which were
 * introduced in the 2.3 version of the Servlet API.  This listener
 * merely documents the occurrence of such events in the application log
 * associated with our servlet context.
 *
 * @author Craig R. McClanahan
 */
public final class ContextListener
    implements ServletContextAttributeListener, ServletContextListener {


    // ----------------------------------------------------- Instance Variables


    /**
     * The servlet context with which we are associated.
     */
    private ServletContext context = null;


    // --------------------------------------------------------- Public Methods


    /**
     * Record the fact that a servlet context attribute was added.
     *
     * @param event The servlet context attribute event
     */
    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {

        log("attributeAdded('" + event.getName() + "', '" +
                event.getValue() + "')");

    }


    /**
     * Record the fact that a servlet context attribute was removed.
     *
     * @param event The servlet context attribute event
     */
    @Override
    public void attributeRemoved(ServletContextAttributeEvent event) {

        log("attributeRemoved('" + event.getName() + "', '" +
                event.getValue() + "')");

    }


    /**
     * Record the fact that a servlet context attribute was replaced.
     *
     * @param event The servlet context attribute event
     */
    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {

        log("attributeReplaced('" + event.getName() + "', '" +
                event.getValue() + "')");

    }


    /**
     * Record the fact that this web application has been destroyed.
     *
     * @param event The servlet context event
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {

        log("contextDestroyed()");
        this.context = null;

    }


    /**
     * Record the fact that this web application has been initialized.
     *
     * @param event The servlet context event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {

        this.context = event.getServletContext();
        log("contextInitialized()");

    }


    // -------------------------------------------------------- Private Methods


    /**
     * Log a message to the servlet context application log.
     *
     * @param message Message to be logged
     */
    private void log(String message) {

        if (context != null)
            context.log("ContextListener: " + message);
        else
            System.out.println("ContextListener: " + message);

    }

}
