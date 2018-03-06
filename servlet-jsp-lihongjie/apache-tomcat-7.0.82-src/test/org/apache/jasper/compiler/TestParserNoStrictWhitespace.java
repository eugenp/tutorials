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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

/**
 * Tests are duplicated in {@link TestParser} with the strict whitespace parsing
 * enabled by default.
 */
public class TestParserNoStrictWhitespace extends TomcatBaseTest {

    @Override
    public void setUp() throws Exception {
        System.setProperty(
                "org.apache.jasper.compiler.Parser.STRICT_WHITESPACE",
                "false");
        super.setUp();
    }

    @Test
    public void testBug48627() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =
            new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/bug48nnn/bug48627.jsp");

        String result = res.toString();
        // Beware of the differences between escaping in JSP attributes and
        // in Java Strings
        assertEcho(result, "00-\\");
        assertEcho(result, "01-\\");
    }

    @Test
    public void testBug48668a() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =
            new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/bug48nnn/bug48668a.jsp");
        String result = res.toString();
        assertEcho(result, "00-Hello world</p>#{foo.bar}");
        assertEcho(result, "01-Hello world</p>${foo.bar}");
        assertEcho(result, "10-Hello ${'foo.bar}");
        assertEcho(result, "11-Hello ${'foo.bar}");
        assertEcho(result, "12-Hello #{'foo.bar}");
        assertEcho(result, "13-Hello #{'foo.bar}");
        assertEcho(result, "14-Hello ${'foo}");
        assertEcho(result, "15-Hello ${'foo}");
        assertEcho(result, "16-Hello #{'foo}");
        assertEcho(result, "17-Hello #{'foo}");
        assertEcho(result, "18-Hello ${'foo.bar}");
        assertEcho(result, "19-Hello ${'foo.bar}");
        assertEcho(result, "20-Hello #{'foo.bar}");
        assertEcho(result, "21-Hello #{'foo.bar}");
        assertEcho(result, "30-Hello ${'foo}");
        assertEcho(result, "31-Hello ${'foo}");
        assertEcho(result, "32-Hello #{'foo}");
        assertEcho(result, "33-Hello #{'foo}");
        assertEcho(result, "34-Hello ${'foo}");
        assertEcho(result, "35-Hello ${'foo}");
        assertEcho(result, "36-Hello #{'foo}");
        assertEcho(result, "37-Hello #{'foo}");
        assertEcho(result, "40-Hello ${'foo}");
        assertEcho(result, "41-Hello ${'foo}");
        assertEcho(result, "42-Hello #{'foo}");
        assertEcho(result, "43-Hello #{'foo}");
        assertEcho(result, "50-Hello ${'foo}");
        assertEcho(result, "51-Hello ${'foo}");
        assertEcho(result, "52-Hello #{'foo}");
        assertEcho(result, "53-Hello #{'foo}");
    }

    @Test
    public void testBug48668b() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/bug48nnn/bug48668b.jsp");
        String result = res.toString();
        assertEcho(result, "00-Hello world</p>#{foo.bar}");
        assertEcho(result, "01-Hello world</p>#{foo2");
    }

    @Test
    public void testBug49297NoSpaceNotStrict() throws Exception {

        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = new ByteChunk();
        int sc = getUrl("http://localhost:" + getPort() +
                "/test/bug49nnn/bug49297NoSpace.jsp", res,
                new HashMap<String,List<String>>());


        assertEquals(200, sc);
        assertEcho(res.toString(), "Hello World");
    }

    @Test
    public void testBug49297DuplicateAttr() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        int sc = getUrl("http://localhost:" + getPort() +
                "/test/bug49nnn/bug49297DuplicateAttr.jsp", new ByteChunk(),
                new HashMap<String,List<String>>());

        assertEquals(500, sc);
    }

    /** Assertion for text printed by tags:echo */
    private static void assertEcho(String result, String expected) {
        assertTrue(result.indexOf("<p>" + expected + "</p>") > 0);
    }
}
