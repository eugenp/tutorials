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
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.jar.JarFile;

public class JarURLResource implements JarResource {
    
    private String jarUrl;
    
    public JarURLResource(URL jarURL) {
        this(jarURL.toExternalForm());
    }
    
    public JarURLResource(String jarUrl) {
        this.jarUrl = jarUrl;
    }
    
    @Override
    public JarFile getJarFile() throws IOException {
        URL jarFileUrl = new URL("jar:" + jarUrl + "!/");
        JarURLConnection conn = (JarURLConnection) jarFileUrl.openConnection();
        conn.setUseCaches(false);
        conn.connect();
        return conn.getJarFile();
    }
       
    @Override
    public String getUrl() {
        return jarUrl;
    }
    
    @Override
    public URL getEntry(String name) {
        try {
            return new URL("jar:" + jarUrl + "!/" + name);
        } catch (MalformedURLException e) {
            throw new RuntimeException("", e);
        }
    }
}
