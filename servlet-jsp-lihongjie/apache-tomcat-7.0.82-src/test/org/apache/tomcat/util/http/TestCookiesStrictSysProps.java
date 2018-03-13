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

package org.apache.tomcat.util.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.buf.ByteChunk;

/**
 * Test case for {@link Cookies}. <b>Note</b> because of the use of <code>final
 * static</code> constants in {@link Cookies}, each of these tests must be
 * executed in a new JVM instance. The tests have been place in separate classes
 * to facilitate this when running the unit tests via Ant.
 */
public class TestCookiesStrictSysProps extends CookiesBaseTest {

    @Override
    @Test
    public void testCookiesInstance() throws Exception {

        System.setProperty("org.apache.catalina.STRICT_SERVLET_COMPLIANCE",
                "true");

        Tomcat tomcat = getTomcatInstance();

        addServlets(tomcat);

        tomcat.start();

        ByteChunk res = getUrl("http://localhost:" + getPort() + "/invalid");
        assertEquals("Cookie name fail", res.toString());
        res = getUrl("http://localhost:" + getPort() + "/null");
        assertEquals("Cookie name fail", res.toString());
        res = getUrl("http://localhost:" + getPort() + "/blank");
        assertEquals("Cookie name fail", res.toString());
        res = getUrl("http://localhost:" + getPort() + "/invalidFwd");
        assertEquals("Cookie name fail", res.toString());
        res = getUrl("http://localhost:" + getPort() + "/invalidStrict");
        assertEquals("Cookie name fail", res.toString());
        res = getUrl("http://localhost:" + getPort() + "/valid");
        assertEquals("Cookie name ok", res.toString());

        // Need to read response headers to test version switching
        Map<String,List<String>> headers = new HashMap<String,List<String>>();
        getUrl("http://localhost:" + getPort() + "/switch", res, headers);
        List<String> cookieHeaders = headers.get("Set-Cookie");
        for (String cookieHeader : cookieHeaders) {
            assertEquals("name=\"val?ue\"; Version=1", cookieHeader);
        }

    }

}
