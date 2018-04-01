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

import java.util.Enumeration;

/**
 * 
 * A filter configuration object used by a servlet container to pass information
 * to a filter during initialization.
 * 
 * @see Filter
 * @since Servlet 2.3
 */
public interface FilterConfig {

    /**
     * Returns the filter-name of this filter as defined in the deployment
     * descriptor.
     */
    public String getFilterName();

    /**
     * Returns a reference to the {@link ServletContext} in which the caller is
     * executing.
     * 
     * @return {@link ServletContext} object, used by the caller to interact
     *         with its servlet container
     * 
     * @see ServletContext
     */
    public ServletContext getServletContext();

    /**
     * Returns a <code>String</code> containing the value of the named
     * initialization parameter, or <code>null</code> if the parameter does not
     * exist.
     * 
     * @param name
     *            <code>String</code> specifying the name of the initialization
     *            parameter
     * 
     * @return <code>String</code> containing the value of the initialization
     *         parameter
     */
    public String getInitParameter(String name);

    /**
     * Returns the names of the filter's initialization parameters as an
     * <code>Enumeration</code> of <code>String</code> objects, or an empty
     * <code>Enumeration</code> if the filter has no initialization parameters.
     * 
     * @return <code>Enumeration</code> of <code>String</code> objects
     *         containing the names of the filter's initialization parameters
     */
    public Enumeration<String> getInitParameterNames();

}
