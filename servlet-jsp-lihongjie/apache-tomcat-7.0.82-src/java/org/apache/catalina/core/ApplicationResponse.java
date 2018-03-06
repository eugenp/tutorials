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

import java.util.Locale;

import javax.servlet.ServletResponse;
import javax.servlet.ServletResponseWrapper;


/**
 * Wrapper around a <code>javax.servlet.ServletResponse</code>
 * that transforms an application response object (which might be the original
 * one passed to a servlet, or might be based on the 2.3
 * <code>javax.servlet.ServletResponseWrapper</code> class)
 * back into an internal <code>org.apache.catalina.Response</code>.
 * <p>
 * <strong>WARNING</strong>:  Due to Java's lack of support for multiple
 * inheritance, all of the logic in <code>ApplicationResponse</code> is
 * duplicated in <code>ApplicationHttpResponse</code>.  Make sure that you
 * keep these two classes in synchronization when making changes!
 *
 * @author Craig R. McClanahan
 */
class ApplicationResponse extends ServletResponseWrapper {


    // ----------------------------------------------------------- Constructors


    /**
     * Construct a new wrapped response around the specified servlet response.
     *
     * @param response The servlet response being wrapped
     */
    @Deprecated
    public ApplicationResponse(ServletResponse response) {

        this(response, false);

    }


    /**
     * Construct a new wrapped response around the specified servlet response.
     *
     * @param response The servlet response being wrapped
     * @param included <code>true</code> if this response is being processed
     *  by a <code>RequestDispatcher.include()</code> call
     */
    public ApplicationResponse(ServletResponse response, boolean included) {

        super(response);
        setIncluded(included);

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * Is this wrapped response the subject of an <code>include()</code>
     * call?
     */
    protected boolean included = false;


    // ------------------------------------------------ ServletResponse Methods


    /**
     * Disallow <code>reset()</code> calls on a included response.
     *
     * @exception IllegalStateException if the response has already
     *  been committed
     */
    @Override
    public void reset() {

        // If already committed, the wrapped response will throw ISE
        if (!included || getResponse().isCommitted())
            getResponse().reset();

    }


    /**
     * Disallow <code>setContentLength()</code> calls on an included response.
     *
     * @param len The new content length
     */
    @Override
    public void setContentLength(int len) {

        if (!included)
            getResponse().setContentLength(len);

    }


    /**
     * Disallow <code>setContentType()</code> calls on an included response.
     *
     * @param type The new content type
     */
    @Override
    public void setContentType(String type) {

        if (!included)
            getResponse().setContentType(type);

    }


    /**
     * Ignore <code>setLocale()</code> calls on an included response.
     *
     * @param loc The new locale
     */
    @Override
    public void setLocale(Locale loc) {
        if (!included)
            getResponse().setLocale(loc);
    }


    /**
     * Ignore <code>setBufferSize()</code> calls on an included response.
     *
     * @param size The buffer size
     */
    @Override
    public void setBufferSize(int size) {
        if (!included)
            getResponse().setBufferSize(size);
    }


    // ----------------------------------------- ServletResponseWrapper Methods


    /**
     * Set the response that we are wrapping.
     *
     * @param response The new wrapped response
     */
    @Override
    public void setResponse(ServletResponse response) {

        super.setResponse(response);

    }


    // -------------------------------------------------------- Package Methods


    /**
     * Return the included flag for this response.
     */
    @Deprecated
    boolean isIncluded() {

        return (this.included);

    }


    /**
     * Set the included flag for this response.
     *
     * @param included The new included flag
     */
    void setIncluded(boolean included) {

        this.included = included;

    }


}
