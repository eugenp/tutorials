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
package javax.servlet;

/**
 * Events of this kind indicate lifecycle events for a ServletRequest. The
 * source of the event is the ServletContext of this web application.
 * 
 * @see ServletRequestListener
 * @since Servlet 2.4
 */
public class ServletRequestEvent extends java.util.EventObject {
    private static final long serialVersionUID = 1L;

    private final transient ServletRequest request;

    /**
     * Construct a ServletRequestEvent for the given ServletContext and
     * ServletRequest.
     * 
     * @param sc
     *            the ServletContext of the web application.
     * @param request
     *            the ServletRequest that is sending the event.
     */
    public ServletRequestEvent(ServletContext sc, ServletRequest request) {
        super(sc);
        this.request = request;
    }

    /**
     * Get the associated ServletRequest.
     * @return the ServletRequest that is changing.
     */
    public ServletRequest getServletRequest() {
        return this.request;
    }

    /**
     * Get the associated ServletContext.
     * @return the ServletContext that is changing.
     */
    public ServletContext getServletContext() {
        return (ServletContext) super.getSource();
    }
}
