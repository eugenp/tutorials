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

import java.util.Enumeration;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

import org.apache.tomcat.unittest.TesterServletContext;

public class TesterFilterConfigs {
    public static final String HTTPS_WWW_APACHE_ORG = "https://www.apache.org";
    public static final String HTTP_TOMCAT_APACHE_ORG =
            "http://tomcat.apache.org";
    public static final String EXPOSED_HEADERS = "X-CUSTOM-HEADER";
    /**
     * Any origin
     */
    public static final String ANY_ORIGIN = "*";

    public static final TesterServletContext mockServletContext =
            new TesterServletContext();

    public static FilterConfig getDefaultFilterConfig() {
        final String allowedHttpHeaders =
                CorsFilter.DEFAULT_ALLOWED_HTTP_HEADERS;
        final String allowedHttpMethods =
                CorsFilter.DEFAULT_ALLOWED_HTTP_METHODS;
        final String allowedOrigins = CorsFilter.DEFAULT_ALLOWED_ORIGINS;
        final String exposedHeaders = CorsFilter.DEFAULT_EXPOSED_HEADERS;
        final String supportCredentials =
                CorsFilter.DEFAULT_SUPPORTS_CREDENTIALS;
        final String preflightMaxAge =
                CorsFilter.DEFAULT_PREFLIGHT_MAXAGE;
        final String decorateRequest = CorsFilter.DEFAULT_DECORATE_REQUEST;

        return generateFilterConfig(allowedHttpHeaders, allowedHttpMethods,
                allowedOrigins, exposedHeaders, supportCredentials,
                preflightMaxAge, decorateRequest);
    }

    public static FilterConfig getFilterConfigAnyOriginAndSupportsCredentials() {
        final String allowedHttpHeaders =
                CorsFilter.DEFAULT_ALLOWED_HTTP_HEADERS;
        final String allowedHttpMethods =
                CorsFilter.DEFAULT_ALLOWED_HTTP_METHODS + ",PUT";
        final String allowedOrigins = CorsFilter.DEFAULT_ALLOWED_ORIGINS;
        final String exposedHeaders = CorsFilter.DEFAULT_EXPOSED_HEADERS;
        final String supportCredentials = "true";
        final String preflightMaxAge =
                CorsFilter.DEFAULT_PREFLIGHT_MAXAGE;
        final String decorateRequest = CorsFilter.DEFAULT_DECORATE_REQUEST;

        return generateFilterConfig(allowedHttpHeaders, allowedHttpMethods,
                allowedOrigins, exposedHeaders, supportCredentials,
                preflightMaxAge, decorateRequest);
    }

    public static FilterConfig
            getFilterConfigAnyOriginAndSupportsCredentialsDisabled() {
        final String allowedHttpHeaders =
                CorsFilter.DEFAULT_ALLOWED_HTTP_HEADERS;
        final String allowedHttpMethods =
                CorsFilter.DEFAULT_ALLOWED_HTTP_METHODS + ",PUT";
        final String allowedOrigins = CorsFilter.DEFAULT_ALLOWED_ORIGINS;
        final String exposedHeaders = CorsFilter.DEFAULT_EXPOSED_HEADERS;
        final String supportCredentials = "false";
        final String preflightMaxAge =
                CorsFilter.DEFAULT_PREFLIGHT_MAXAGE;
        final String decorateRequest = CorsFilter.DEFAULT_DECORATE_REQUEST;

        return generateFilterConfig(allowedHttpHeaders, allowedHttpMethods,
                allowedOrigins, exposedHeaders, supportCredentials,
                preflightMaxAge, decorateRequest);
    }

    public static FilterConfig
            getFilterConfigSpecificOriginAndSupportsCredentialsDisabled() {
        final String allowedHttpHeaders =
                CorsFilter.DEFAULT_ALLOWED_HTTP_HEADERS;
        final String allowedHttpMethods =
                CorsFilter.DEFAULT_ALLOWED_HTTP_METHODS + ",PUT";
        final String allowedOrigins =
                HTTP_TOMCAT_APACHE_ORG + "," + HTTPS_WWW_APACHE_ORG;
        final String exposedHeaders = CorsFilter.DEFAULT_EXPOSED_HEADERS;
        final String supportCredentials = "false";
        final String preflightMaxAge =
                CorsFilter.DEFAULT_PREFLIGHT_MAXAGE;
        final String decorateRequest = CorsFilter.DEFAULT_DECORATE_REQUEST;

        return generateFilterConfig(allowedHttpHeaders, allowedHttpMethods,
                allowedOrigins, exposedHeaders, supportCredentials,
                preflightMaxAge, decorateRequest);
    }

    public static FilterConfig getFilterConfigSpecificOriginNullAllowed() {
        final String allowedHttpHeaders =
                CorsFilter.DEFAULT_ALLOWED_HTTP_HEADERS;
        final String allowedHttpMethods =
                CorsFilter.DEFAULT_ALLOWED_HTTP_METHODS;
        final String allowedOrigins = HTTP_TOMCAT_APACHE_ORG + ",null";
        final String exposedHeaders = CorsFilter.DEFAULT_EXPOSED_HEADERS;
        final String supportCredentials =
                CorsFilter.DEFAULT_SUPPORTS_CREDENTIALS;
        final String preflightMaxAge =
                CorsFilter.DEFAULT_PREFLIGHT_MAXAGE;
        final String decorateRequest = CorsFilter.DEFAULT_DECORATE_REQUEST;

        return generateFilterConfig(allowedHttpHeaders, allowedHttpMethods,
                allowedOrigins, exposedHeaders, supportCredentials,
                preflightMaxAge, decorateRequest);
    }

    public static FilterConfig getFilterConfigWithExposedHeaders() {
        final String allowedHttpHeaders =
                CorsFilter.DEFAULT_ALLOWED_HTTP_HEADERS;
        final String allowedHttpMethods =
                CorsFilter.DEFAULT_ALLOWED_HTTP_METHODS;
        final String allowedOrigins = CorsFilter.DEFAULT_ALLOWED_ORIGINS;
        final String exposedHeaders = EXPOSED_HEADERS;
        final String supportCredentials =
                CorsFilter.DEFAULT_SUPPORTS_CREDENTIALS;
        final String preflightMaxAge =
                CorsFilter.DEFAULT_PREFLIGHT_MAXAGE;
        final String decorateRequest = CorsFilter.DEFAULT_DECORATE_REQUEST;

        return generateFilterConfig(allowedHttpHeaders, allowedHttpMethods,
                allowedOrigins, exposedHeaders, supportCredentials,
                preflightMaxAge, decorateRequest);
    }

    public static FilterConfig getSecureFilterConfig() {
        final String allowedHttpHeaders =
                CorsFilter.DEFAULT_ALLOWED_HTTP_HEADERS;
        final String allowedHttpMethods =
                CorsFilter.DEFAULT_ALLOWED_HTTP_METHODS + ",PUT";
        final String allowedOrigins = HTTPS_WWW_APACHE_ORG;
        final String exposedHeaders = CorsFilter.DEFAULT_EXPOSED_HEADERS;
        final String supportCredentials = "true";
        final String preflightMaxAge =
                CorsFilter.DEFAULT_PREFLIGHT_MAXAGE;
        final String decorateRequest = CorsFilter.DEFAULT_DECORATE_REQUEST;

        return generateFilterConfig(allowedHttpHeaders, allowedHttpMethods,
                allowedOrigins, exposedHeaders, supportCredentials,
                preflightMaxAge, decorateRequest);
    }

    public static FilterConfig getNullFilterConfig() {
        return generateFilterConfig(null, null, null, null, null, null, null);
    }

    public static FilterConfig getSpecificOriginFilterConfig() {
        final String allowedOrigins =
                HTTPS_WWW_APACHE_ORG + "," + HTTP_TOMCAT_APACHE_ORG;

        final String allowedHttpHeaders =
                CorsFilter.DEFAULT_ALLOWED_HTTP_HEADERS;
        final String allowedHttpMethods =
                CorsFilter.DEFAULT_ALLOWED_HTTP_METHODS + ",PUT";
        final String exposedHeaders = CorsFilter.DEFAULT_EXPOSED_HEADERS;
        final String supportCredentials =
                CorsFilter.DEFAULT_SUPPORTS_CREDENTIALS;
        final String preflightMaxAge =
                CorsFilter.DEFAULT_PREFLIGHT_MAXAGE;
        final String decorateRequest = CorsFilter.DEFAULT_DECORATE_REQUEST;

        return generateFilterConfig(allowedHttpHeaders, allowedHttpMethods,
                allowedOrigins, exposedHeaders, supportCredentials,
                preflightMaxAge, decorateRequest);
    }

    public static FilterConfig getSpecificOriginFilterConfigNegativeMaxAge() {
        final String allowedOrigins =
                HTTPS_WWW_APACHE_ORG + "," + HTTP_TOMCAT_APACHE_ORG;

        final String allowedHttpHeaders =
                CorsFilter.DEFAULT_ALLOWED_HTTP_HEADERS;
        final String allowedHttpMethods =
                CorsFilter.DEFAULT_ALLOWED_HTTP_METHODS + ",PUT";
        final String exposedHeaders = CorsFilter.DEFAULT_EXPOSED_HEADERS;
        final String supportCredentials =
                CorsFilter.DEFAULT_SUPPORTS_CREDENTIALS;
        final String preflightMaxAge = "-1";
        final String decorateRequest = CorsFilter.DEFAULT_DECORATE_REQUEST;

        return generateFilterConfig(allowedHttpHeaders, allowedHttpMethods,
                allowedOrigins, exposedHeaders, supportCredentials,
                preflightMaxAge, decorateRequest);
    }

    public static FilterConfig getFilterConfigInvalidMaxPreflightAge() {
        final String allowedHttpHeaders =
                CorsFilter.DEFAULT_ALLOWED_HTTP_HEADERS;
        final String allowedHttpMethods =
                CorsFilter.DEFAULT_ALLOWED_HTTP_METHODS;
        final String allowedOrigins = CorsFilter.DEFAULT_ALLOWED_ORIGINS;
        final String exposedHeaders = CorsFilter.DEFAULT_EXPOSED_HEADERS;
        final String supportCredentials =
                CorsFilter.DEFAULT_SUPPORTS_CREDENTIALS;
        final String preflightMaxAge = "abc";
        final String decorateRequest = CorsFilter.DEFAULT_DECORATE_REQUEST;

        return generateFilterConfig(allowedHttpHeaders, allowedHttpMethods,
                allowedOrigins, exposedHeaders, supportCredentials,
                preflightMaxAge, decorateRequest);
    }

    public static FilterConfig getEmptyFilterConfig() {
        final String allowedHttpHeaders = "";
        final String allowedHttpMethods = "";
        final String allowedOrigins = "";
        final String exposedHeaders = "";
        final String supportCredentials = "";
        final String preflightMaxAge = "";
        final String decorateRequest = "";

        return generateFilterConfig(allowedHttpHeaders, allowedHttpMethods,
                allowedOrigins, exposedHeaders, supportCredentials,
                preflightMaxAge, decorateRequest);
    }

    public static FilterConfig getFilterConfigDecorateRequestDisabled() {
        final String allowedHttpHeaders =
                CorsFilter.DEFAULT_ALLOWED_HTTP_HEADERS;
        final String allowedHttpMethods =
                CorsFilter.DEFAULT_ALLOWED_HTTP_METHODS;
        final String allowedOrigins = CorsFilter.DEFAULT_ALLOWED_ORIGINS;
        final String exposedHeaders = CorsFilter.DEFAULT_EXPOSED_HEADERS;
        final String supportCredentials =
                CorsFilter.DEFAULT_SUPPORTS_CREDENTIALS;
        final String preflightMaxAge =
                CorsFilter.DEFAULT_PREFLIGHT_MAXAGE;
        final String decorateRequest = "false";

        return generateFilterConfig(allowedHttpHeaders, allowedHttpMethods,
                allowedOrigins, exposedHeaders, supportCredentials,
                preflightMaxAge, decorateRequest);
    }

    private static FilterConfig generateFilterConfig(
            final String allowedHttpHeaders, final String allowedHttpMethods,
            final String allowedOrigins, final String exposedHeaders,
            final String supportCredentials, final String preflightMaxAge,
            final String decorateRequest) {
        FilterConfig filterConfig = new FilterConfig() {

            @Override
            public String getFilterName() {
                return "cors-filter";
            }

            @Override
            public ServletContext getServletContext() {
                return mockServletContext;
            }

            @Override
            public String getInitParameter(String name) {
                if (CorsFilter.PARAM_CORS_ALLOWED_HEADERS
                        .equalsIgnoreCase(name)) {
                    return allowedHttpHeaders;
                } else if (CorsFilter.PARAM_CORS_ALLOWED_METHODS
                        .equalsIgnoreCase(name)) {
                    return allowedHttpMethods;
                } else if (CorsFilter.PARAM_CORS_ALLOWED_ORIGINS
                        .equalsIgnoreCase(name)) {
                    return allowedOrigins;
                } else if (CorsFilter.PARAM_CORS_EXPOSED_HEADERS
                        .equalsIgnoreCase(name)) {
                    return exposedHeaders;
                } else if (CorsFilter.PARAM_CORS_SUPPORT_CREDENTIALS
                        .equalsIgnoreCase(name)) {
                    return supportCredentials;
                } else if (CorsFilter.PARAM_CORS_PREFLIGHT_MAXAGE
                        .equalsIgnoreCase(name)) {
                    return preflightMaxAge;
                } else if (CorsFilter.PARAM_CORS_REQUEST_DECORATE
                        .equalsIgnoreCase(name)) {
                    return decorateRequest;
                }
                return null;
            }

            @Override
            public Enumeration<String> getInitParameterNames() {
                return null;
            }
        };

        return filterConfig;
    }
}
