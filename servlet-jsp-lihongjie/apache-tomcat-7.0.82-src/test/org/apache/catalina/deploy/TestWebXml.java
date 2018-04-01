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
package org.apache.catalina.deploy;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.startup.WebRuleSet;
import org.apache.tomcat.util.descriptor.DigesterFactory;
import org.apache.tomcat.util.descriptor.XmlErrorHandler;
import org.apache.tomcat.util.descriptor.XmlIdentifiers;
import org.apache.tomcat.util.digester.Digester;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Test case for {@link WebXml}.
 */
public class TestWebXml {

    @Test
    public void testParseVersion() {

        WebXml webxml = new WebXml();

        // Defaults
        Assert.assertEquals(3, webxml.getMajorVersion());
        Assert.assertEquals(0, webxml.getMinorVersion());

        // Both get changed
        webxml.setVersion("2.5");
        Assert.assertEquals(2, webxml.getMajorVersion());
        Assert.assertEquals(5, webxml.getMinorVersion());

        // unknown input should be ignored
        webxml.setVersion("0.0");
        Assert.assertEquals(2, webxml.getMajorVersion());
        Assert.assertEquals(5, webxml.getMinorVersion());

        // null input should be ignored
        webxml.setVersion(null);
        Assert.assertEquals(2, webxml.getMajorVersion());
        Assert.assertEquals(5, webxml.getMinorVersion());
    }

    @Test
    public void testParsePublicIdVersion22() {

        WebXml webxml = new WebXml();

        webxml.setPublicId(XmlIdentifiers.WEB_22_PUBLIC);
        Assert.assertEquals(2, webxml.getMajorVersion());
        Assert.assertEquals(2, webxml.getMinorVersion());
        Assert.assertEquals("2.2", webxml.getVersion());
    }

    @Test
    public void testParsePublicIdVersion23() {

        WebXml webxml = new WebXml();

        webxml.setPublicId(XmlIdentifiers.WEB_23_PUBLIC);
        Assert.assertEquals(2, webxml.getMajorVersion());
        Assert.assertEquals(3, webxml.getMinorVersion());
        Assert.assertEquals("2.3", webxml.getVersion());
    }

    @Test
    public void testParsePublicIdVersion24() {

        WebXml webxml = new WebXml();

        webxml.setVersion("2.4");
        Assert.assertEquals(2, webxml.getMajorVersion());
        Assert.assertEquals(4, webxml.getMinorVersion());
        Assert.assertEquals("2.4", webxml.getVersion());
    }

    @Test
    public void testParsePublicIdVersion25() {

        WebXml webxml = new WebXml();

        webxml.setVersion("2.5");
        Assert.assertEquals(2, webxml.getMajorVersion());
        Assert.assertEquals(5, webxml.getMinorVersion());
        Assert.assertEquals("2.5", webxml.getVersion());
    }

    @Test
    public void testParsePublicIdVersion30() {

        WebXml webxml = new WebXml();

        webxml.setVersion("3.0");
        Assert.assertEquals(3, webxml.getMajorVersion());
        Assert.assertEquals(0, webxml.getMinorVersion());
        Assert.assertEquals("3.0", webxml.getVersion());
    }

    @Test
    public void testValidateVersion22() throws IOException, SAXException {
        doTestValidateVersion("2.2");
    }

    @Test
    public void testValidateVersion23() throws IOException, SAXException {
        doTestValidateVersion("2.3");
    }

    @Test
    public void testValidateVersion24() throws IOException, SAXException {
        doTestValidateVersion("2.4");
    }

    @Test
    public void testValidateVersion25() throws IOException, SAXException {
        doTestValidateVersion("2.5");
    }

    @Test
    public void testValidateVersion30() throws IOException, SAXException {
        doTestValidateVersion("3.0");
    }

    private void doTestValidateVersion(String version) throws IOException, SAXException {
        WebXml webxml = new WebXml();

        // Special cases
        if ("2.2".equals(version)) {
            webxml.setPublicId(XmlIdentifiers.WEB_22_PUBLIC);
        } else if ("2.3".equals(version)) {
            webxml.setPublicId(XmlIdentifiers.WEB_23_PUBLIC);
        } else {
            webxml.setVersion(version);
        }

        // Merged web.xml that is published as MERGED_WEB_XML context attribute
        // in the simplest case consists of webapp's web.xml file
        // plus the default conf/web.xml one.
        Set<WebXml> defaults = new HashSet<WebXml>();
        defaults.add(getDefaultWebXmlFragment());
        webxml.merge(defaults);

        Digester digester = DigesterFactory.newDigester(true, true, new WebRuleSet(), true);

        XmlErrorHandler handler = new XmlErrorHandler();
        digester.setErrorHandler(handler);

        InputSource is = new InputSource(new StringReader(webxml.toXml()));
        WebXml webxmlResult = new WebXml();
        digester.push(webxmlResult);
        digester.parse(is);

        Assert.assertEquals(0, handler.getErrors().size());
        Assert.assertEquals(0, handler.getWarnings().size());

        Assert.assertEquals(version, webxml.getVersion());
        Assert.assertEquals(version, webxmlResult.getVersion());
    }

    // A simplified copy of ContextConfig.getDefaultWebXmlFragment().
    // Assuming that global web.xml exists, host-specific web.xml does not exist.
    private WebXml getDefaultWebXmlFragment() throws IOException, SAXException {
        InputSource globalWebXml = new InputSource(new File("conf/web.xml")
                .getAbsoluteFile().toURI().toString());

        WebXml webXmlDefaultFragment = new WebXml();
        webXmlDefaultFragment.setOverridable(true);
        webXmlDefaultFragment.setDistributable(true);
        webXmlDefaultFragment.setAlwaysAddWelcomeFiles(false);

        Digester digester = DigesterFactory.newDigester(true, true, new WebRuleSet(), true);
        XmlErrorHandler handler = new XmlErrorHandler();
        digester.setErrorHandler(handler);
        digester.push(webXmlDefaultFragment);
        digester.parse(globalWebXml);
        Assert.assertEquals(0, handler.getErrors().size());
        Assert.assertEquals(0, handler.getWarnings().size());

        webXmlDefaultFragment.setReplaceWelcomeFiles(true);

        // Assert that web.xml was parsed and is not empty. Default servlet is known to be there.
        Assert.assertNotNull(webXmlDefaultFragment.getServlets().get("default"));

        // Manually add some version specific features to ensure that these do
        // not cause problems for the merged web.xml

        // Filters were added in 2.3 so should be excluded in 2.2
        FilterDef filterDef = new FilterDef();
        filterDef.setFilterClass("org.apache.tomcat.DummyFilter");
        filterDef.setFilterName("Dummy");
        webXmlDefaultFragment.addFilter(filterDef);

        FilterMap filterMap = new FilterMap();
        filterMap.setFilterName("Dummy");
        filterMap.addURLPattern("/*");
        webXmlDefaultFragment.addFilterMapping(filterMap);

        // Listeners were added in 2.3 so should be excluded in 2.2
        webXmlDefaultFragment.addListener("org.apache.tomcat.DummyListener");

        // resource-env-ref was added in 2.3 so should be excluded in 2.2
        ContextResourceEnvRef resourceEnvRef = new ContextResourceEnvRef();
        resourceEnvRef.setName("dummy");
        resourceEnvRef.setType("dummy");

        webXmlDefaultFragment.addResourceEnvRef(resourceEnvRef);

        // ejb-local-ref was added in 2.3 so should be excluded in 2.2
        ContextLocalEjb ejbLocalRef = new ContextLocalEjb();
        ejbLocalRef.setName("dummy");
        ejbLocalRef.setType("Session");
        ejbLocalRef.setLocal("dummy");
        ejbLocalRef.setHome("dummy");
        webXmlDefaultFragment.addEjbLocalRef(ejbLocalRef);

        // Servlet/run-as was added in 2.3 so should be excluded in 2.2
        ServletDef servletDef = new ServletDef();
        servletDef.setServletName("Dummy");
        servletDef.setServletClass("org.apache.tomcat.DummyServlet");
        servletDef.setRunAs("dummy");
        webXmlDefaultFragment.addServlet(servletDef);

        webXmlDefaultFragment.addServletMapping("/dummy", "Dummy");

        // resource-ref/res-sharing-scope was added in 2.3 so should be excluded
        // in 2.2
        ContextResource contextResource = new ContextResource();
        contextResource.setName("dummy");
        contextResource.setType("dummy");
        contextResource.setAuth("Container");
        contextResource.setScope("Shareable");
        webXmlDefaultFragment.addResourceRef(contextResource);

        // security-constraint/display-name was added in 2.3 so should be
        // excluded in 2.2
        SecurityConstraint sc = new SecurityConstraint();
        sc.setDisplayName("dummy");
        SecurityCollection collection = new SecurityCollection();
        collection.setName("dummy");
        collection.addPattern("/*");
        collection.addMethod("DELETE");
        sc.addCollection(collection);
        webXmlDefaultFragment.addSecurityConstraint(sc);

        // service-ref was added in 2.4 so should be excluded in 2.3 and earlier
        ContextService serviceRef = new ContextService();
        serviceRef.setName("dummy");
        serviceRef.setInterface("dummy");
        webXmlDefaultFragment.addServiceRef(serviceRef);

        // message-destination-ref was added in 2.4 so should be excluded in 2.3
        // and earlier
        MessageDestinationRef mdRef = new MessageDestinationRef();
        mdRef.setName("dummy");
        mdRef.setType("dummy");
        mdRef.setUsage("Consumes");
        webXmlDefaultFragment.addMessageDestinationRef(mdRef);

        // message-destination was added in 2.4 so should be excluded in 2.3
        // and earlier
        MessageDestination md = new MessageDestination();
        md.setName("dummy");
        webXmlDefaultFragment.addMessageDestination(md);

        // local-encoding-mapping-list was added in 2.4 so should be excluded in
        // 2.3 and earlier
        webXmlDefaultFragment.addLocaleEncodingMapping("en", "UTF-8");

        // jsp-config was added in Servlet 2.4
        webXmlDefaultFragment.addTaglib("dummy", "dummy");

        // filter-mapping/dispatcher added in Servlet 2.4
        filterMap.setDispatcher("REQUEST");

        // listener-[description|display-name|icon] added in Servlet 2.4
        // None of these are supported in WebXml

        // filter-mapping/dispatcher/ASYNC added in Servlet 3.0
        filterMap.setDispatcher("ASYNC");

        // error-page with just location allowed in Servlet 3.0+
        ErrorPage errorPage = new ErrorPage();
        errorPage.setLocation("/dummy");
        webXmlDefaultFragment.addErrorPage(errorPage);

        // async-supported added to Servlet and Filter in 3.0
        filterDef.setAsyncSupported("false");
        servletDef.setAsyncSupported("false");

        // session-cookie-config added in 3.0
        SessionConfig sessionConfig = new SessionConfig();
        sessionConfig.setCookieDomain("dummy");
        webXmlDefaultFragment.setSessionConfig(sessionConfig);

        // http-method-omission added in Servlet 3.0
        // Let this trigger a validation error as dropping it silently could
        // be a security concern

        // multi-part-config added in Servlet 3.0
        MultipartDef multiPart = new MultipartDef();
        servletDef.setMultipartDef(multiPart);

        return webXmlDefaultFragment;
    }

    @Test
    public void testLifecycleMethodsWebXml() {
        WebXml webxml = new WebXml();
        webxml.addPostConstructMethods("a", "a");
        webxml.addPreDestroyMethods("b", "b");

        WebXml fragment = new WebXml();
        fragment.addPostConstructMethods("c", "c");
        fragment.addPreDestroyMethods("d", "d");

        Set<WebXml> fragments = new HashSet<WebXml>();
        fragments.add(fragment);

        webxml.merge(fragments);

        Map<String, String> postConstructMethods = webxml.getPostConstructMethods();
        Map<String, String> preDestroyMethods = webxml.getPreDestroyMethods();
        Assert.assertEquals(1, postConstructMethods.size());
        Assert.assertEquals(1, preDestroyMethods.size());

        Assert.assertEquals("a", postConstructMethods.get("a"));
        Assert.assertEquals("b", preDestroyMethods.get("b"));
    }

    @Test
    public void testLifecycleMethodsWebFragments() {
        WebXml webxml = new WebXml();

        WebXml fragment1 = new WebXml();
        fragment1.addPostConstructMethods("a", "a");
        fragment1.addPreDestroyMethods("b", "b");

        WebXml fragment2 = new WebXml();
        fragment2.addPostConstructMethods("c", "c");
        fragment2.addPreDestroyMethods("d", "d");

        Set<WebXml> fragments = new HashSet<WebXml>();
        fragments.add(fragment1);
        fragments.add(fragment2);

        webxml.merge(fragments);

        Map<String, String> postConstructMethods = webxml.getPostConstructMethods();
        Map<String, String> preDestroyMethods = webxml.getPreDestroyMethods();
        Assert.assertEquals(2, postConstructMethods.size());
        Assert.assertEquals(2, preDestroyMethods.size());

        Assert.assertEquals("a", postConstructMethods.get("a"));
        Assert.assertEquals("c", postConstructMethods.get("c"));
        Assert.assertEquals("b", preDestroyMethods.get("b"));
        Assert.assertEquals("d", preDestroyMethods.get("d"));
    }

    @Test
    public void testLifecycleMethodsWebFragmentsWithConflicts() {
        WebXml webxml = new WebXml();

        WebXml fragment1 = new WebXml();
        fragment1.addPostConstructMethods("a", "a");
        fragment1.addPreDestroyMethods("b", "a");

        WebXml fragment2 = new WebXml();
        fragment2.addPostConstructMethods("a", "b");

        Set<WebXml> fragments = new HashSet<WebXml>();
        fragments.add(fragment1);
        fragments.add(fragment2);

        Assert.assertFalse(webxml.merge(fragments));

        Assert.assertEquals(0, webxml.getPostConstructMethods().size());

        WebXml fragment3 = new WebXml();
        fragment3.addPreDestroyMethods("b", "b");

        fragments.remove(fragment2);
        fragments.add(fragment3);

        Assert.assertFalse(webxml.merge(fragments));

        Assert.assertEquals(0, webxml.getPreDestroyMethods().size());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBug54387a() {
        // Multiple servlets may not be mapped to the same url-pattern
        WebXml webxml = new WebXml();
        webxml.addServletMapping("/foo", "a");
        webxml.addServletMapping("/foo", "b");
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBug54387b() {
        // Multiple servlets may not be mapped to the same url-pattern
        WebXml webxml = new WebXml();
        WebXml f1 = new WebXml();
        WebXml f2 = new WebXml();

        HashSet<WebXml> fragments = new HashSet<WebXml>();
        fragments.add(f1);
        fragments.add(f2);

        f1.addServletMapping("/foo", "a");
        f2.addServletMapping("/foo", "b");

        webxml.merge(fragments);
    }

    @Test
    public void testBug54387c() {
        // Multiple servlets may not be mapped to the same url-pattern but main
        // web.xml takes priority
        WebXml webxml = new WebXml();
        WebXml f1 = new WebXml();
        WebXml f2 = new WebXml();

        HashSet<WebXml> fragments = new HashSet<WebXml>();
        fragments.add(f1);
        fragments.add(f2);

        f1.addServletMapping("/foo", "a");
        f2.addServletMapping("/foo", "b");
        webxml.addServletMapping("/foo", "main");

        webxml.merge(fragments);
    }
}
