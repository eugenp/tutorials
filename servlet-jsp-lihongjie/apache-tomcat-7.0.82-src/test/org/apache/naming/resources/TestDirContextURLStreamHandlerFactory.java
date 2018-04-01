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

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class TestDirContextURLStreamHandlerFactory {

    @Test
    public void testUserSuppliedFactory() throws Exception {

        URL url = null;

        // Initially unknown
        try {
            url = new URL("foo://www.apache.org");
        } catch (MalformedURLException ignore) {
            // Ignore
        }
        assertNull(url);

        // Set the factory
        URL.setURLStreamHandlerFactory(
                DirContextURLStreamHandlerFactory.getInstance());

        // Still unknown
        try {
            url = new URL("foo://www.apache.org");
        } catch (MalformedURLException ignore) {
            // Ignore
        }
        assertNull(url);

        // Register a user factory
        DirContextURLStreamHandlerFactory.addUserFactory(
                new FooURLStreamHandlerFactory());

        // Now it works
        try {
            url = new URL("foo://www.apache.org");
        } catch (MalformedURLException ignore) {
            // Ignore
        }
        assertNotNull(url);
    }

    public static class FooURLStreamHandlerFactory
            implements URLStreamHandlerFactory {

        @Override
        public URLStreamHandler createURLStreamHandler(String protocol) {
            if ("foo".equals(protocol)) {
                // This is good enough for this test but not for actual use
                return new DirContextURLStreamHandler();
            } else {
                return null;
            }
        }
    }
}
