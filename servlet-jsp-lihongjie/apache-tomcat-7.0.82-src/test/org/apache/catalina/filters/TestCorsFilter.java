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
package org.apache.catalina.filters;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

public class TestCorsFilter {
    private FilterChain filterChain = new TesterFilterChain();

    /**
     * Tests if a GET request is treated as simple request.
     *
     * @See http://www.w3.org/TR/cors/#simple-method
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void testDoFilterSimpleGET() throws IOException, ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG);
        request.setMethod("GET");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getDefaultFilterConfig());
        corsFilter.doFilter(request, response, filterChain);

        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN).equals(
                "https://www.apache.org"));
        Assert.assertTrue(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE).equals(
                CorsFilter.CORSRequestType.SIMPLE.name().toLowerCase(Locale.ENGLISH)));
    }

    /**
     * Tests if a POST request is treated as simple request.
     *
     * @See http://www.w3.org/TR/cors/#simple-method
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void testDoFilterSimplePOST() throws IOException, ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG);
        request.setContentType("text/plain");
        request.setMethod("POST");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getDefaultFilterConfig());
        corsFilter.doFilter(request, response, filterChain);

        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN).equals(
                "https://www.apache.org"));
        Assert.assertTrue(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE).equals(
                CorsFilter.CORSRequestType.SIMPLE.name().toLowerCase(Locale.ENGLISH)));
    }

    /**
     * Tests if a HEAD request is treated as simple request.
     *
     * @See http://www.w3.org/TR/cors/#simple-method
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void testDoFilterSimpleHEAD() throws IOException, ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG);
        request.setMethod("HEAD");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getDefaultFilterConfig());
        corsFilter.doFilter(request, response, filterChain);

        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN).equals(
                "https://www.apache.org"));
        Assert.assertTrue(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE).equals(
                CorsFilter.CORSRequestType.SIMPLE.name().toLowerCase(Locale.ENGLISH)));
    }

    /**
     * Test the presence of specific origin in response, when '*' is not used.
     *
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void testDoFilterSimpleSpecificHeader() throws IOException,
            ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG);
        request.setMethod("POST");
        request.setContentType("text/plain");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getSpecificOriginFilterConfig());
        corsFilter.doFilter(request, response, filterChain);

        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE).equals(
                CorsFilter.CORSRequestType.SIMPLE.name().toLowerCase(Locale.ENGLISH)));
    }

    /**
     * Tests the presence of the origin (and not '*') in the response, when
     * supports credentials is enabled alongwith any origin, '*'.
     *
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void testDoFilterSimpleAnyOriginAndSupportsCredentials()
            throws IOException, ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG);
        request.setMethod("GET");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getFilterConfigAnyOriginAndSupportsCredentials());
        corsFilter.doFilter(request, response, filterChain);

        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS)
                .equals(
                        "true"));
        Assert.assertTrue(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE).equals(
                CorsFilter.CORSRequestType.SIMPLE.name().toLowerCase(Locale.ENGLISH)));
    }

    /**
     * Tests the presence of the origin (and not '*') in the response, when
     * supports credentials is enabled alongwith any origin, '*'.
     *
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void testDoFilterSimpleAnyOriginAndSupportsCredentialsDisabled()
            throws IOException, ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG);
        request.setMethod("GET");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getFilterConfigAnyOriginAndSupportsCredentialsDisabled());
        corsFilter.doFilter(request, response, filterChain);

        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN).equals(
                TesterFilterConfigs.ANY_ORIGIN));
        Assert.assertNull(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS));
        Assert.assertTrue(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE).equals(
                CorsFilter.CORSRequestType.SIMPLE.name().toLowerCase(Locale.ENGLISH)));
    }

    /**
     * Tests the presence of exposed headers in response, if configured.
     *
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void testDoFilterSimpleWithExposedHeaders() throws IOException,
            ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG);
        request.setMethod("POST");
        request.setContentType("text/plain");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getFilterConfigWithExposedHeaders());
        corsFilter.doFilter(request, response, filterChain);

        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN).equals(
                "https://www.apache.org"));
        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_EXPOSE_HEADERS)
                .equals(TesterFilterConfigs.EXPOSED_HEADERS));
        Assert.assertTrue(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE).equals(
                CorsFilter.CORSRequestType.SIMPLE.name().toLowerCase(Locale.ENGLISH)));
    }

    /**
     * Checks if an OPTIONS request is processed as pre-flight.
     *
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void testDoFilterPreflight() throws IOException, ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG);
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD, "PUT");
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS,
                "Content-Type");
        request.setMethod("OPTIONS");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getSpecificOriginFilterConfig());
        corsFilter.doFilter(request, response, filterChain);

        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE).equals(
                CorsFilter.CORSRequestType.PRE_FLIGHT.name().toLowerCase(Locale.ENGLISH)));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_HEADERS).equals(
                "Content-Type"));
    }

    /**
     * Checks if an OPTIONS request is processed as pre-flight where any origin
     * is enabled.
     *
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void testDoFilterPreflightAnyOrigin() throws IOException,
            ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG);
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD, "PUT");
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS,
                "Content-Type");
        request.setMethod("OPTIONS");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getSpecificOriginFilterConfig());
        corsFilter.doFilter(request, response, filterChain);

        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE).equals(
                CorsFilter.CORSRequestType.PRE_FLIGHT.name().toLowerCase(Locale.ENGLISH)));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_HEADERS).equals(
                "Content-Type"));
    }

    /**
     * Checks if an OPTIONS request is processed as pre-flight.
     *
     * @throws IOException
     * @throws ServletException
     */
    @Test
    public void testDoFilterPreflightInvalidOrigin() throws IOException,
            ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                "http://www.example.com");
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD, "PUT");
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS,
                "Content-Type");
        request.setMethod("OPTIONS");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getSpecificOriginFilterConfig());
        corsFilter.doFilter(request, response, filterChain);

        Assert.assertEquals(response.getStatus(),
                HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    public void testDoFilterPreflightNegativeMaxAge() throws IOException,
            ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG);
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD, "PUT");
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS,
                "Content-Type");
        request.setMethod("OPTIONS");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getSpecificOriginFilterConfigNegativeMaxAge());
        corsFilter.doFilter(request, response, filterChain);

        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertNull(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_MAX_AGE));
        Assert.assertTrue(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE).equals(
                CorsFilter.CORSRequestType.PRE_FLIGHT.name().toLowerCase(Locale.ENGLISH)));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_HEADERS).equals(
                "Content-Type"));
    }

    @Test
    public void testDoFilterPreflightWithCredentials() throws IOException,
            ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG);
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD, "PUT");
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS,
                "Content-Type");
        request.setMethod("OPTIONS");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getSecureFilterConfig());
        corsFilter.doFilter(request, response, filterChain);

        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS)
                .equals("true"));
        Assert.assertTrue(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE).equals(
                CorsFilter.CORSRequestType.PRE_FLIGHT.name().toLowerCase(Locale.ENGLISH)));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_HEADERS).equals(
                "Content-Type"));
    }

    @Test
    public void testDoFilterPreflightWithoutCredentialsAndSpecificOrigin()
            throws IOException,
            ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG);
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD, "PUT");
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS,
                "Content-Type");
        request.setMethod("OPTIONS");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getFilterConfigSpecificOriginAndSupportsCredentialsDisabled());
        corsFilter.doFilter(request, response, filterChain);

        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertNull(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS));
        Assert.assertTrue(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE).equals(
                CorsFilter.CORSRequestType.PRE_FLIGHT.name().toLowerCase(Locale.ENGLISH)));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_HEADERS).equals(
                "Content-Type"));
    }

    /**
     * Negative test, when a CORS request arrives, with no origin header.
     */
    @Test
    public void testDoFilterNoOrigin() throws IOException, ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();

        request.setMethod("POST");
        request.setContentType("text/plain");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.NOT_CORS, requestType);

        corsFilter.doFilter(request, response, filterChain);

        Assert.assertFalse(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
    }

    /*
     * Negative test, when a non-CORS request arrives, with an origin header.
     */
    @Test
    public void testDoFilterSameHostWithOrigin01() throws IOException, ServletException {
        doTestDoFilterSameHostWithOrigin01(
                "http://localhost:8080", "http", "localhost", 8080, false);
    }

    @Test
    public void testDoFilterSameHostWithOrigin02() throws IOException, ServletException {
        doTestDoFilterSameHostWithOrigin01(
                "http://localhost:8080", "https", "localhost", 8080, true);
    }

    @Test
    public void testDoFilterSameHostWithOrigin03() throws IOException, ServletException {
        doTestDoFilterSameHostWithOrigin01(
                "http://localhost:8080", "http", "localhost", 8081, true);
    }

    @Test
    public void testDoFilterSameHostWithOrigin04() throws IOException, ServletException {
        doTestDoFilterSameHostWithOrigin01(
                "http://localhost:8080", "http", "foo.dev.local", 8080, true);
    }

    @Test
    public void testDoFilterSameHostWithOrigin05() throws IOException, ServletException {
        doTestDoFilterSameHostWithOrigin01(
                "https://localhost:8443", "https", "localhost", 8443, false);
    }

    @Test
    public void testDoFilterSameHostWithOrigin06() throws IOException, ServletException {
        doTestDoFilterSameHostWithOrigin01(
                "https://localhost", "https", "localhost", 443, false);
    }

    @Test
    public void testDoFilterSameHostWithOrigin07() throws IOException, ServletException {
        doTestDoFilterSameHostWithOrigin01(
                "http://localhost", "http", "localhost", 80, false);
    }

    private void doTestDoFilterSameHostWithOrigin01(String origin, String scheme, String host,
            int port, boolean isCors) throws IOException, ServletException {

        TesterHttpServletRequest request = new TesterHttpServletRequest();

        request.setMethod("POST");
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN, origin);
        request.setScheme(scheme);
        request.setServerName(host);
        request.setServerPort(port);
        request.setContentType("text/plain");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        if (isCors) {
            Assert.assertNotEquals(CorsFilter.CORSRequestType.NOT_CORS, requestType);
        } else {
            Assert.assertEquals(CorsFilter.CORSRequestType.NOT_CORS, requestType);
        }

        corsFilter.doFilter(request, response, filterChain);

        if (isCors) {
            Assert.assertTrue(((Boolean) request.getAttribute(
                    CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
        } else {
            Assert.assertFalse(((Boolean) request.getAttribute(
                    CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
        }
    }

    @Test
    public void testDoFilterInvalidCORSOriginNotAllowed() throws IOException,
            ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                "www.google.com");
        request.setMethod("POST");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getSpecificOriginFilterConfig());
        corsFilter.doFilter(request, response, filterChain);

        Assert.assertEquals(HttpServletResponse.SC_FORBIDDEN,
                response.getStatus());
    }

    /*
     * A CORS request arrives with a "null" origin which is allowed by default.
     */
    @Test
    public void testDoFilterNullOriginAllowedByDefault() throws IOException,
            ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();

        request.setMethod("POST");
        request.setContentType("text/plain");
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN, "null");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.SIMPLE, requestType);

        corsFilter.doFilter(request, response, filterChain);

        Assert.assertTrue(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
    }

    /*
     * A CORS request arrives with a "null" origin which is explicitly allowed
     * by configuration.
     */
    @Test
    public void testDoFilterNullOriginAllowedByConfiguration() throws
            IOException, ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();

        request.setMethod("POST");
        request.setContentType("text/plain");
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN, "null");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(
                TesterFilterConfigs.getFilterConfigSpecificOriginNullAllowed());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.SIMPLE, requestType);

        corsFilter.doFilter(request, response, filterChain);

        Assert.assertTrue(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
    }

    @Test(expected = ServletException.class)
    public void testDoFilterNullRequestNullResponse() throws IOException,
            ServletException {
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getDefaultFilterConfig());
        corsFilter.doFilter(null, null, filterChain);
    }

    @Test(expected = ServletException.class)
    public void testDoFilterNullRequestResponse() throws IOException,
            ServletException {
        TesterHttpServletResponse response = new TesterHttpServletResponse();
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getDefaultFilterConfig());
        corsFilter.doFilter(null, response, filterChain);
    }

    @Test(expected = ServletException.class)
    public void testDoFilterRequestNullResponse() throws IOException,
            ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getDefaultFilterConfig());
        corsFilter.doFilter(request, null, filterChain);
    }

    @Test
    public void testInitDefaultFilterConfig() throws IOException,
            ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG);
        request.setMethod("GET");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(null);
        corsFilter.doFilter(request, response, filterChain);

        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN).equals(
                "https://www.apache.org"));
        Assert.assertTrue(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_ORIGIN).equals(
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG));
        Assert.assertTrue(request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE).equals(
                CorsFilter.CORSRequestType.SIMPLE.name().toLowerCase(Locale.ENGLISH)));
    }

    @Test(expected = ServletException.class)
    public void testInitInvalidFilterConfig() throws ServletException {
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getFilterConfigInvalidMaxPreflightAge());
        // If we don't get an exception at this point, then all mocked objects
        // worked as expected.
    }

    /**
     * Tests if a non-simple request is given to simple request handler.
     *
     * @throws IOException
     * @throws ServletException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNotSimple() throws IOException, ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG);
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD, "PUT");
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS,
                "Content-Type");
        request.setMethod("OPTIONS");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        corsFilter.handleSimpleCORS(request, response, filterChain);
    }

    /**
     * When a non-preflight request is given to a pre-flight request handler.
     *
     * @throws IOException
     * @throws ServletException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNotPreflight() throws IOException, ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG);
        request.setMethod("GET");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getDefaultFilterConfig());
        corsFilter.handlePreflightCORS(request, response, filterChain);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecorateCORSPropertiesNullRequestNullCORSRequestType() {
        CorsFilter.decorateCORSProperties(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecorateCORSPropertiesNullRequestValidCORSRequestType() {
        CorsFilter.decorateCORSProperties(null,
                CorsFilter.CORSRequestType.SIMPLE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecorateCORSPropertiesValidRequestNullRequestType() {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        CorsFilter.decorateCORSProperties(request, null);
    }

    @Test
    public void testDecorateCORSPropertiesCORSRequestTypeNotCORS() {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        CorsFilter.decorateCORSProperties(request,
                CorsFilter.CORSRequestType.NOT_CORS);
        Assert.assertFalse(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
    }

    @Test
    public void testDecorateCORSPropertiesCORSRequestTypeInvalidCORS() {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        CorsFilter
                .decorateCORSProperties(request,
                        CorsFilter.CORSRequestType.INVALID_CORS);
        Assert.assertNull(request
                .getAttribute(CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST));
    }

    @Test
    public void testCheckSimpleRequestTypeAnyOrigin() throws ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                "http://www.w3.org");
        request.setMethod("GET");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.SIMPLE, requestType);
    }

    /**
     * Happy path test, when a valid CORS Simple request arrives.
     *
     * @throws ServletException
     */
    @Test
    public void testCheckSimpleRequestType() throws ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTP_TOMCAT_APACHE_ORG);
        request.setMethod("GET");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.SIMPLE, requestType);
    }

    /**
     * Happy path test, when a valid CORS Simple request arrives.
     *
     * @throws ServletException
     */
    @Test
    public void testCheckActualRequestType() throws ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTP_TOMCAT_APACHE_ORG);
        request.setMethod("PUT");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.ACTUAL, requestType);
    }

    /**
     * Happy path test, when a valid CORS Simple request arrives.
     *
     * @throws ServletException
     */
    @Test
    public void testCheckActualRequestTypeMethodPOSTNotSimpleHeaders()
            throws ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTP_TOMCAT_APACHE_ORG);
        request.setMethod("POST");
        request.setContentType("application/json");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.ACTUAL, requestType);
    }

    /**
     * Happy path test, when a valid CORS Pre-flight request arrives.
     *
     * @throws ServletException
     */
    @Test
    public void testCheckPreFlightRequestType() throws ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTP_TOMCAT_APACHE_ORG);
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD,
                "PUT");
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS,
                "Content-Type");
        request.setMethod("OPTIONS");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.PRE_FLIGHT, requestType);
    }

    /**
     * when a valid CORS Pre-flight request arrives, with no
     * Access-Control-Request-Method
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testCheckPreFlightRequestTypeNoACRM() throws ServletException,
            IOException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTP_TOMCAT_APACHE_ORG);

        request.setMethod("OPTIONS");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.ACTUAL, requestType);
    }

    /**
     * when a valid CORS Pre-flight request arrives, with empty
     * Access-Control-Request-Method
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testCheckPreFlightRequestTypeEmptyACRM()
            throws ServletException, IOException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTP_TOMCAT_APACHE_ORG);
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD,
                "");
        request.setMethod("OPTIONS");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.INVALID_CORS,
                requestType);
    }

    /**
     * Happy path test, when a valid CORS Pre-flight request arrives.
     *
     * @throws ServletException
     */
    @Test
    public void testCheckPreFlightRequestTypeNoHeaders()
            throws ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTP_TOMCAT_APACHE_ORG);
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD,
                "PUT");
        request.setMethod("OPTIONS");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.PRE_FLIGHT, requestType);
    }

    /**
     * Section 6.2.3
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testCheckPreFlightRequestTypeInvalidRequestMethod()
            throws ServletException, IOException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        TesterHttpServletResponse response = new TesterHttpServletResponse();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTP_TOMCAT_APACHE_ORG);
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD,
                "POLITE");
        request.setMethod("OPTIONS");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        corsFilter.doFilter(request, response, filterChain);
        Assert.assertEquals(HttpServletResponse.SC_FORBIDDEN,
                response.getStatus());
    }

    /**
     * Section Section 6.2.5
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testCheckPreFlightRequestTypeUnsupportedRequestMethod()
            throws ServletException, IOException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        TesterHttpServletResponse response = new TesterHttpServletResponse();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTP_TOMCAT_APACHE_ORG);
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD,
                "TRACE");
        request.setMethod("OPTIONS");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        corsFilter.doFilter(request, response, filterChain);
        Assert.assertEquals(HttpServletResponse.SC_FORBIDDEN,
                response.getStatus());
    }

    /**
     * Section Section 6.2.6
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testCheckPreFlightRequestTypeUnsupportedRequestHeaders()
            throws ServletException, IOException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        TesterHttpServletResponse response = new TesterHttpServletResponse();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG);
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD,
                "PUT");
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS,
                "X-ANSWER");
        request.setMethod("OPTIONS");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getSecureFilterConfig());
        corsFilter.doFilter(request, response, filterChain);
        Assert.assertEquals(HttpServletResponse.SC_FORBIDDEN,
                response.getStatus());
    }

    /**
     * Section Section 6.2.7
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testCheckPreFlightRequestTypeAnyOriginNoWithCredentials()
            throws ServletException, IOException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        TesterHttpServletResponse response = new TesterHttpServletResponse();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTP_TOMCAT_APACHE_ORG);
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD,
                "PUT");
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS,
                "Origin");
        request.setMethod("OPTIONS");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getFilterConfigAnyOriginAndSupportsCredentialsDisabled());
        corsFilter.doFilter(request, response, filterChain);
        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN).equals(
                "*"));
        Assert.assertNull(response
                .getHeader(CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_CREDENTIALS));
    }

    @Test
    public void testCheckPreFlightRequestTypeOriginNotAllowed()
            throws ServletException, IOException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        TesterHttpServletResponse response = new TesterHttpServletResponse();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                "www.ebay.com");
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD,
                "PUT");
        request.setMethod("OPTIONS");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getSecureFilterConfig());
        corsFilter.doFilter(request, response, filterChain);
        Assert.assertEquals(HttpServletResponse.SC_FORBIDDEN,
                response.getStatus());
    }

    /**
     * Happy path test, when a valid CORS Pre-flight request arrives.
     *
     * @throws ServletException
     */
    @Test
    public void testCheckPreFlightRequestTypeEmptyHeaders()
            throws ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTP_TOMCAT_APACHE_ORG);
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_METHOD,
                "PUT");
        request.setHeader(
                CorsFilter.REQUEST_HEADER_ACCESS_CONTROL_REQUEST_HEADERS,
                "");
        request.setMethod("OPTIONS");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.PRE_FLIGHT, requestType);
    }

    /**
     * Negative test, when a CORS request arrives, with an empty origin.
     *
     * @throws ServletException
     */
    @Test
    public void testCheckNotCORSRequestTypeEmptyOrigin()
            throws ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                "");
        request.setMethod("GET");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.INVALID_CORS,
                requestType);
    }

    /**
     * Tests for failure, when a different domain is used, that's not in the
     * allowed list of origins.
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testCheckInvalidOrigin() throws ServletException, IOException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        TesterHttpServletResponse response = new TesterHttpServletResponse();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                "www.example.com");
        request.setMethod("GET");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getSpecificOriginFilterConfig());
        corsFilter.doFilter(request, response, filterChain);
        Assert.assertEquals(HttpServletResponse.SC_FORBIDDEN,
                response.getStatus());
    }

    /**
     * Tests for failure, when the 'null' origin is used, and it's not in the
     * list of allowed origins.
     */
    @Test
    public void testCheckNullOriginNotAllowed() throws ServletException,
            IOException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        TesterHttpServletResponse response = new TesterHttpServletResponse();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN, "null");
        request.setMethod("GET");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getSpecificOriginFilterConfig());
        corsFilter.doFilter(request, response, filterChain);
        Assert.assertEquals(HttpServletResponse.SC_FORBIDDEN,
                response.getStatus());
    }

    /*
     * Tests for failure, when a different sub-domain is used, that's not in the
     * allowed list of origins.
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testCheckInvalidOriginNotAllowedSubdomain()
            throws ServletException, IOException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        TesterHttpServletResponse response = new TesterHttpServletResponse();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                "http://commons.apache.org");
        request.setMethod("GET");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getSpecificOriginFilterConfig());
        corsFilter.doFilter(request, response, filterChain);
        Assert.assertEquals(HttpServletResponse.SC_FORBIDDEN,
                response.getStatus());
    }

    /**
     * PUT is not an allowed request method.
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testCheckInvalidRequestMethod() throws ServletException,
            IOException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        TesterHttpServletResponse response = new TesterHttpServletResponse();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                "http://tomcat.apache.org");
        request.setMethod("PUT");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        corsFilter.doFilter(request, response, filterChain);
        Assert.assertEquals(HttpServletResponse.SC_FORBIDDEN,
                response.getStatus());
    }

    /**
     * When requestMethod is null
     *
     * @throws ServletException
     */
    @Test
    public void testCheckNullRequestMethod() throws ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                "http://tomcat.apache.org");
        request.setMethod(null);
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getSpecificOriginFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.INVALID_CORS,
                requestType);
    }

    /**
     * "http://tomcat.apache.org" is an allowed origin and
     * "https://tomcat.apache.org" is not, because scheme doesn't match
     *
     * @throws ServletException
     */
    @Test
    public void testCheckForSchemeVariance() throws ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                "https://tomcat.apache.org");
        request.setMethod("POST");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getSpecificOriginFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.INVALID_CORS,
                requestType);
    }

    /**
     * "http://tomcat.apache.org" is an allowed origin and
     * "http://tomcat.apache.org:8080" is not, because ports doesn't match
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testCheckForPortVariance() throws ServletException, IOException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        TesterHttpServletResponse response = new TesterHttpServletResponse();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                "http://tomcat.apache.org:8080");
        request.setMethod("GET");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getSpecificOriginFilterConfig());
        corsFilter.doFilter(request, response, filterChain);
        Assert.assertEquals(HttpServletResponse.SC_FORBIDDEN,
                response.getStatus());
    }

    /**
     * Tests for failure, when an invalid {@link HttpServletRequest} is
     * encountered.
     *
     * @throws ServletException
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCheckRequestTypeNull() throws ServletException {
        HttpServletRequest request = null;
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.checkRequestType(request);
    }

    @Test
    public void testJoin() {
        Set<String> elements = new LinkedHashSet<String>();
        String separator = ",";
        elements.add("world");
        elements.add("peace");
        String join = CorsFilter.join(elements, separator);
        Assert.assertTrue("world,peace".equals(join));
    }

    @Test
    public void testJoinSingleElement() {
        Set<String> elements = new LinkedHashSet<String>();
        String separator = ",";
        elements.add("world");
        String join = CorsFilter.join(elements, separator);
        Assert.assertTrue("world".equals(join));
    }

    @Test
    public void testJoinSepNull() {
        Set<String> elements = new LinkedHashSet<String>();
        String separator = null;
        elements.add("world");
        elements.add("peace");
        String join = CorsFilter.join(elements, separator);
        Assert.assertTrue("world,peace".equals(join));
    }

    @Test
    public void testJoinElementsNull() {
        Set<String> elements = null;
        String separator = ",";
        String join = CorsFilter.join(elements, separator);

        Assert.assertNull(join);
    }

    @Test
    public void testJoinOneNullElement() {
        Set<String> elements = new LinkedHashSet<String>();
        String separator = ",";
        elements.add(null);
        elements.add("peace");
        String join = CorsFilter.join(elements, separator);
        Assert.assertTrue(",peace".equals(join));
    }

    @Test
    public void testJoinAllNullElements() {
        Set<String> elements = new LinkedHashSet<String>();
        String separator = ",";
        elements.add(null);
        elements.add(null);
        String join = CorsFilter.join(elements, separator);
        Assert.assertTrue("".equals(join));
    }

    @Test
    public void testJoinAllEmptyElements() {
        Set<String> elements = new LinkedHashSet<String>();
        String separator = ",";
        elements.add("");
        elements.add("");
        String join = CorsFilter.join(elements, separator);
        Assert.assertTrue("".equals(join));
    }

    @Test
    public void testJoinPipeSeparator() {
        Set<String> elements = new LinkedHashSet<String>();
        String separator = "|";
        elements.add("world");
        elements.add("peace");
        String join = CorsFilter.join(elements, separator);
        Assert.assertTrue("world|peace".equals(join));
    }

    @Test
    public void testWithFilterConfig() throws ServletException {
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getDefaultFilterConfig());
        Assert.assertTrue(corsFilter.getAllowedHttpHeaders().size() == 6);
        Assert.assertTrue(corsFilter.getAllowedHttpMethods().size() == 4);
        Assert.assertTrue(corsFilter.getAllowedOrigins().size() == 0);
        Assert.assertTrue(corsFilter.isAnyOriginAllowed());
        Assert.assertTrue(corsFilter.getExposedHeaders().size() == 0);
        Assert.assertTrue(corsFilter.isSupportsCredentials());
        Assert.assertTrue(corsFilter.getPreflightMaxAge() == 1800);
    }

    @Test(expected = ServletException.class)
    public void testWithFilterConfigInvalidPreflightAge()
            throws ServletException {
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getFilterConfigInvalidMaxPreflightAge());
    }

    @Test
    public void testWithStringParserEmpty() throws ServletException {
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getEmptyFilterConfig());
        Assert.assertTrue(corsFilter.getAllowedHttpHeaders().size() == 0);
        Assert.assertTrue(corsFilter.getAllowedHttpMethods().size() == 0);
        Assert.assertTrue(corsFilter.getAllowedOrigins().size() == 0);
        Assert.assertTrue(corsFilter.getExposedHeaders().size() == 0);
        Assert.assertFalse(corsFilter.isSupportsCredentials());
        Assert.assertTrue(corsFilter.getPreflightMaxAge() == 0);
    }

    /**
     * If an init param is null, it's default value will be used.
     *
     * @throws ServletException
     */
    @Test
    public void testWithStringParserNull() throws ServletException {
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getNullFilterConfig());
        Assert.assertTrue(corsFilter.getAllowedHttpHeaders().size() == 6);
        Assert.assertTrue(corsFilter.getAllowedHttpMethods().size() == 4);
        Assert.assertTrue(corsFilter.getAllowedOrigins().size() == 0);
        Assert.assertTrue(corsFilter.isAnyOriginAllowed());
        Assert.assertTrue(corsFilter.getExposedHeaders().size() == 0);
        Assert.assertTrue(corsFilter.isSupportsCredentials());
        Assert.assertTrue(corsFilter.getPreflightMaxAge() == 1800);
    }

    @Test
    public void testValidOrigin() {
        Assert.assertTrue(CorsFilter.isValidOrigin("http://www.w3.org"));
    }

    @Test
    public void testInValidOriginCRLF() {
        Assert.assertFalse(CorsFilter.isValidOrigin("http://www.w3.org\r\n"));
    }

    @Test
    public void testInValidOriginEncodedCRLF1() {
        Assert.assertFalse(CorsFilter.isValidOrigin("http://www.w3.org%0d%0a"));
    }

    @Test
    public void testInValidOriginEncodedCRLF2() {
        Assert.assertFalse(CorsFilter.isValidOrigin("http://www.w3.org%0D%0A"));
    }

    @Test
    public void testInValidOriginEncodedCRLF3() {
        Assert.assertFalse(CorsFilter
                .isValidOrigin("http://www.w3.org%0%0d%0ad%0%0d%0aa"));
    }

    @Test
    public void testCheckInvalidCRLF1() throws ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                "http://www.w3.org\r\n");
        request.setMethod("GET");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.INVALID_CORS,
                requestType);
    }

    @Test
    public void testCheckInvalidCRLF2() throws ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                "http://www.w3.org\r\n");
        request.setMethod("GET");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.INVALID_CORS,
                requestType);
    }

    @Test
    public void testCheckInvalidCRLF3() throws ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                "http://www.w3.org%0d%0a");
        request.setMethod("GET");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.INVALID_CORS,
                requestType);
    }

    @Test
    public void testCheckInvalidCRLF4() throws ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                "http://www.w3.org%0D%0A");
        request.setMethod("GET");
        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs
                .getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.INVALID_CORS,
                requestType);
    }

    @Test
    public void testDecorateRequestDisabled() throws IOException,
            ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN,
                TesterFilterConfigs.HTTPS_WWW_APACHE_ORG);
        request.setMethod("GET");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getFilterConfigDecorateRequestDisabled());
        corsFilter.doFilter(request, response, filterChain);

        Assert.assertTrue(response.getHeader(
                CorsFilter.RESPONSE_HEADER_ACCESS_CONTROL_ALLOW_ORIGIN).equals(
                "https://www.apache.org"));
        Assert.assertNull(request
                .getAttribute(CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST));
        Assert.assertNull(request
                .getAttribute(CorsFilter.HTTP_REQUEST_ATTRIBUTE_ORIGIN));
        Assert.assertNull(request
                .getAttribute(CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_HEADERS));
        Assert.assertNull(request
                .getAttribute(CorsFilter.HTTP_REQUEST_ATTRIBUTE_REQUEST_TYPE));
    }

    /*
     * A CORS request arrives with a "null" origin which is allowed by default.
     */
    @Test
    public void testContentTypeWithParameter() throws IOException,
            ServletException {
        TesterHttpServletRequest request = new TesterHttpServletRequest();

        request.setMethod("POST");
        request.setContentType("text/plain;charset=UTF-8");
        request.setHeader(CorsFilter.REQUEST_HEADER_ORIGIN, "null");
        TesterHttpServletResponse response = new TesterHttpServletResponse();

        CorsFilter corsFilter = new CorsFilter();
        corsFilter.init(TesterFilterConfigs.getDefaultFilterConfig());
        CorsFilter.CORSRequestType requestType =
                corsFilter.checkRequestType(request);
        Assert.assertEquals(CorsFilter.CORSRequestType.SIMPLE, requestType);

        corsFilter.doFilter(request, response, filterChain);

        Assert.assertTrue(((Boolean) request.getAttribute(
                CorsFilter.HTTP_REQUEST_ATTRIBUTE_IS_CORS_REQUEST)).booleanValue());
    }
}
