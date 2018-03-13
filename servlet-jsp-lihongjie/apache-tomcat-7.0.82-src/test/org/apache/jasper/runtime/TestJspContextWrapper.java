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
package org.apache.jasper.runtime;

import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestJspContextWrapper extends TomcatBaseTest {

    @Test
    public void testELTagFilePageContext() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        // app dir is relative to server home
        StandardContext ctxt = (StandardContext) tomcat.addWebapp(
                null, "/test", appDir.getAbsolutePath());

        // This test needs the JSTL libraries
        File lib = new File("webapps/examples/WEB-INF/lib");
        ctxt.setAliases("/WEB-INF/lib=" + lib.getCanonicalPath());

        tomcat.start();
        
        ByteChunk out = new ByteChunk();

        int rc = getUrl("http://localhost:" + getPort() + "/test/bug5nnnn/bug58178.jsp", out, null);

        Assert.assertEquals(HttpServletResponse.SC_OK, rc);

        String result = out.toString();

        Assert.assertTrue(result, result.contains("PASS"));
    }
}
