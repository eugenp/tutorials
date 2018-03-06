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


package org.apache.catalina.loader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Subclass implementation of <b>java.net.URLClassLoader</b>. There are no
 * functional differences between this class and <b>java.net.URLClassLoader</b>.
 *
 * @author Craig R. McClanahan
 * @author Remy Maucherat
 * @deprecated  Unused. Will be removed in Tomcat 8.0.x.
 */
@Deprecated
public class StandardClassLoader
    extends URLClassLoader
    implements StandardClassLoaderMBean {

    public StandardClassLoader(URL repositories[]) {
        super(repositories);
    }

    public StandardClassLoader(URL repositories[], ClassLoader parent) {
        super(repositories, parent);
    }

}

