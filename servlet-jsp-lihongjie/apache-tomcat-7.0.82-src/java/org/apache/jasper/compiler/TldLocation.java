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

package org.apache.jasper.compiler;

public class TldLocation {
    
    private String entryName;
    private JarResource jar;
    
    public TldLocation(String entryName) {
        this(entryName, (JarResource)null);
    }
    
    public TldLocation(String entryName, String resourceUrl) {
        this(entryName, getJarResource(resourceUrl));
    }
    
    public TldLocation(String entryName, JarResource jarResource) {
        if (entryName == null) {
            throw new IllegalArgumentException("Tld name is required");
        }
        this.entryName = entryName;
        this.jar = jarResource;
    }
        
    private static JarResource getJarResource(String resourceUrl) {
        return (resourceUrl != null) ? new JarURLResource(resourceUrl) : null;
    }
    
    /**
     * @return The name of the tag library.
     */
    public String getName() {
        return entryName;
    }
    
    /**
     * 
     * @return The jar resource the tag library is contained in. 
     *         Might return null if the tag library is not contained in jar resource.
     */
    public JarResource getJarResource() {
        return jar;
    }
}
