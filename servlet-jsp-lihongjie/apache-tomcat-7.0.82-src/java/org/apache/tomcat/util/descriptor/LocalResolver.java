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
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import org.apache.tomcat.util.res.StringManager;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.EntityResolver2;

/**
 * A resolver for locally cached XML resources.
 */
public class LocalResolver implements EntityResolver2 {

    private static final StringManager sm =
            StringManager.getManager(Constants.PACKAGE_NAME);

    private static final String[] JAVA_EE_NAMESPACES = {
        XmlIdentifiers.JAVAEE_1_4_NS,
        XmlIdentifiers.JAVAEE_5_NS };


    private final Map<String,String> publicIds;
    private final Map<String,String> systemIds;
    private final boolean blockExternal;

    /**
     * Constructor providing mappings of public and system identifiers to local
     * resources. Each map contains a mapping from a well-known identifier to a
     * URL for a local resource path.
     *
     * @param publicIds mapping of well-known public identifiers to local
     *                  resources
     * @param systemIds mapping of well-known system identifiers to local
     *                  resources
     * @param blockExternal are external resources blocked that are not
     *                      well-known
     */
    public LocalResolver(Map<String,String> publicIds,
            Map<String,String> systemIds, boolean blockExternal) {
        this.publicIds = publicIds;
        this.systemIds = systemIds;
        this.blockExternal = blockExternal;
    }


    @Override
    public InputSource resolveEntity(String publicId, String systemId)
            throws SAXException, IOException {
        return resolveEntity(null, publicId, null, systemId);
    }


    @Override
    public InputSource resolveEntity(String name, String publicId,
            String base, String systemId) throws SAXException, IOException {

        // First try resolving using the publicId
        String resolved = publicIds.get(publicId);
        if (resolved != null) {
            InputSource is = new InputSource(resolved);
            is.setPublicId(publicId);
            return is;
        }

        // If there is no systemId, can't try anything else
        if (systemId == null) {
            throw new FileNotFoundException(sm.getString("localResolver.unresolvedEntity",
                    name, publicId, systemId, base));
        }

        // Try resolving with the supplied systemId
        resolved = systemIds.get(systemId);
        if (resolved != null) {
            InputSource is = new InputSource(resolved);
            is.setPublicId(publicId);
            return is;
        }

        // Work-around for XML documents that use just the file name for the
        // location to refer to a JavaEE schema
        for (String javaEENamespace : JAVA_EE_NAMESPACES) {
            String javaEESystemId = javaEENamespace + '/' + systemId;
            resolved = systemIds.get(javaEESystemId);
            if (resolved != null) {
                InputSource is = new InputSource(resolved);
                is.setPublicId(publicId);
                return is;
            }
        }

        // Resolve the supplied systemId against the base
        URI systemUri;
        try {
            if (base == null) {
                systemUri = new URI(systemId);
            } else {
                // Can't use URI.resolve() because "jar:..." URLs are not valid
                // hierarchical URIs so resolve() does not work. new URL()
                // delegates to the jar: stream handler and it manages to figure
                // it out.
                URI baseUri = new URI(base);
                systemUri = new URL(baseUri.toURL(), systemId).toURI();
            }
            systemUri = systemUri.normalize();
        } catch (URISyntaxException e) {
            // May be caused by a | being used instead of a : in an absolute
            // file URI on Windows.
            if (blockExternal) {
                // Absolute paths aren't allowed so block it
                throw new MalformedURLException(e.getMessage());
            } else {
                // See if the URLHandler can resolve it
                return new InputSource(systemId);
            }
        }
        if (systemUri.isAbsolute()) {
            // Try the resolved systemId
            resolved = systemIds.get(systemUri.toString());
            if (resolved != null) {
                InputSource is = new InputSource(resolved);
                is.setPublicId(publicId);
                return is;
            }
            if (!blockExternal) {
                InputSource is = new InputSource(systemUri.toString());
                is.setPublicId(publicId);
                return is;
            }
        }

        throw new FileNotFoundException(sm.getString("localResolver.unresolvedEntity",
                name, publicId, systemId, base));
    }


    @Override
    public InputSource getExternalSubset(String name, String baseURI)
            throws SAXException, IOException {
        return null;
    }
}
