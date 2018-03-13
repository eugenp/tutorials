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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.scan.StandardJarScanner;

public class TestCompiler extends TomcatBaseTest {

    @Test
    public void testBug49726a() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());
        tomcat.start();

        ByteChunk res = new ByteChunk();
        Map<String,List<String>> headers = new HashMap<String,List<String>>();

        getUrl("http://localhost:" + getPort() + "/test/bug49nnn/bug49726a.jsp",
                res, headers);

        // Check request completed
        String result = res.toString();
        assertEcho(result, "OK");

        // Check content type
        assertTrue(headers.get("Content-Type").get(0).startsWith("text/html"));
    }

    @Test
    public void testBug49726b() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());
        tomcat.start();

        ByteChunk res = new ByteChunk();
        Map<String,List<String>> headers = new HashMap<String,List<String>>();

        getUrl("http://localhost:" + getPort() + "/test/bug49nnn/bug49726b.jsp",
                res, headers);

        // Check request completed
        String result = res.toString();
        assertEcho(result, "OK");

        // Check content type
        assertTrue(headers.get("Content-Type").get(0).startsWith("text/plain"));
    }

    @Test
    public void testBug53257a() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());
        tomcat.start();

        // foo;bar.jsp
        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/bug53257/foo%3bbar.jsp");

        // Check request completed
        String result = res.toString();
        assertEcho(result, "OK");
    }

    @Test
    public void testBug53257b() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());
        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/bug53257/foo&bar.jsp");

        // Check request completed
        String result = res.toString();
        assertEcho(result, "OK");
    }

    @Test
    public void testBug53257c() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());
        tomcat.start();

        // foo#bar.jsp
        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/bug53257/foo%23bar.jsp");

        // Check request completed
        String result = res.toString();
        assertEcho(result, "OK");
    }

    @Test
    public void testBug53257d() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());
        tomcat.start();

        // foo%bar.jsp
        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/bug53257/foo%25bar.jsp");

        // Check request completed
        String result = res.toString();
        assertEcho(result, "OK");
    }

    @Test
    public void testBug53257e() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());
        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/bug53257/foo+bar.jsp");

        // Check request completed
        String result = res.toString();
        assertEcho(result, "OK");
    }

    @Test
    public void testBug53257f() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());
        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/bug53257/foo%20bar.jsp");

        // Check request completed
        String result = res.toString();
        assertEcho(result, "OK");
    }

    @Test
    public void testBug53257g() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());
        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/bug53257/foo%20bar/foobar.jsp");

        // Check request completed
        String result = res.toString();
        assertEcho(result, "OK");
    }

    @Test
    public void testBug53257z() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());
        tomcat.start();

        // Check that URL decoding is not done twice
        ByteChunk res = new ByteChunk();
        int rc = getUrl("http://localhost:" + getPort() +
                "/test/bug53257/foo%2525bar.jsp", res, null);
        assertEquals(404, rc);
    }

    @Test
    public void testBug51584() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0-fragments");
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());
        tomcat.start();

        // No further tests required. The bug triggers an infinite loop on
        // context start so the test will crash before it reaches this point if
        // it fails
    }

    @Test
    public void testBug55807() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        Context context = tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());
        ((StandardJarScanner) context.getJarScanner()).setScanAllDirectories(true);
        tomcat.start();

        ByteChunk res = new ByteChunk();
        Map<String,List<String>> headers = new HashMap<String,List<String>>();

        getUrl("http://localhost:" + getPort() + "/test/bug5nnnn/bug55807.jsp",
                res, headers);

        // Check request completed
        String result = res.toString();
        assertEcho(result, "OK");

        // Check the dependencies count
        Assert.assertTrue(result.contains("<p>DependenciesCount: 1</p>"));

        // Check the right timestamp was used in the dependency
        File tld = new File("test/webapp-3.0/WEB-INF/classes/META-INF/bug55807.tld");
        String expected = "/WEB-INF/classes/META-INF/bug55807.tld : " +
                tld.lastModified() + "</p>";
        Assert.assertTrue(result.contains(expected));


        // Check content type
        Assert.assertTrue(headers.get("Content-Type").get(0).startsWith("text/html"));
    }

    /** Assertion for text printed by tags:echo */
    private static void assertEcho(String result, String expected) {
        assertTrue(result, result.indexOf("<p>" + expected + "</p>") > 0);
    }
}
