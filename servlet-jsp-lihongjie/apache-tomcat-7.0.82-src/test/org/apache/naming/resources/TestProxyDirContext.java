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
package org.apache.naming.resources;

import javax.naming.NameNotFoundException;

import static org.junit.Assert.fail;

import org.junit.Test;

import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;

/**
 * Unit test for the {@link ProxyDirContext}.
 * @author Marc Guillemot
 */
public class TestProxyDirContext extends TomcatBaseTest {

    /*
     * lookup doesn't always throw the same exception.
     */
    @Test
    public void testLookupException() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        // No file system docBase required
        StandardContext ctx = (StandardContext) tomcat.addContext("", null);
        ctx.setCacheTTL(500);
        tomcat.start();

        try {
            ctx.getResources().lookup("/WEB-INF/web.xml");
            fail();
        }
        catch (final NameNotFoundException e) {
            // as expected
        }
        Thread.sleep(600); // see ProxyDirContext.cacheTTL
        try {
            ctx.getResources().lookup("/WEB-INF/web.xml");
            fail();
        }
        catch (final NameNotFoundException e) {
            // as expected
        }
    }
}
