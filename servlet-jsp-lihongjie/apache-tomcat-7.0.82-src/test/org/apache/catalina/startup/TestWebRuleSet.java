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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.apache.catalina.deploy.WebXml;
import org.apache.tomcat.util.digester.Digester;

public class TestWebRuleSet {

    private Digester fragmentDigester = new Digester();
    private WebRuleSet fragmentRuleSet = new WebRuleSet(true);

    private Digester webDigester = new Digester();
    private WebRuleSet webRuleSet = new WebRuleSet(false);

    public TestWebRuleSet() {
        fragmentDigester.addRuleSet(fragmentRuleSet);
        webDigester.addRuleSet(webRuleSet);
    }


    @Test
    public void testSingleNameInWebFragmentXml() throws Exception {

        WebXml webXml = new WebXml();

        parse(webXml, "web-fragment-1name.xml", true, true);
        assertEquals("name1", webXml.getName());
    }


    @Test
    public void testMultipleNameInWebFragmentXml() throws Exception {
        parse(new WebXml(), "web-fragment-2name.xml", true, false);
    }


    @Test
    public void testSingleOrderingInWebFragmentXml() throws Exception {

        WebXml webXml = new WebXml();

        parse(webXml, "web-fragment-1ordering.xml", true, true);
        assertEquals(1, webXml.getBeforeOrdering().size());
        assertTrue(webXml.getBeforeOrdering().contains("bar"));
    }


    @Test
    public void testMultipleOrderingInWebFragmentXml() throws Exception {
        parse(new WebXml(), "web-fragment-2ordering.xml", true, false);
    }


    @Test
    public void testSingleOrderingInWebXml() throws Exception {

        WebXml webXml = new WebXml();

        parse(webXml, "web-1ordering.xml", false, true);
        assertEquals(1, webXml.getAbsoluteOrdering().size());
        assertTrue(webXml.getAbsoluteOrdering().contains("bar"));
    }


    @Test
    public void testMultipleOrderingInWebXml() throws Exception {
        parse(new WebXml(), "web-2ordering.xml", false, false);
    }


    @Test
    public void testRecycle() throws Exception {
        // Name
        parse(new WebXml(), "web-fragment-2name.xml", true, false);
        parse(new WebXml(), "web-fragment-1name.xml", true, true);
        parse(new WebXml(), "web-fragment-2name.xml", true, false);
        parse(new WebXml(), "web-fragment-1name.xml", true, true);

        // Relative ordering
        parse(new WebXml(), "web-fragment-2ordering.xml", true, false);
        parse(new WebXml(), "web-fragment-1ordering.xml", true, true);
        parse(new WebXml(), "web-fragment-2ordering.xml", true, false);
        parse(new WebXml(), "web-fragment-1ordering.xml", true, true);

        // Absolute ordering
        parse(new WebXml(), "web-2ordering.xml", false, false);
        parse(new WebXml(), "web-1ordering.xml", false, true);
        parse(new WebXml(), "web-2ordering.xml", false, false);
        parse(new WebXml(), "web-1ordering.xml", false, true);
}

    @Test
    public void testLifecycleMethodsDefinitions() throws Exception {
        // post-construct and pre-destroy
        parse(new WebXml(), "web-1lifecyclecallback.xml", false, true);
        // conflicting post-construct definitions
        parse(new WebXml(), "web-2lifecyclecallback.xml", false, false);
    }

    private synchronized void parse(WebXml webXml, String target,
            boolean fragment, boolean expected) {

        Digester d;
        if (fragment) {
            d = fragmentDigester;
            fragmentRuleSet.recycle();
        } else {
            d = webDigester;
            webRuleSet.recycle();
        }

        d.push(webXml);

        File f = new File("test/org/apache/catalina/startup/" + target);
        InputStream is = null;

        boolean result = true;

        try {
            is = new FileInputStream(f);
            d.parse(is);
        } catch (Exception e) {
            if (expected) {
                // Didn't expect an exception
                e.printStackTrace();
            }
            result = false;
        } finally {
           if (is != null) {
               try {
                   is.close();
               } catch (IOException e) {
               }
           }
        }

        if (expected) {
            assertTrue(result);
        } else {
            assertFalse(result);
        }
    }
}
