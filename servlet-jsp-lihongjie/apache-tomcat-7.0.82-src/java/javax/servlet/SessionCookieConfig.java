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
package javax.servlet;

/**
 * 
 * TODO SERVLET3 - Add comments
 * @since Servlet 3.0
 */
public interface SessionCookieConfig {
    
    /**
     * 
     * @param name
     * @throws IllegalStateException
     */
    public void setName(String name);
    
    public String getName();
    
    /**
     * 
     * @param domain
     * @throws IllegalStateException
     */
    public void setDomain(String domain);
    
    public String getDomain();
    
    /**
     * 
     * @param path
     * @throws IllegalStateException
     */
    public void setPath(String path);
    
    public String getPath();
    
    /**
     * 
     * @param comment
     * @throws IllegalStateException
     */
    public void setComment(String comment);
    
    public String getComment();
    
    /**
     * 
     * @param httpOnly
     * @throws IllegalStateException
     */
    public void setHttpOnly(boolean httpOnly);
    
    public boolean isHttpOnly();
    
    /**
     * 
     * @param secure
     * @throws IllegalStateException
     */
    public void setSecure(boolean secure);
    
    public boolean isSecure();

    /**
     * Sets the maximum age.
     * 
     * @param MaxAge the maximum age to set
     * @throws IllegalStateException
     */
    public void setMaxAge(int MaxAge);
    
    public int getMaxAge();
    
}
