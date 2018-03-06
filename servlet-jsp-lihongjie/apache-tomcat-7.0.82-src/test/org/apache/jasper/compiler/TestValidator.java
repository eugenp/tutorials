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
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

public class TestValidator extends TomcatBaseTest {

    @Test
    public void testBug47331() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =
            new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        int rc = getUrl("http://localhost:" + getPort() +
                "/test/bug47331.jsp", new ByteChunk(), null);

        assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, rc);
    }

    @Test
    public void testTldVersions23() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =
            new File("test/webapp-2.3");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/tld-versions.jsp");

        String result = res.toString();

        assertTrue(result.indexOf("<p>${'00-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>#{'01-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>${'02-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>#{'03-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>${'04-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>#{'05-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>${'06-hello world'}</p>") > 0);
    }

    @Test
    public void testTldVersions24() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =
            new File("test/webapp-2.4");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/tld-versions.jsp");

        String result = res.toString();

        assertTrue(result.indexOf("<p>00-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'01-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>02-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'03-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>04-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'05-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>06-hello world</p>") > 0);
    }

    @Test
    public void testTldVersions25() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =
            new File("test/webapp-2.5");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/tld-versions.jsp");

        String result = res.toString();

        assertTrue(result.indexOf("<p>00-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'01-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>02-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'03-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>04-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'05-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>06-hello world</p>") > 0);
    }

    @Test
    public void testTldVersions30() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =
            new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/tld-versions.jsp");

        String result = res.toString();

        assertTrue(result.indexOf("<p>00-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'01-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>02-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'03-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>04-hello world</p>") > 0);
        assertTrue(result.indexOf("<p>#{'05-hello world'}</p>") > 0);
        assertTrue(result.indexOf("<p>06-hello world</p>") > 0);
    }

    public static class Echo extends TagSupport {

        private static final long serialVersionUID = 1L;

        private String echo = null;

        public void setEcho(String echo) {
            this.echo = echo;
        }

        public String getEcho() {
            return echo;
        }

        @Override
        public int doStartTag() throws JspException {
            try {
                pageContext.getOut().print("<p>" + echo + "</p>");
            } catch (IOException e) {
                pageContext.getServletContext().log("Tag (Echo21) failure", e);
            }
            return super.doStartTag();
        }
    }
}
