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

package org.apache.catalina.filters;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.http.HttpServletResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import org.apache.catalina.filters.CsrfPreventionFilter.LruCache;
import org.apache.catalina.startup.TomcatBaseTest;

public class TestCsrfPreventionFilter extends TomcatBaseTest {

    private static final String RESULT_NONCE =
        Constants.CSRF_NONCE_SESSION_ATTR_NAME + "=TESTNONCE";

    private final HttpServletResponse wrapper =
        new CsrfPreventionFilter.CsrfResponseWrapper(
                new NonEncodingResponse(), "TESTNONCE");

    @Test
    public void testAddNonceNoQueryNoAnchor() throws Exception {
        assertEquals("/test?" + RESULT_NONCE ,
                wrapper.encodeRedirectURL("/test"));
    }

    @Test
    public void testAddNonceQueryNoAnchor() throws Exception {
        assertEquals("/test?a=b&" + RESULT_NONCE ,
                wrapper.encodeRedirectURL("/test?a=b"));
    }

    @Test
    public void testAddNonceNoQueryAnchor() throws Exception {
        assertEquals("/test?" + RESULT_NONCE + "#c",
                wrapper.encodeRedirectURL("/test#c"));
    }

    @Test
    public void testAddNonceQueryAnchor() throws Exception {
        assertEquals("/test?a=b&" + RESULT_NONCE + "#c",
                wrapper.encodeRedirectURL("/test?a=b#c"));
    }

    @Test
    public void testLruCacheSerializable() throws Exception {
        LruCache<String> cache = new LruCache<String>(5);
        cache.add("key1");
        cache.add("key2");
        cache.add("key3");
        cache.add("key4");
        cache.add("key5");
        cache.add("key6");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(cache);

        ByteArrayInputStream bais =
            new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        @SuppressWarnings("unchecked")
        LruCache<String> cache2 = (LruCache<String>) ois.readObject();

        cache2.add("key7");
        assertFalse(cache2.contains("key1"));
        assertFalse(cache2.contains("key2"));
        assertTrue(cache2.contains("key3"));
        assertTrue(cache2.contains("key4"));
        assertTrue(cache2.contains("key5"));
        assertTrue(cache2.contains("key6"));
        assertTrue(cache2.contains("key7"));
    }

    @Test
    public void testLruCacheSerializablePerformance() throws Exception {
        for (int i = 0; i < 10000; i++) {
            testLruCacheSerializable();
        }
    }

    private static class NonEncodingResponse extends TesterHttpServletResponse {

        @Override
        public String encodeRedirectURL(String url) {
            return url;
        }

        @Override
        public String encodeRedirectUrl(String url) {
            return url;
        }

        @Override
        public String encodeURL(String url) {
            return url;
        }

        @Override
        public String encodeUrl(String url) {
            return url;
        }
    }
}
