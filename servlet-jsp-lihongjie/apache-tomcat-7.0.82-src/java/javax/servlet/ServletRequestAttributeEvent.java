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
 * This is the event class for notifications of changes to the attributes of the
 * servlet request in an application.
 * 
 * @see ServletRequestAttributeListener
 * @since Servlet 2.4
 */
public class ServletRequestAttributeEvent extends ServletRequestEvent {
    private static final long serialVersionUID = 1L;

    private final String name;
    private final Object value;

    /**
     * Construct a ServletRequestAttributeEvent giving the servlet context of
     * this web application, the ServletRequest whose attributes are changing
     * and the name and value of the attribute.
     * 
     * @param sc
     *            the ServletContext that is sending the event.
     * @param request
     *            the ServletRequest that is sending the event.
     * @param name
     *            the name of the request attribute.
     * @param value
     *            the value of the request attribute.
     */
    public ServletRequestAttributeEvent(ServletContext sc,
            ServletRequest request, String name, Object value) {
        super(sc, request);
        this.name = name;
        this.value = value;
    }

    /**
     * Return the name of the attribute that changed on the ServletRequest.
     * 
     * @return the name of the changed request attribute
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the value of the attribute that has been added, removed or
     * replaced. If the attribute was added, this is the value of the attribute.
     * If the attribute was removed, this is the value of the removed attribute.
     * If the attribute was replaced, this is the old value of the attribute.
     * 
     * @return the value of the changed request attribute
     */
    public Object getValue() {
        return this.value;
    }
}
