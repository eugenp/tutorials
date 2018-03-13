/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.catalina.loader;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.naming.resources.FileDirContext;

public class TestVirtualWebappLoader extends TomcatBaseTest {

    @Test
    public void testModified() throws Exception {
        WebappLoader loader = new WebappLoader();
        assertNull(loader.getClassLoader());
        assertFalse(loader.modified());
    }

    @Test
    public void testStartInternal() throws Exception {
        Tomcat tomcat = getTomcatInstance();
        File appDir = new File("test/webapp-3.0");
        // Must have a real docBase - just use temp
        StandardContext ctx =
            (StandardContext)tomcat.addContext("",  appDir.getAbsolutePath());

        VirtualWebappLoader loader = new VirtualWebappLoader();

        loader.setContainer(ctx);
        ctx.setLoader(loader);
        ctx.setResources(new FileDirContext());
        ctx.resourcesStart();
        File dir = new File("test/webapp-3.0-fragments/WEB-INF/lib");
        loader.setVirtualClasspath(dir.getAbsolutePath() + "/*.jar");
        loader.start();
        String[] repos = loader.getRepositories();
        assertEquals(2,repos.length);
        loader.stop();
        // ToDo: Why doesn't remove repositories?
        repos = loader.getRepositories();
        assertEquals(2, repos.length);

        // no leak
        loader.start();
        repos = loader.getRepositories();
        assertEquals(2,repos.length);

        // clear loader
        ctx.setLoader(null);
        // see tearDown()!
        tomcat.start();
    }
}
