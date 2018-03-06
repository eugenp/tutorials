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
package org.apache.catalina.startup;

import java.io.File;
import java.util.Collection;

import javax.servlet.ServletContainerInitializer;

import org.junit.Test;

import org.apache.catalina.core.StandardContext;

public class TestWebappServiceLoader extends TomcatBaseTest {
    @Test
    public void testWebapp() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        File appDir = new File("test/webapp-3.0-fragments-empty-absolute-ordering");
        StandardContext ctxt = (StandardContext) tomcat.addContext(null, "/test", appDir.getAbsolutePath());
        ctxt.addLifecycleListener(new ContextConfig());
        tomcat.start();

        WebappServiceLoader<ServletContainerInitializer> loader =
                new WebappServiceLoader<ServletContainerInitializer>(ctxt);
        @SuppressWarnings("unused")
        Collection<ServletContainerInitializer> initializers = loader.load(ServletContainerInitializer.class);
    }
}
