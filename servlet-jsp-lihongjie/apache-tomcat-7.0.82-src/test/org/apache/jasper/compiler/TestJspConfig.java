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

import java.io.File;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestJspConfig extends TomcatBaseTest {

    @Test
    public void testServlet23NoEL() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =
            new File("test/webapp-2.3");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/el-as-literal.jsp");

        String result = res.toString();

        assertTrue(result.indexOf("<p>00-${'hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>01-#{'hello world'}</p>") > 0);
    }

    @Test
    public void testServlet24NoEL() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =
            new File("test/webapp-2.4");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/el-as-literal.jsp");

        String result = res.toString();

        assertTrue(result.indexOf("<p>00-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>01-#{'hello world'}</p>") > 0);
    }

    @Test
    public void testServlet25NoEL() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =
            new File("test/webapp-2.5");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/el-as-literal.jsp");

        String result = res.toString();

        assertTrue(result.indexOf("<p>00-hello world</p>") > 0);
    }

    @Test
    public void testServlet30NoEL() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =
            new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/el-as-literal.jsp");

        String result = res.toString();

        assertTrue(result.indexOf("<p>00-hello world</p>") > 0);
    }

}
