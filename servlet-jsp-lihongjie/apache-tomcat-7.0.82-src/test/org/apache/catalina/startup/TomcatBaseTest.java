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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import org.apache.catalina.Container;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.Manager;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.session.ManagerBase;
import org.apache.catalina.session.StandardManager;
import org.apache.catalina.valves.AccessLogValve;
import org.apache.tomcat.util.buf.ByteChunk;

/**
 * Base test case that provides a Tomcat instance for each test - mainly so we
 * don't have to keep writing the cleanup code.
 */
public abstract class TomcatBaseTest extends LoggingBaseTest {
    private Tomcat tomcat;
    private boolean accessLogEnabled = false;

    public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");

    /**
     * Make Tomcat instance accessible to sub-classes.
     */
    public Tomcat getTomcatInstance() {
        return tomcat;
    }

    /**
     * Sub-classes need to know port so they can connect
     */
    public int getPort() {
        return tomcat.getConnector().getLocalPort();
    }

    /**
     * Sub-classes may want to check, whether an AccessLogValve is active
     */
    public boolean isAccessLogEnabled() {
        return accessLogEnabled;
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();

        // Trigger loading of catalina.properties
        CatalinaProperties.getProperty("foo");

        File appBase = new File(getTemporaryDirectory(), "webapps");
        if (!appBase.exists() && !appBase.mkdir()) {
            fail("Unable to create appBase for test");
        }

        tomcat = new TomcatWithFastSessionIDs();

        String protocol = getProtocol();
        Connector connector = new Connector(protocol);
        // Listen only on localhost
        connector.setAttribute("address",
                InetAddress.getByName("localhost").getHostAddress());
        // Use random free port
        connector.setPort(0);
        // Mainly set to reduce timeouts during async tests
        connector.setAttribute("connectionTimeout", "3000");
        tomcat.getService().addConnector(connector);
        tomcat.setConnector(connector);

        // Add AprLifecycleListener if we are using the Apr connector
        if (protocol.contains("Apr")) {
            StandardServer server = (StandardServer) tomcat.getServer();
            AprLifecycleListener listener = new AprLifecycleListener();
            listener.setSSLRandomSeed("/dev/urandom");
            server.addLifecycleListener(listener);
            connector.setAttribute("pollerThreadCount", Integer.valueOf(1));
        }

        File catalinaBase = getTemporaryDirectory();
        tomcat.setBaseDir(catalinaBase.getAbsolutePath());
        tomcat.getHost().setAppBase(appBase.getAbsolutePath());

        accessLogEnabled = Boolean.parseBoolean(
            System.getProperty("tomcat.test.accesslog", "false"));
        if (accessLogEnabled) {
            String accessLogDirectory = System
                    .getProperty("tomcat.test.reports");
            if (accessLogDirectory == null) {
                accessLogDirectory = new File(getBuildDirectory(), "logs")
                        .toString();
            }
            AccessLogValve alv = new AccessLogValve();
            alv.setDirectory(accessLogDirectory);
            alv.setPattern("%h %l %u %t \"%r\" %s %b %I %D");
            tomcat.getHost().getPipeline().addValve(alv);
        }

        // Cannot delete the whole tempDir, because logs are there,
        // but delete known subdirectories of it.
        addDeleteOnTearDown(new File(catalinaBase, "webapps"));
        addDeleteOnTearDown(new File(catalinaBase, "work"));
    }

    protected String getProtocol() {
        // Has a protocol been specified
        String protocol = System.getProperty("tomcat.test.protocol");

        // Use BIO by default
        if (protocol == null) {
            protocol = "org.apache.coyote.http11.Http11Protocol";
        }

        return protocol;
    }

    @After
    @Override
    public void tearDown() throws Exception {
        try {
            // Some tests may call tomcat.destroy(), some tests may just call
            // tomcat.stop(), some not call either method. Make sure that stop()
            // & destroy() are called as necessary.
            if (tomcat.server != null
                    && tomcat.server.getState() != LifecycleState.DESTROYED) {
                if (tomcat.server.getState() != LifecycleState.STOPPED) {
                    tomcat.stop();
                }
                tomcat.destroy();
            }
        } finally {
            super.tearDown();
        }
    }

    /**
     * Simple Hello World servlet for use by test cases
     */
    public static final class HelloWorldServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        public static final String RESPONSE_TEXT =
            "<html><body><p>Hello World</p></body></html>";

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            PrintWriter out = resp.getWriter();
            out.print(RESPONSE_TEXT);
        }
    }


    public static final class RequestDescriptor {

        private final Map<String, String> requestInfo =
            new HashMap<String, String>();
        private final Map<String, String> contextInitParameters =
            new HashMap<String, String>();
        private final Map<String, String> contextAttributes =
            new HashMap<String, String>();
        private final Map<String, String> headers =
            new CaseInsensitiveKeyMap<String>();
        private final Map<String, String> attributes =
            new HashMap<String, String>();
        private final Map<String, String> params =
            new HashMap<String, String>();
        private final Map<String, String> sessionAttributes =
            new HashMap<String, String>();

        public Map<String, String> getRequestInfo() {
            return requestInfo;
        }

        public Map<String, String> getContextInitParameters() {
            return contextInitParameters;
        }

        public Map<String, String> getContextAttributes() {
            return contextAttributes;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public Map<String, String> getAttributes() {
            return attributes;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public Map<String, String> getSessionAttributes() {
            return sessionAttributes;
        }

        public String getRequestInfo(String name) {
            return requestInfo.get(name);
        }

        public void putRequestInfo(String name, String value) {
            requestInfo.put(name, value);
        }

        public String getContextInitParameter(String name) {
            return contextInitParameters.get(name);
        }

        public void putContextInitParameter(String name, String value) {
            contextInitParameters.put(name, value);
        }

        public String getContextAttribute(String name) {
            return contextAttributes.get(name);
        }

        public void putContextAttribute(String name, String value) {
            contextAttributes.put(name, value);
        }

        public String getHeader(String name) {
            return headers.get(name);
        }

        public void putHeader(String name, String value) {
            headers.put(name, value);
        }

        public String getAttribute(String name) {
            return attributes.get(name);
        }

        public void putAttribute(String name, String value) {
            attributes.put(name, value);
        }

        public String getParam(String name) {
            return params.get(name);
        }

        public void putParam(String name, String value) {
            params.put(name, value);
        }

        public String getSessionAttribute(String name) {
            return sessionAttributes.get(name);
        }

        public void putSessionAttribute(String name, String value) {
            sessionAttributes.put(name, value);
        }

        public void compare (RequestDescriptor request) {
            Map<String, String> base;
            Map<String, String> cmp;
            base = request.getRequestInfo();
            cmp = this.getRequestInfo();
            for (String name: base.keySet()) {
                Assert.assertEquals("Request info " + name, base.get(name), cmp.get(name));
            }
            base = request.getContextInitParameters();
            cmp = this.getContextInitParameters();
            for (String name: base.keySet()) {
                Assert.assertEquals("Context parameter " + name, base.get(name), cmp.get(name));
            }
            base = request.getContextAttributes();
            cmp = this.getContextAttributes();
            for (String name: base.keySet()) {
                Assert.assertEquals("Context attribute " + name, base.get(name), cmp.get(name));
            }
            base = request.getHeaders();
            cmp = this.getHeaders();
            for (String name: base.keySet()) {
                Assert.assertEquals("Header " + name, base.get(name), cmp.get(name));
            }
            base = request.getAttributes();
            cmp = this.getAttributes();
            for (String name: base.keySet()) {
                Assert.assertEquals("Attribute " + name, base.get(name), cmp.get(name));
            }
            base = request.getParams();
            cmp = this.getParams();
            for (String name: base.keySet()) {
                Assert.assertEquals("Param " + name, base.get(name), cmp.get(name));
            }
            base = request.getSessionAttributes();
            cmp = this.getSessionAttributes();
            for (String name: base.keySet()) {
                Assert.assertEquals("Session attribute " + name, base.get(name), cmp.get(name));
            }
        }
    }


    public static final class SnoopResult {

        public static RequestDescriptor parse(String body) {

            int n;
            int m;
            String key;
            String value;
            String name;

            RequestDescriptor request = new RequestDescriptor();
            String lineSeparator = System.getProperty("line.separator");

            for (String line: body.split(lineSeparator)) {
                n = line.indexOf(": ");
                if (n > 0) {
                    key = line.substring(0, n);
                    value = line.substring(n + 2);
                    m = key.indexOf(':');
                    if (m > 0) {
                        name = key.substring(m + 1);
                        key = key.substring(0, m);
                        if (key.equals("CONTEXT-PARAM")) {
                            request.putContextInitParameter(name, value);
                        } else if (key.equals("CONTEXT-ATTRIBUTE")) {
                            request.putContextAttribute(name, value);
                        } else if (key.equals("HEADER")) {
                            request.putHeader(name, value);
                        } else if (key.equals("ATTRIBUTE")) {
                            request.putAttribute(name, value);
                        } else if (key.equals("PARAM")) {
                            request.putParam(name, value);
                        } else if (key.equals("SESSION-ATTRIBUTE")) {
                            request.putSessionAttribute(name, value);
                        } else {
                            request.putRequestInfo(key + ":" + name, value);
                        }
                    } else {
                        request.putRequestInfo(key, value);
                    }
                }
            }

            return request;
        }
    }

    /**
     * Simple servlet that dumps request information. Tests using this should
     * note that additional information may be added to in the future and should
     * therefore test return values using SnoopResult.
     */
    public static final class SnoopServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @SuppressWarnings("deprecation")
        @Override
        public void service(HttpServletRequest request,
                            HttpServletResponse response)
                throws ServletException, IOException {

            String name;
            StringBuilder value;
            Object attribute;

            ServletContext ctx = this.getServletContext();
            HttpSession session = request.getSession(false);
            PrintWriter out = response.getWriter();

            response.setContentType("text/plain");

            out.println("CONTEXT-NAME: " + ctx.getServletContextName());
            out.println("CONTEXT-PATH: " + ctx.getContextPath());
            out.println("CONTEXT-MAJOR-VERSION: " + ctx.getMajorVersion());
            out.println("CONTEXT-MINOR-VERSION: " + ctx.getMinorVersion());
            out.println("CONTEXT-SERVER-INFO: " + ctx.getServerInfo());
            for (Enumeration<String> e = ctx.getInitParameterNames();
                 e.hasMoreElements();) {
                name = e.nextElement();
                out.println("CONTEXT-INIT-PARAM:" + name + ": " +
                            ctx.getInitParameter(name));
            }
            for (Enumeration<String> e = ctx.getAttributeNames();
                 e.hasMoreElements();) {
                name = e.nextElement();
                out.println("CONTEXT-ATTRIBUTE:" + name + ": " +
                            ctx.getAttribute(name));
            }
            out.println("REQUEST-CONTEXT-PATH: " + request.getContextPath());
            out.println("REQUEST-SERVER-NAME: " + request.getServerName());
            out.println("REQUEST-SERVER-PORT: " + request.getServerPort());
            out.println("REQUEST-LOCAL-NAME: " + request.getLocalName());
            out.println("REQUEST-LOCAL-ADDR: " + request.getLocalAddr());
            out.println("REQUEST-LOCAL-PORT: " + request.getLocalPort());
            out.println("REQUEST-REMOTE-HOST: " + request.getRemoteHost());
            out.println("REQUEST-REMOTE-ADDR: " + request.getRemoteAddr());
            out.println("REQUEST-REMOTE-PORT: " + request.getRemotePort());
            out.println("REQUEST-PROTOCOL: " + request.getProtocol());
            out.println("REQUEST-SCHEME: " + request.getScheme());
            out.println("REQUEST-IS-SECURE: " + request.isSecure());
            out.println("REQUEST-URI: " + request.getRequestURI());
            out.println("REQUEST-URL: " + request.getRequestURL());
            out.println("REQUEST-SERVLET-PATH: " + request.getServletPath());
            out.println("REQUEST-METHOD: " + request.getMethod());
            out.println("REQUEST-PATH-INFO: " + request.getPathInfo());
            out.println("REQUEST-PATH-TRANSLATED: " +
                        request.getPathTranslated());
            out.println("REQUEST-QUERY-STRING: " + request.getQueryString());
            out.println("REQUEST-REMOTE-USER: " + request.getRemoteUser());
            out.println("REQUEST-AUTH-TYPE: " + request.getAuthType());
            out.println("REQUEST-USER-PRINCIPAL: " +
                        request.getUserPrincipal());
            out.println("REQUEST-CHARACTER-ENCODING: " +
                        request.getCharacterEncoding());
            out.println("REQUEST-CONTENT-LENGTH: " +
                        request.getContentLength());
            out.println("REQUEST-CONTENT-TYPE: " + request.getContentType());
            out.println("REQUEST-LOCALE: " + request.getLocale());

            for (Enumeration<String> e = request.getHeaderNames();
                 e.hasMoreElements();) {
                name = e.nextElement();
                value = new StringBuilder();
                for (Enumeration<String> h = request.getHeaders(name);
                     h.hasMoreElements();) {
                    value.append(h.nextElement());
                    if (h.hasMoreElements()) {
                        value.append(";");
                    }
                }
                out.println("HEADER:" + name + ": " + value);
            }

            for (Enumeration<String> e = request.getAttributeNames();
                 e.hasMoreElements();) {
                name = e.nextElement();
                attribute = request.getAttribute(name);
                out.println("ATTRIBUTE:" + name + ": " +
                            (attribute != null ? attribute : "(null)"));
            }

            for (Enumeration<String> e = request.getParameterNames();
                 e.hasMoreElements();) {
                name = e.nextElement();
                value = new StringBuilder();
                String values[] = request.getParameterValues(name);
                int m = values.length;
                for (int j = 0; j < m; j++) {
                    value.append(values[j]);
                    if (j < m - 1) {
                        value.append(";");
                    }
                }
                out.println("PARAM/" + name + ": " + value);
            }

            out.println("SESSION-REQUESTED-ID: " +
                        request.getRequestedSessionId());
            out.println("SESSION-REQUESTED-ID-COOKIE: " +
                        request.isRequestedSessionIdFromCookie());
            out.println("SESSION-REQUESTED-ID-URL: " +
                        request.isRequestedSessionIdFromUrl());
            out.println("SESSION-REQUESTED-ID-VALID: " +
                        request.isRequestedSessionIdValid());

            if (session != null) {
                out.println("SESSION-ID: " + session.getId());
                out.println("SESSION-CREATION-TIME: " +
                        session.getCreationTime());
                out.println("SESSION-LAST-ACCESSED-TIME: " +
                        session.getLastAccessedTime());
                out.println("SESSION-MAX-INACTIVE-INTERVAL: " +
                        session.getMaxInactiveInterval());
                out.println("SESSION-IS-NEW: " + session.isNew());
                for (Enumeration<String> e = session.getAttributeNames();
                     e.hasMoreElements();) {
                    name = e.nextElement();
                    attribute = session.getAttribute(name);
                    out.println("SESSION-ATTRIBUTE:" + name + ": " +
                                (attribute != null ? attribute : "(null)"));
                }
            }

            int bodySize = 0;
            if ("PUT".equalsIgnoreCase(request.getMethod())) {
                InputStream is = request.getInputStream();
                int read = 0;
                byte[] buffer = new byte[8192];
                while (read != -1) {
                    read = is.read(buffer);
                    if (read > -1) {
                        bodySize += read;
                    }
                }
            }
            out.println("REQUEST-BODY-SIZE: " + bodySize);
        }
    }


    /**
     * Servlet that simply echos the request body back as the response body.
     */
    public static class EchoBodyServlet extends HttpServlet {

        private static final long serialVersionUID = 1L;

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            // NO-OP - No body to echo
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                throws ServletException, IOException {
            // Beware of clients that try to send the whole request body before
            // reading any of the response. They may cause this test to lock up.
            byte[] buffer = new byte[8096];
            int read = 0;
            InputStream is = null;
            OutputStream os = null;
            try {
                is = req.getInputStream();
                os = resp.getOutputStream();
                while (read > -1) {
                    os.write(buffer, 0, read);
                    read = is.read(buffer);
                }
            } catch (IOException ex) {
                // Ignore
            } finally {
                if (os != null) {
                    try {
                        os.close();
                    } catch (IOException e) {
                        // Ignore
                    }
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        // Ignore
                    }
                }
            }
        }
    }


    /*
     *  Wrapper for getting the response.
     */
    public static ByteChunk getUrl(String path) throws IOException {
        ByteChunk out = new ByteChunk();
        getUrl(path, out, null);
        return out;
    }

    public static int getUrl(String path, ByteChunk out,
            Map<String, List<String>> resHead) throws IOException {
        return getUrl(path, out, null, resHead);
    }

    public static int headUrl(String path, ByteChunk out,
            Map<String, List<String>> resHead) throws IOException {
        return methodUrl(path, out, 1000000, null, resHead, "HEAD");
    }

    public static int getUrl(String path, ByteChunk out,
            Map<String, List<String>> reqHead,
            Map<String, List<String>> resHead) throws IOException {
        return getUrl(path, out, 1000000, reqHead, resHead);
    }

    public static int getUrl(String path, ByteChunk out, int readTimeout,
            Map<String, List<String>> reqHead,
            Map<String, List<String>> resHead) throws IOException {
        return methodUrl(path, out, readTimeout, reqHead, resHead, "GET");
    }

    public static int methodUrl(String path, ByteChunk out, int readTimeout,
            Map<String, List<String>> reqHead,
            Map<String, List<String>> resHead,
            String method) throws IOException {

        URL url = new URL(path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setUseCaches(false);
        connection.setReadTimeout(readTimeout);
        connection.setRequestMethod(method);
        if (reqHead != null) {
            for (Map.Entry<String, List<String>> entry : reqHead.entrySet()) {
                StringBuilder valueList = new StringBuilder();
                for (String value : entry.getValue()) {
                    if (valueList.length() > 0) {
                        valueList.append(',');
                    }
                    valueList.append(value);
                }
                connection.setRequestProperty(entry.getKey(),
                        valueList.toString());
            }
        }
        connection.connect();
        int rc = connection.getResponseCode();
        if (resHead != null) {
            Map<String, List<String>> head = connection.getHeaderFields();
            resHead.putAll(head);
        }
        InputStream is;
        if (rc < 400) {
            is = connection.getInputStream();
        } else {
            is = connection.getErrorStream();
        }
        if (is != null) {
            BufferedInputStream bis = null;
            try {
                bis = new BufferedInputStream(is);
                byte[] buf = new byte[2048];
                int rd = 0;
                while((rd = bis.read(buf)) > 0) {
                    out.append(buf, 0, rd);
                }
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        // Ignore
                    }
                }
            }
        }
        return rc;
    }

    public static ByteChunk postUrl(byte[] body, String path)
            throws IOException {
        ByteChunk out = new ByteChunk();
        postUrl(body, path, out, null);
        return out;
    }

    public static int postUrl(byte[] body, String path, ByteChunk out,
            Map<String, List<String>> resHead) throws IOException {
        return postUrl(body, path, out, null, resHead);
    }

    public static int postUrl(byte[] body, String path, ByteChunk out,
            Map<String, List<String>> reqHead,
            Map<String, List<String>> resHead) throws IOException {

        URL url = new URL(path);
        HttpURLConnection connection =
            (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setReadTimeout(1000000);
        if (reqHead != null) {
            for (Map.Entry<String, List<String>> entry : reqHead.entrySet()) {
                StringBuilder valueList = new StringBuilder();
                for (String value : entry.getValue()) {
                    if (valueList.length() > 0) {
                        valueList.append(',');
                    }
                    valueList.append(value);
                }
                connection.setRequestProperty(entry.getKey(),
                        valueList.toString());
            }
        }
        connection.connect();

        // Write the request body
        OutputStream os = null;
        try {
            os = connection.getOutputStream();
            if (body != null) {
                os.write(body, 0, body.length);
            }
        } catch (IOException ioe) {
            // Failed to write the request body. Server may have closed the
            // connection.
            ioe.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException ioe) {
                    // Ignore
                }
            }
        }

        int rc = connection.getResponseCode();
        if (resHead != null) {
            Map<String, List<String>> head = connection.getHeaderFields();
            resHead.putAll(head);
        }
        if (rc == HttpServletResponse.SC_OK) {
            InputStream is = connection.getInputStream();
            BufferedInputStream bis = null;
            try {
                bis = new BufferedInputStream(is);
                byte[] buf = new byte[2048];
                int rd = 0;
                while((rd = bis.read(buf)) > 0) {
                    out.append(buf, 0, rd);
                }
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        // Ignore
                    }
                }
            }
        }
        return rc;
    }

    private static class TomcatWithFastSessionIDs extends Tomcat {

        @Override
        public void start() throws LifecycleException {
            // Use fast, insecure session ID generation for all tests
            Server server = getServer();
            for (Service service : server.findServices()) {
                Container e = service.getContainer();
                for (Container h : e.findChildren()) {
                    for (Container c : h.findChildren()) {
                        Manager m = c.getManager();
                        if (m == null) {
                            m = new StandardManager();
                            c.setManager(m);
                        }
                        if (m instanceof ManagerBase) {
                            ((ManagerBase) m).setSecureRandomClass(
                                    "org.apache.catalina.startup.FastNonSecureRandom");
                        }
                    }
                }
            }
            super.start();
        }
    }
}
