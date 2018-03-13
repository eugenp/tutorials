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


import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;


/**
 * Wrapper around a <code>javax.servlet.ServletRequest</code>
 * that transforms an application request object (which might be the original
 * one passed to a servlet, or might be based on the 2.3
 * <code>javax.servlet.ServletRequestWrapper</code> class)
 * back into an internal <code>org.apache.catalina.Request</code>.
 * <p>
 * <strong>WARNING</strong>:  Due to Java's lack of support for multiple
 * inheritance, all of the logic in <code>ApplicationRequest</code> is
 * duplicated in <code>ApplicationHttpRequest</code>.  Make sure that you
 * keep these two classes in synchronization when making changes!
 *
 * @author Craig R. McClanahan
 */
class ApplicationRequest extends ServletRequestWrapper {


    // ------------------------------------------------------- Static Variables


    /**
     * The set of attribute names that are special for request dispatchers.
     */
    protected static final String specials[] =
    { RequestDispatcher.INCLUDE_REQUEST_URI,
      RequestDispatcher.INCLUDE_CONTEXT_PATH,
      RequestDispatcher.INCLUDE_SERVLET_PATH,
      RequestDispatcher.INCLUDE_PATH_INFO,
      RequestDispatcher.INCLUDE_QUERY_STRING,
      RequestDispatcher.FORWARD_REQUEST_URI, 
      RequestDispatcher.FORWARD_CONTEXT_PATH,
      RequestDispatcher.FORWARD_SERVLET_PATH, 
      RequestDispatcher.FORWARD_PATH_INFO,
      RequestDispatcher.FORWARD_QUERY_STRING };


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new wrapped request around the specified servlet request.
     *
     * @param request The servlet request being wrapped
     */
    public ApplicationRequest(ServletRequest request) {

        super(request);
        setRequest(request);

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The request attributes for this request.  This is initialized from the
     * wrapped request, but updates are allowed.
     */
    protected HashMap<String, Object> attributes =
        new HashMap<String, Object>();


    // ------------------------------------------------- ServletRequest Methods


    /**
     * Override the <code>getAttribute()</code> method of the wrapped request.
     *
     * @param name Name of the attribute to retrieve
     */
    @Override
    public Object getAttribute(String name) {

        synchronized (attributes) {
            return (attributes.get(name));
        }

    }


    /**
     * Override the <code>getAttributeNames()</code> method of the wrapped
     * request.
     */
    @Override
    public Enumeration<String> getAttributeNames() {

        synchronized (attributes) {
            return Collections.enumeration(attributes.keySet());
        }

    }


    /**
     * Override the <code>removeAttribute()</code> method of the
     * wrapped request.
     *
     * @param name Name of the attribute to remove
     */
    @Override
    public void removeAttribute(String name) {

        synchronized (attributes) {
            attributes.remove(name);
            if (!isSpecial(name))
                getRequest().removeAttribute(name);
        }

    }


    /**
     * Override the <code>setAttribute()</code> method of the
     * wrapped request.
     *
     * @param name Name of the attribute to set
     * @param value Value of the attribute to set
     */
    @Override
    public void setAttribute(String name, Object value) {

        synchronized (attributes) {
            attributes.put(name, value);
            if (!isSpecial(name))
                getRequest().setAttribute(name, value);
        }

    }


    // ------------------------------------------ ServletRequestWrapper Methods


    /**
     * Set the request that we are wrapping.
     *
     * @param request The new wrapped request
     */
    @Override
    public void setRequest(ServletRequest request) {

        super.setRequest(request);

        // Initialize the attributes for this request
        synchronized (attributes) {
            attributes.clear();
            Enumeration<String> names = request.getAttributeNames();
            while (names.hasMoreElements()) {
                String name = names.nextElement();
                Object value = request.getAttribute(name);
                attributes.put(name, value);
            }
        }

    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Is this attribute name one of the special ones that is added only for
     * included servlets?
     *
     * @param name Attribute name to be tested
     */
    protected boolean isSpecial(String name) {

        for (int i = 0; i < specials.length; i++) {
            if (specials[i].equals(name))
                return (true);
        }
        return (false);

    }


}
