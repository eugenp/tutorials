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
package org.apache.tomcat.util.descriptor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TestLocalResolver {

    private final Map<String, String> publicIds = new HashMap<String, String>();
    private final Map<String, String> systemIds = new HashMap<String, String>();

    private LocalResolver resolver = new LocalResolver(publicIds, systemIds, true);
    private String WEB_22_LOCAL;
    private String WEB_30_LOCAL;
    private String WEBCOMMON_30_LOCAL;

    @Before
    public void init() {
        WEB_22_LOCAL = urlFor("resources/web-app_2_2.dtd");
        WEB_30_LOCAL = urlFor("resources/web-app_3_0.xsd");
        WEBCOMMON_30_LOCAL = urlFor("resources/web-common_3_0.xsd");
        publicIds.put(XmlIdentifiers.WEB_22_PUBLIC, WEB_22_LOCAL);
        systemIds.put(XmlIdentifiers.WEB_30_XSD, WEB_30_LOCAL);
        systemIds.put(WEBCOMMON_30_LOCAL, WEBCOMMON_30_LOCAL);
    }

    public String urlFor(String id) {
        return ServletContext.class.getResource(id).toExternalForm();
    }

    @Test(expected = FileNotFoundException.class)
    public void unknownNullId() throws IOException, SAXException {
        Assert.assertNull(resolver.resolveEntity(null, null));
    }

    @Test(expected = FileNotFoundException.class)
    public void unknownPublicId() throws IOException, SAXException {
        Assert.assertNull(resolver.resolveEntity("unknown", null));
    }

    @Test(expected = FileNotFoundException.class)
    public void unknownSystemId() throws IOException, SAXException {
        InputSource source = resolver.resolveEntity(null, "unknown");
        Assert.assertEquals(null, source.getPublicId());
        Assert.assertEquals("unknown", source.getSystemId());
    }

    @Test(expected = FileNotFoundException.class)
    public void unknownRelativeSystemId()
            throws IOException, SAXException {
        InputSource source = resolver.resolveEntity(
                null, null, "http://example.com/home.html", "unknown");
        Assert.assertEquals(null, source.getPublicId());
        Assert.assertEquals("http://example.com/unknown", source.getSystemId());
    }

    @Test
    public void publicIdIsResolved() throws IOException, SAXException {
        InputSource source = resolver.resolveEntity(
                XmlIdentifiers.WEB_22_PUBLIC, XmlIdentifiers.WEB_22_SYSTEM);
        Assert.assertEquals(XmlIdentifiers.WEB_22_PUBLIC, source.getPublicId());
        Assert.assertEquals(WEB_22_LOCAL, source.getSystemId());
    }

    @Test
    public void systemIdIsIgnoredWhenPublicIdIsResolved()
            throws IOException, SAXException {
        InputSource source = resolver.resolveEntity(
                XmlIdentifiers.WEB_22_PUBLIC, "unknown");
        Assert.assertEquals(XmlIdentifiers.WEB_22_PUBLIC, source.getPublicId());
        Assert.assertEquals(WEB_22_LOCAL, source.getSystemId());
    }

    @Test
    public void systemIdIsResolved() throws IOException, SAXException {
        InputSource source =
                resolver.resolveEntity(null, XmlIdentifiers.WEB_30_XSD);
        Assert.assertEquals(null, source.getPublicId());
        Assert.assertEquals(WEB_30_LOCAL, source.getSystemId());
    }

    @Test
    public void relativeSystemIdIsResolvedAgainstBaseURI()
            throws IOException, SAXException {
        InputSource source = resolver.resolveEntity(
                null, null, WEB_30_LOCAL, "web-common_3_0.xsd");
        Assert.assertEquals(null, source.getPublicId());
        Assert.assertEquals(WEBCOMMON_30_LOCAL, source.getSystemId());
    }

    @Test
    public void absoluteSystemIdOverridesBaseURI()
            throws IOException, SAXException {
        InputSource source = resolver.resolveEntity(null, null,
                "http://example.com/home.html", XmlIdentifiers.WEB_30_XSD);
        Assert.assertEquals(null, source.getPublicId());
        Assert.assertEquals(WEB_30_LOCAL, source.getSystemId());
    }
}
