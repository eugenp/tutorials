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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.List;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

import org.apache.catalina.Context;
import org.apache.catalina.deploy.FilterDef;
import org.apache.catalina.deploy.FilterMap;
import org.apache.catalina.filters.ExpiresFilter.Duration;
import org.apache.catalina.filters.ExpiresFilter.DurationUnit;
import org.apache.catalina.filters.ExpiresFilter.ExpiresConfiguration;
import org.apache.catalina.filters.ExpiresFilter.StartingPoint;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.startup.TomcatBaseTest;

public class TestExpiresFilter extends TomcatBaseTest {
    public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    @Test
    public void testConfiguration() throws Exception {

        Tomcat tomcat = getTomcatInstance();
        Context root = tomcat.addContext("", TEMP_DIR);

        FilterDef filterDef = new FilterDef();
        filterDef.addInitParameter("ExpiresDefault", "access plus 1 month");
        filterDef.addInitParameter("ExpiresByType text/html",
                "access plus 1 month 15 days 2 hours");
        filterDef.addInitParameter("ExpiresByType image/gif",
                "modification plus 5 hours 3 minutes");
        filterDef.addInitParameter("ExpiresByType image/jpg", "A10000");
        filterDef.addInitParameter("ExpiresByType video/mpeg", "M20000");
        filterDef.addInitParameter("ExpiresExcludedResponseStatusCodes",
                "304, 503");

        ExpiresFilter expiresFilter = new ExpiresFilter();

        filterDef.setFilter(expiresFilter);
        filterDef.setFilterClass(ExpiresFilter.class.getName());
        filterDef.setFilterName(ExpiresFilter.class.getName());

        root.addFilterDef(filterDef);

        FilterMap filterMap = new FilterMap();
        filterMap.setFilterName(ExpiresFilter.class.getName());
        filterMap.addURLPattern("*");

        tomcat.start();
        try {
            // VERIFY EXCLUDED RESPONSE STATUS CODES
            {
                int[] excludedResponseStatusCodes = expiresFilter.getExcludedResponseStatusCodesAsInts();
                Assert.assertEquals(2, excludedResponseStatusCodes.length);
                Assert.assertEquals(304, excludedResponseStatusCodes[0]);
                Assert.assertEquals(503, excludedResponseStatusCodes[1]);
            }

            // VERIFY DEFAULT CONFIGURATION
            {
                ExpiresConfiguration expiresConfiguration = expiresFilter.getDefaultExpiresConfiguration();
                Assert.assertEquals(StartingPoint.ACCESS_TIME,
                        expiresConfiguration.getStartingPoint());
                Assert.assertEquals(1,
                        expiresConfiguration.getDurations().size());
                Assert.assertEquals(DurationUnit.MONTH,
                        expiresConfiguration.getDurations().get(0).getUnit());
                Assert.assertEquals(1, expiresConfiguration.getDurations().get(
                        0).getAmount());
            }

            // VERIFY TEXT/HTML
            {
                ExpiresConfiguration expiresConfiguration = expiresFilter.getExpiresConfigurationByContentType().get(
                        "text/html");
                Assert.assertEquals(StartingPoint.ACCESS_TIME,
                        expiresConfiguration.getStartingPoint());

                Assert.assertEquals(3,
                        expiresConfiguration.getDurations().size());

                Duration oneMonth = expiresConfiguration.getDurations().get(0);
                Assert.assertEquals(DurationUnit.MONTH, oneMonth.getUnit());
                Assert.assertEquals(1, oneMonth.getAmount());

                Duration fifteenDays = expiresConfiguration.getDurations().get(
                        1);
                Assert.assertEquals(DurationUnit.DAY, fifteenDays.getUnit());
                Assert.assertEquals(15, fifteenDays.getAmount());

                Duration twoHours = expiresConfiguration.getDurations().get(2);
                Assert.assertEquals(DurationUnit.HOUR, twoHours.getUnit());
                Assert.assertEquals(2, twoHours.getAmount());
            }
            // VERIFY IMAGE/GIF
            {
                ExpiresConfiguration expiresConfiguration = expiresFilter.getExpiresConfigurationByContentType().get(
                        "image/gif");
                Assert.assertEquals(StartingPoint.LAST_MODIFICATION_TIME,
                        expiresConfiguration.getStartingPoint());

                Assert.assertEquals(2,
                        expiresConfiguration.getDurations().size());

                Duration fiveHours = expiresConfiguration.getDurations().get(0);
                Assert.assertEquals(DurationUnit.HOUR, fiveHours.getUnit());
                Assert.assertEquals(5, fiveHours.getAmount());

                Duration threeMinutes = expiresConfiguration.getDurations().get(
                        1);
                Assert.assertEquals(DurationUnit.MINUTE, threeMinutes.getUnit());
                Assert.assertEquals(3, threeMinutes.getAmount());

            }
            // VERIFY IMAGE/JPG
            {
                ExpiresConfiguration expiresConfiguration = expiresFilter.getExpiresConfigurationByContentType().get(
                        "image/jpg");
                Assert.assertEquals(StartingPoint.ACCESS_TIME,
                        expiresConfiguration.getStartingPoint());

                Assert.assertEquals(1,
                        expiresConfiguration.getDurations().size());

                Duration tenThousandSeconds = expiresConfiguration.getDurations().get(
                        0);
                Assert.assertEquals(DurationUnit.SECOND,
                        tenThousandSeconds.getUnit());
                Assert.assertEquals(10000, tenThousandSeconds.getAmount());

            }
            // VERIFY VIDEO/MPEG
            {
                ExpiresConfiguration expiresConfiguration = expiresFilter.getExpiresConfigurationByContentType().get(
                        "video/mpeg");
                Assert.assertEquals(StartingPoint.LAST_MODIFICATION_TIME,
                        expiresConfiguration.getStartingPoint());

                Assert.assertEquals(1,
                        expiresConfiguration.getDurations().size());

                Duration twentyThousandSeconds = expiresConfiguration.getDurations().get(
                        0);
                Assert.assertEquals(DurationUnit.SECOND,
                        twentyThousandSeconds.getUnit());
                Assert.assertEquals(20000, twentyThousandSeconds.getAmount());
            }
        } finally {
            tomcat.stop();
        }
    }

    /**
     * Test that a resource with empty content is also processed
     */
    @Test
    public void testEmptyContent() throws Exception {
        HttpServlet servlet = new HttpServlet() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void service(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException,
                    IOException {
                response.setContentType("text/plain");
                // no content is written in the response
            }
        };

        validate(servlet, Integer.valueOf(7 * 60));
    }

    @Test
    public void testParseExpiresConfigurationCombinedDuration() {
        ExpiresFilter expiresFilter = new ExpiresFilter();
        ExpiresConfiguration actualConfiguration = expiresFilter.parseExpiresConfiguration("access plus 1 month 15 days 2 hours");

        Assert.assertEquals(StartingPoint.ACCESS_TIME,
                actualConfiguration.getStartingPoint());

        Assert.assertEquals(3, actualConfiguration.getDurations().size());

    }

    @Test
    public void testParseExpiresConfigurationMonoDuration() {
        ExpiresFilter expiresFilter = new ExpiresFilter();
        ExpiresConfiguration actualConfiguration = expiresFilter.parseExpiresConfiguration("access plus 2 hours");

        Assert.assertEquals(StartingPoint.ACCESS_TIME,
                actualConfiguration.getStartingPoint());

        Assert.assertEquals(1, actualConfiguration.getDurations().size());
        Assert.assertEquals(2,
                actualConfiguration.getDurations().get(0).getAmount());
        Assert.assertEquals(DurationUnit.HOUR,
                actualConfiguration.getDurations().get(0).getUnit());

    }

    @Test
    public void testSkipBecauseCacheControlMaxAgeIsDefined() throws Exception {
        HttpServlet servlet = new HttpServlet() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void service(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException,
                    IOException {
                response.setContentType("text/xml; charset=utf-8");
                response.addHeader("Cache-Control", "private, max-age=232");
                response.getWriter().print("Hello world");
            }
        };

        validate(servlet, Integer.valueOf(232));
    }

    @Test
    public void testExcludedResponseStatusCode() throws Exception {
        HttpServlet servlet = new HttpServlet() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void service(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException,
                    IOException {
                response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
                response.addHeader("ETag", "W/\"1934-1269208821000\"");
                response.addDateHeader("Date", System.currentTimeMillis());
            }
        };

        validate(servlet, null, HttpServletResponse.SC_NOT_MODIFIED);
    }

    @Test
    public void testNullContentType() throws Exception {
        HttpServlet servlet = new HttpServlet() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void service(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException,
                    IOException {
                response.setContentType(null);
            }
        };

        validate(servlet, Integer.valueOf(1 * 60));
    }

    @Test
    public void testSkipBecauseExpiresIsDefined() throws Exception {
        HttpServlet servlet = new HttpServlet() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void service(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException,
                    IOException {
                response.setContentType("text/xml; charset=utf-8");
                response.addDateHeader("Expires", System.currentTimeMillis());
                response.getWriter().print("Hello world");
            }
        };

        validate(servlet, null);
    }

    @Test
    public void testUseContentTypeExpiresConfiguration() throws Exception {
        HttpServlet servlet = new HttpServlet() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void service(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException,
                    IOException {
                response.setContentType("text/xml; charset=utf-8");
                response.getWriter().print("Hello world");
            }
        };

        validate(servlet, Integer.valueOf(3 * 60));
    }

    @Test
    public void testUseContentTypeWithoutCharsetExpiresConfiguration()
            throws Exception {
        HttpServlet servlet = new HttpServlet() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void service(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException,
                    IOException {
                response.setContentType("text/xml; charset=iso-8859-1");
                response.getWriter().print("Hello world");
            }
        };

        validate(servlet, Integer.valueOf(5 * 60));
    }

    @Test
    public void testUseDefaultConfiguration1() throws Exception {
        HttpServlet servlet = new HttpServlet() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void service(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException,
                    IOException {
                response.setContentType("image/jpeg");
                response.getWriter().print("Hello world");
            }
        };

        validate(servlet, Integer.valueOf(1 * 60));
    }

    @Test
    public void testUseDefaultConfiguration2() throws Exception {
        HttpServlet servlet = new HttpServlet() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void service(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException,
                    IOException {
                response.setContentType("image/jpeg");
                response.addHeader("Cache-Control", "private");

                response.getWriter().print("Hello world");
            }
        };

        validate(servlet, Integer.valueOf(1 * 60));
    }

    @Test
    public void testUseMajorTypeExpiresConfiguration() throws Exception {
        HttpServlet servlet = new HttpServlet() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void service(HttpServletRequest request,
                    HttpServletResponse response) throws ServletException,
                    IOException {
                response.setContentType("text/json; charset=iso-8859-1");
                response.getWriter().print("Hello world");
            }
        };

        validate(servlet, Integer.valueOf(7 * 60));
    }

    protected void validate(HttpServlet servlet, Integer expectedMaxAgeInSeconds)
            throws Exception {
        validate(servlet, expectedMaxAgeInSeconds, HttpURLConnection.HTTP_OK);
    }

    protected void validate(HttpServlet servlet,
            Integer expectedMaxAgeInSeconds, int expectedResponseStatusCode)
            throws Exception {
        // SETUP

        Tomcat tomcat = getTomcatInstance();
        Context root = tomcat.addContext("", TEMP_DIR);

        FilterDef filterDef = new FilterDef();
        filterDef.addInitParameter("ExpiresDefault", "access plus 1 minute");
        filterDef.addInitParameter("ExpiresByType text/xml;charset=utf-8",
                "access plus 3 minutes");
        filterDef.addInitParameter("ExpiresByType text/xml",
                "access plus 5 minutes");
        filterDef.addInitParameter("ExpiresByType text",
                "access plus 7 minutes");
        filterDef.addInitParameter("ExpiresExcludedResponseStatusCodes",
                "304, 503");

        filterDef.setFilterClass(ExpiresFilter.class.getName());
        filterDef.setFilterName(ExpiresFilter.class.getName());

        root.addFilterDef(filterDef);

        FilterMap filterMap = new FilterMap();
        filterMap.setFilterName(ExpiresFilter.class.getName());
        filterMap.addURLPattern("*");
        root.addFilterMap(filterMap);

        Tomcat.addServlet(root, servlet.getClass().getName(), servlet);
        root.addServletMapping("/test", servlet.getClass().getName());

        tomcat.start();

        try {
            Calendar.getInstance(TimeZone.getTimeZone("GMT"));
            long timeBeforeInMillis = System.currentTimeMillis();

            // TEST
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(
                    "http://localhost:" + tomcat.getConnector().getLocalPort() +
                            "/test").openConnection();

            // VALIDATE
            Assert.assertEquals(expectedResponseStatusCode,
                    httpURLConnection.getResponseCode());

            StringBuilder msg = new StringBuilder();
            for (Entry<String, List<String>> field : httpURLConnection.getHeaderFields().entrySet()) {
                for (String value : field.getValue()) {
                    msg.append((field.getKey() == null ? "" : field.getKey() +
                            ": ") +
                            value + "\n");
                }
            }
            System.out.println(msg);

            Integer actualMaxAgeInSeconds;

            String cacheControlHeader = httpURLConnection.getHeaderField("Cache-Control");
            if (cacheControlHeader == null) {
                actualMaxAgeInSeconds = null;
            } else {
                actualMaxAgeInSeconds = null;
                StringTokenizer cacheControlTokenizer = new StringTokenizer(
                        cacheControlHeader, ",");
                while (cacheControlTokenizer.hasMoreTokens() &&
                        actualMaxAgeInSeconds == null) {
                    String cacheDirective = cacheControlTokenizer.nextToken();
                    StringTokenizer cacheDirectiveTokenizer = new StringTokenizer(
                            cacheDirective, "=");
                    if (cacheDirectiveTokenizer.countTokens() == 2) {
                        String key = cacheDirectiveTokenizer.nextToken().trim();
                        String value = cacheDirectiveTokenizer.nextToken().trim();
                        if (key.equalsIgnoreCase("max-age")) {
                            actualMaxAgeInSeconds = Integer.valueOf(value);
                        }
                    }
                }
            }

            if (expectedMaxAgeInSeconds == null) {
                Assert.assertNull("actualMaxAgeInSeconds '" +
                        actualMaxAgeInSeconds + "' should be null",
                        actualMaxAgeInSeconds);
                return;
            }

            Assert.assertNotNull(actualMaxAgeInSeconds);

            int deltaInSeconds = Math.abs(actualMaxAgeInSeconds.intValue() -
                    expectedMaxAgeInSeconds.intValue());
            Assert.assertTrue("actualMaxAgeInSeconds: " +
                    actualMaxAgeInSeconds + ", expectedMaxAgeInSeconds: " +
                    expectedMaxAgeInSeconds + ", request time: " +
                    timeBeforeInMillis + " for content type " +
                    httpURLConnection.getContentType(), deltaInSeconds < 3);

        } finally {
            tomcat.stop();
        }
    }

    @Test
    public void testIntsToCommaDelimitedString() {
        String actual = ExpiresFilter.intsToCommaDelimitedString(new int[] {
                500, 503 });
        String expected = "500, 503";

        Assert.assertEquals(expected, actual);
    }
}
