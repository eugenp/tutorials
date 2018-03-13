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

import javax.servlet.ServletContext;
import javax.servlet.jsp.tagext.TagFileInfo;
import javax.servlet.jsp.tagext.TagInfo;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;

/**
 * Test case for {@link TagPluginManager}.
 */
public class TestTagPluginManager extends TomcatBaseTest {

    private static TagInfo tagInfo = new TagInfo("ATag",
            "org.apache.jasper.compiler.ATagSupport", "", "", null, null, null);

    @Test
    public void testBug54240() throws Exception {
        Tomcat tomcat = getTomcatInstance();

        File appDir = new File("test/webapp-3.0");
        Context ctx = tomcat.addWebapp(null, "/test", appDir.getAbsolutePath());
        tomcat.start();

        ServletContext context = ctx.getServletContext();

        TagPluginManager manager = new TagPluginManager(context);

        Node.Nodes nodes = new Node.Nodes();
        Node.CustomTag c = new Node.CustomTag("test:ATag", "test", "ATag",
                "http://tomcat.apache.org/jasper", null, null, null, null, null,
                new TagFileInfo("ATag", "http://tomcat.apache.org/jasper",
                        tagInfo));
        c.setTagHandlerClass(TesterTag.class);
        nodes.add(c);
        manager.apply(nodes, null, null);

        Node n = nodes.getNode(0);
        Assert.assertNotNull(n);
        Assert.assertTrue(n instanceof Node.CustomTag);

        Node.CustomTag t = (Node.CustomTag)n;
        Assert.assertNotNull(t.getAtSTag());

        Node.Nodes sTag = c.getAtSTag();
        Node scriptlet = sTag.getNode(0);
        Assert.assertNotNull(scriptlet);
        Assert.assertTrue(scriptlet instanceof Node.Scriptlet);
        Node.Scriptlet s = (Node.Scriptlet)scriptlet;
        Assert.assertEquals("//Just a comment", s.getText());
    }
}
