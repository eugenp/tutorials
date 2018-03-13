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

import java.util.Collection;
import java.util.EnumSet;

/**
 * @since Servlet 3.0
 * TODO SERVLET3 - Add comments
 */
public interface FilterRegistration extends Registration {
    
    /**
     * 
     * @param dispatcherTypes
     * @param isMatchAfter
     * @param servletNames
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     */
    public void addMappingForServletNames(
            EnumSet<DispatcherType> dispatcherTypes,
            boolean isMatchAfter, String... servletNames);
    /**
     * 
     * @return TODO
     */
    public Collection<String> getServletNameMappings();
    
    /**
     * 
     * @param dispatcherTypes
     * @param isMatchAfter
     * @param urlPatterns
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     */
    public void addMappingForUrlPatterns(
            EnumSet<DispatcherType> dispatcherTypes,
            boolean isMatchAfter, String... urlPatterns);

    /**
     * 
     * @return TODO
     */
    public Collection<String> getUrlPatternMappings();
    
    public static interface Dynamic
    extends FilterRegistration, Registration.Dynamic {
        // No additional methods
    }
}
