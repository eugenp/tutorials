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

import java.io.IOException;
import java.net.URL;
import java.util.jar.JarFile;

public interface JarResource {
       
    /**     
     * @return The JarFile for this resource. A new instance of JarFile
     *         should be returned on each call.
     * @throws IOException
     */
    JarFile getJarFile() throws IOException;
       
    /**     
     * @return The URL of this resource. May or may not point 
     *         to the actual Jar file.    
     */
    String getUrl();
    
    /**     
     * @param name
     * @return The URL for the entry within this resource.
     */
    URL getEntry(String name);

}
