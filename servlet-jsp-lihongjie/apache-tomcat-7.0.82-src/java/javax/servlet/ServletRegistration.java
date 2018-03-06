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
import java.util.Set;

/**
 * TODO SERVLET3 - Add comments
 * @since Servlet 3.0
 */
public interface ServletRegistration extends Registration {
    
    /**
     * TODO
     * @param urlPatterns The URL patterns that this Servlet should be mapped to
     * @return TODO
     * @throws IllegalArgumentException if urlPattern is null or empty
     * @throws IllegalStateException if the associated ServletContext has
     *                                  already been initialised
     */
    public Set<String> addMapping(String... urlPatterns);
    
    public Collection<String> getMappings();
    
    public String getRunAsRole();
    
    public static interface Dynamic
    extends ServletRegistration, Registration.Dynamic {
        public void setLoadOnStartup(int loadOnStartup);
        public void setMultipartConfig(MultipartConfigElement multipartConfig);
        public void setRunAsRole(String roleName);
        public Set<String> setServletSecurity(ServletSecurityElement constraint);
    }
}
