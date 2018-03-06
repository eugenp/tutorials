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

import java.util.Map;
import java.util.Set;

/**
 * TODO SERVLET3 - Add comments
 * @since Servlet 3.0
 */
public interface Registration {
    
    public String getName();
    
    public String getClassName();

    /**
     * 
     * @param name
     * @param value
     * @return TODO
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     */
    public boolean setInitParameter(String name, String value);

    public String getInitParameter(String name);
    
    /**
     * 
     * @param initParameters
     * @return TODO
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     */
    public Set<String> setInitParameters(Map<String,String> initParameters);

    public Map<String, String> getInitParameters();

    public interface Dynamic extends Registration {
        
        /**
         * 
         * @param isAsyncSupported
         * @throws IllegalStateException
         */
        public void setAsyncSupported(boolean isAsyncSupported);
    }
}
