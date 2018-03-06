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

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;
import org.apache.tomcat.util.buf.ByteChunk;

/**
 * Tests are duplicated in {@link TestParserNoStrictWhitespace} with the strict
 * whitespace parsing disabled.
 */
public class TestParser extends TomcatBaseTest {

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
    public void testBug49297NoSpaceStrict() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        int sc = getUrl("http://localhost:" + getPort() +
                "/test/bug49nnn/bug49297NoSpace.jsp", new ByteChunk(),
                new HashMap<String,List<String>>());

        assertEquals(500, sc);
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

    @Test
    public void testBug49297MultipleImport1() throws Exception {

        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = new ByteChunk();
        int sc = getUrl("http://localhost:" + getPort() +
                "/test/bug49nnn/bug49297MultipleImport1.jsp", res,
                new HashMap<String,List<String>>());

        assertEquals(200, sc);
        assertEcho(res.toString(), "OK");
    }

    @Test
    public void testBug49297MultipleImport2() throws Exception {

        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = new ByteChunk();
        int sc = getUrl("http://localhost:" + getPort() +
                "/test/bug49nnn/bug49297MultipleImport2.jsp", res,
                new HashMap<String,List<String>>());

        assertEquals(200, sc);
        assertEcho(res.toString(), "OK");
    }

    @Test
    public void testBug49297MultiplePageEncoding1() throws Exception {

        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = new ByteChunk();
        int sc = getUrl("http://localhost:" + getPort() +
                "/test/bug49nnn/bug49297MultiplePageEncoding1.jsp", res,
                new HashMap<String,List<String>>());

        assertEquals(500, sc);
    }

    @Test
    public void testBug49297MultiplePageEncoding2() throws Exception {

        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = new ByteChunk();
        int sc = getUrl("http://localhost:" + getPort() +
                "/test/bug49nnn/bug49297MultiplePageEncoding2.jsp", res,
                new HashMap<String,List<String>>());

        assertEquals(500, sc);
    }

    @Test
    public void testBug49297MultiplePageEncoding3() throws Exception {

        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = new ByteChunk();
        int sc = getUrl("http://localhost:" + getPort() +
                "/test/bug49nnn/bug49297MultiplePageEncoding3.jsp", res,
                new HashMap<String,List<String>>());

        assertEquals(500, sc);
    }

    @Test
    public void testBug49297MultiplePageEncoding4() throws Exception {

        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = new ByteChunk();
        int sc = getUrl("http://localhost:" + getPort() +
                "/test/bug49nnn/bug49297MultiplePageEncoding4.jsp", res,
                new HashMap<String,List<String>>());

        assertEquals(500, sc);
    }

    @Test
    public void testBug49297Tag() throws Exception {

        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = new ByteChunk();
        int sc = getUrl("http://localhost:" + getPort() +
                "/test/bug49nnn/bug49297Tag.jsp", res,
                new HashMap<String,List<String>>());

        assertEquals(200, sc);
        assertEcho(res.toString(), "OK");
    }

    @Test
    public void testBug52335() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir =
            new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/bug5nnnn/bug52335.jsp");

        String result = res.toString();
        // Beware of the differences between escaping in JSP attributes and
        // in Java Strings
        assertEcho(result, "00 - \\% \\\\% <%");
        assertEcho(result, "01 - <b><%</b>");
        assertEcho(result, "02 - <p>Foo</p><%");
    }

    @Test
    public void testBug55198() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        // app dir is relative to server home
        tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/bug5nnnn/bug55198.jsp");

        String result = res.toString();

        Assert.assertTrue(result,
                result.contains("&quot;1foo1&lt;&amp;&gt;&quot;")
             || result.contains("&#034;1foo1&lt;&amp;&gt;&#034;"));
        Assert.assertTrue(result,
                result.contains("&quot;2bar2&lt;&amp;&gt;&quot;")
             || result.contains("&#034;2bar2&lt;&amp;&gt;&#034;"));
        Assert.assertTrue(result,
                result.contains("&quot;3a&amp;b3&quot;")
             || result.contains("&#034;3a&amp;b3&#034;"));
        Assert.assertTrue(result,
                result.contains("&quot;4&4&quot;")
             || result.contains("&#034;4&4&#034;"));
        Assert.assertTrue(result,
                result.contains("&quot;5&apos;5&quot;")
             || result.contains("&#034;5&apos;5&#034;"));
    }

    @Test
    public void testBug56265() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        // app dir is relative to server home
        StandardContext ctxt = (StandardContext) tomcat.addWebapp(null,
                "/test", appDir.getAbsolutePath());

        // This test needs the JSTL libraries
        File lib = new File("webapps/examples/WEB-INF/lib");
        ctxt.setAliases("/WEB-INF/lib=" + lib.getCanonicalPath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/bug5nnnn/bug56265.jsp");

        String result = res.toString();

        Assert.assertTrue(result,
                result.contains("[1: [data-test]: [window.alert('Hello World <&>!')]]"));
        Assert.assertTrue(result,
                result.contains("[2: [data-test]: [window.alert('Hello World <&>!')]]"));
        Assert.assertTrue(result,
                result.contains("[3: [data-test]: [window.alert('Hello 'World <&>'!')]]"));
        Assert.assertTrue(result,
                result.contains("[4: [data-test]: [window.alert('Hello 'World <&>'!')]]"));
    }

    @Test
    public void testBug56334And56561() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        // app dir is relative to server home
        StandardContext ctxt = (StandardContext) tomcat.addWebapp(null,
                "/test", appDir.getAbsolutePath());

        // This test needs the JSTL libraries
        File lib = new File("webapps/examples/WEB-INF/lib");
        ctxt.setAliases("/WEB-INF/lib=" + lib.getCanonicalPath());

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() +
                "/test/bug5nnnn/bug56334and56561.jspx");

        String result = res.toString();

        // NOTE: The expected values must themselves be \ escaped below
        Assert.assertTrue(result, result.contains("01a\\?resize01a"));
        Assert.assertTrue(result, result.contains("01b\\\\x\\?resize01b"));
        Assert.assertTrue(result, result.contains("<set data-value=\"02a\\\\?resize02a\"/>"));
        Assert.assertTrue(result, result.contains("<set data-value=\"02b\\\\\\\\x\\\\?resize02b\"/>"));
        Assert.assertTrue(result, result.contains("<set data-value=\"03a\\?resize03a\"/>"));
        Assert.assertTrue(result, result.contains("<set data-value=\"03b\\\\x\\?resize03b\"/>"));
        Assert.assertTrue(result, result.contains("<04a\\?resize04a/>"));
        Assert.assertTrue(result, result.contains("<04b\\\\x\\?resize04b/>"));
        Assert.assertTrue(result, result.contains("<set data-value=\"05a$${&amp;\"/>"));
        Assert.assertTrue(result, result.contains("<set data-value=\"05b$${&amp;2\"/>"));
        Assert.assertTrue(result, result.contains("<set data-value=\"05c##{&gt;hello&lt;\"/>"));
        Assert.assertTrue(result, result.contains("05x:<set data-value=\"\"/>"));
        Assert.assertTrue(result, result.contains("<set xmlns:foo=\"urn:06a\\bar\\baz\"/>"));
        Assert.assertTrue(result, result.contains("07a:<set data-value=\"\\?resize\"/>"));
        Assert.assertTrue(result, result.contains("07b:<set data-content=\"\\?resize=.+\"/>"));
        Assert.assertTrue(result, result.contains("07c:<set data-content=\"\\?resize=.+\"/>"));
        Assert.assertTrue(result, result.contains("07d:<set data-content=\"false\"/>"));
        Assert.assertTrue(result, result.contains("07e:<set data-content=\"false\"/>"));
        Assert.assertTrue(result, result.contains("07f:<set data-content=\"\\\'something\'\"/>"));
        Assert.assertTrue(result, result.contains("07g:<set data-content=\"\\\'something\'\"/>"));
    }

    /** Assertion for text printed by tags:echo */
    private static void assertEcho(String result, String expected) {
        assertTrue(result.indexOf("<p>" + expected + "</p>") > 0);
    }
}
