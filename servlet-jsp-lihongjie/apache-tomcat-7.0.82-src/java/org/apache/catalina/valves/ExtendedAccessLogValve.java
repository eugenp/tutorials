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
package org.apache.catalina.valves;


import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.util.ServerInfo;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.ExceptionUtils;



/**
 * An implementation of the W3c Extended Log File Format. See
 * http://www.w3.org/TR/WD-logfile.html for more information about the format.
 *
 * The following fields are supported:
 * <ul>
 * <li><code>c-dns</code>:  Client hostname (or ip address if
 * <code>enableLookups</code> for the connector is false)</li>
 * <li><code>c-ip</code>:  Client ip address</li>
 * <li><code>bytes</code>:  bytes served</li>
 * <li><code>cs-method</code>:  request method</li>
 * <li><code>cs-uri</code>:  The full uri requested</li>
 * <li><code>cs-uri-query</code>:  The query string</li>
 * <li><code>cs-uri-stem</code>:  The uri without query string</li>
 * <li><code>date</code>:  The date in yyyy-mm-dd  format for GMT</li>
 * <li><code>s-dns</code>: The server dns entry </li>
 * <li><code>s-ip</code>:  The server ip address</li>
 * <li><code>cs(XXX)</code>:  The value of header XXX from client to server</li>
 * <li><code>sc(XXX)</code>: The value of header XXX from server to client </li>
 * <li><code>sc-status</code>:  The status code</li>
 * <li><code>time</code>:  Time the request was served</li>
 * <li><code>time-taken</code>:  Time (in seconds) taken to serve the request</li>
 * <li><code>x-threadname</code>: Current request thread name (can compare later with stacktraces)</li>
 * <li><code>x-A(XXX)</code>: Pull XXX attribute from the servlet context </li>
 * <li><code>x-C(XXX)</code>: Pull the first cookie of the name XXX </li>
 * <li><code>x-O(XXX)</code>: Pull the all response header values XXX </li>
 * <li><code>x-R(XXX)</code>: Pull XXX attribute from the servlet request </li>
 * <li><code>x-S(XXX)</code>: Pull XXX attribute from the session </li>
 * <li><code>x-P(...)</code>:  Call request.getParameter(...)
 *                             and URLencode it. Helpful to capture
 *                             certain POST parameters.
 * </li>
 * <li>For any of the x-H(...) the following method will be called from the
 *                HttpServletRequest object </li>
 * <li><code>x-H(authType)</code>: getAuthType </li>
 * <li><code>x-H(characterEncoding)</code>: getCharacterEncoding </li>
 * <li><code>x-H(contentLength)</code>: getContentLength </li>
 * <li><code>x-H(locale)</code>:  getLocale</li>
 * <li><code>x-H(protocol)</code>: getProtocol </li>
 * <li><code>x-H(remoteUser)</code>:  getRemoteUser</li>
 * <li><code>x-H(requestedSessionId)</code>: getRequestedSessionId</li>
 * <li><code>x-H(requestedSessionIdFromCookie)</code>:
 *                  isRequestedSessionIdFromCookie </li>
 * <li><code>x-H(requestedSessionIdValid)</code>:
 *                  isRequestedSessionIdValid</li>
 * <li><code>x-H(scheme)</code>:  getScheme</li>
 * <li><code>x-H(secure)</code>:  isSecure</li>
 * </ul>
 *
 *
 *
 * <p>
 * Log rotation can be on or off. This is dictated by the
 * <code>rotatable</code> property.
 * </p>
 *
 * <p>
 * For UNIX users, another field called <code>checkExists</code> is also
 * available. If set to true, the log file's existence will be checked before
 * each logging. This way an external log rotator can move the file
 * somewhere and Tomcat will start with a new file.
 * </p>
 *
 * <p>
 * For JMX junkies, a public method called <code>rotate</code> has
 * been made available to allow you to tell this instance to move
 * the existing log file to somewhere else and start writing a new log file.
 * </p>
 *
 * <p>
 * Conditional logging is also supported. This can be done with the
 * <code>condition</code> property.
 * If the value returned from ServletRequest.getAttribute(condition)
 * yields a non-null value, the logging will be skipped.
 * </p>
 *
 * <p>
 * For extended attributes coming from a getAttribute() call,
 * it is you responsibility to ensure there are no newline or
 * control characters.
 * </p>
 *
 *
 * @author Tim Funk
 * @author Peter Rossbach
 */
public class ExtendedAccessLogValve extends AccessLogValve {

    private static final Log log = LogFactory.getLog(ExtendedAccessLogValve.class);

    // ----------------------------------------------------- Instance Variables


    /**
     * The descriptive information about this implementation.
     */
    protected static final String extendedAccessLogInfo =
        "org.apache.catalina.valves.ExtendedAccessLogValve/2.1";


    // ------------------------------------------------------------- Properties


    /**
     * Return descriptive information about this implementation.
     */
    @Override
    public String getInfo() {
        return (extendedAccessLogInfo);
    }


    // --------------------------------------------------------- Public Methods


    // -------------------------------------------------------- Private Methods

    /**
     * Wrap the incoming value with double quotes (") and escape any double
     * quotes appearing in the value using two double quotes ("").
     *
     *  @param value - The value to wrap
     *  @return '-' if null. Otherwise, toString() will
     *     be called on the object and the value will be wrapped
     *     in quotes and any quotes will be escaped with 2
     *     sets of quotes.
     */
    static String wrap(Object value) {
        String svalue;
        // Does the value contain a " ? If so must encode it
        if (value == null || "-".equals(value)) {
            return "-";
        }

        try {
            svalue = value.toString();
        } catch (Throwable e) {
            ExceptionUtils.handleThrowable(e);
            /* Log error */
            return "-";
        }

        /* Wrap all values in double quotes. */
        StringBuilder buffer = new StringBuilder(svalue.length() + 2);
        buffer.append('\"');
        int i = 0;
        while (i < svalue.length()) {
            int j = svalue.indexOf('\"', i);
            if (j == -1) {
                buffer.append(svalue.substring(i));
                i = svalue.length();
            } else {
                buffer.append(svalue.substring(i, j + 1));
                buffer.append('"');
                i = j + 1;
            }
        }

        buffer.append('\"');
        return buffer.toString();
    }

    /**
     * Open the new log file for the date specified by <code>dateStamp</code>.
     */
    @Override
    protected synchronized void open() {
        super.open();
        if (currentLogFile.length()==0) {
            writer.println("#Fields: " + pattern);
            writer.println("#Version: 2.0");
            writer.println("#Software: " + ServerInfo.getServerInfo());
        }
    }


    // ------------------------------------------------------ Lifecycle Methods


    protected static class DateElement implements AccessLogElement {
        // Milli-seconds in 24 hours
        private static final long INTERVAL = (1000 * 60 * 60 * 24);

        private static final ThreadLocal<ElementTimestampStruct> currentDate =
                new ThreadLocal<ElementTimestampStruct>() {
            @Override
            protected ElementTimestampStruct initialValue() {
                return new ElementTimestampStruct("yyyy-MM-dd");
            }
        };

        @Override
        public void addElement(StringBuilder buf, Date date, Request request,
                Response response, long time) {
            ElementTimestampStruct eds = currentDate.get();
            long millis = eds.currentTimestamp.getTime();
            if (date.getTime() > (millis + INTERVAL -1) ||
                    date.getTime() < millis) {
                eds.currentTimestamp.setTime(
                        date.getTime() - (date.getTime() % INTERVAL));
                eds.currentTimestampString =
                    eds.currentTimestampFormat.format(eds.currentTimestamp);
            }
            buf.append(eds.currentTimestampString);
        }
    }

    protected static class TimeElement implements AccessLogElement {
        // Milli-seconds in a second
        private static final long INTERVAL = 1000;

        private static final ThreadLocal<ElementTimestampStruct> currentTime =
                new ThreadLocal<ElementTimestampStruct>() {
            @Override
            protected ElementTimestampStruct initialValue() {
                return new ElementTimestampStruct("HH:mm:ss");
            }
        };

        @Override
        public void addElement(StringBuilder buf, Date date, Request request,
                Response response, long time) {
            ElementTimestampStruct eds = currentTime.get();
            long millis = eds.currentTimestamp.getTime();
            if (date.getTime() > (millis + INTERVAL -1) ||
                    date.getTime() < millis) {
                eds.currentTimestamp.setTime(
                        date.getTime() - (date.getTime() % INTERVAL));
                eds.currentTimestampString =
                    eds.currentTimestampFormat.format(eds.currentTimestamp);
            }
            buf.append(eds.currentTimestampString);
        }
    }

    protected static class RequestHeaderElement implements AccessLogElement {
        private final String header;

        public RequestHeaderElement(String header) {
            this.header = header;
        }
        @Override
        public void addElement(StringBuilder buf, Date date, Request request,
                Response response, long time) {
            buf.append(wrap(request.getHeader(header)));
        }
    }

    protected static class ResponseHeaderElement implements AccessLogElement {
        private final String header;

        public ResponseHeaderElement(String header) {
            this.header = header;
        }

        @Override
        public void addElement(StringBuilder buf, Date date, Request request,
                Response response, long time) {
            buf.append(wrap(response.getHeader(header)));
        }
    }

    protected static class ServletContextElement implements AccessLogElement {
        private final String attribute;

        public ServletContextElement(String attribute) {
            this.attribute = attribute;
        }
        @Override
        public void addElement(StringBuilder buf, Date date, Request request,
                Response response, long time) {
            buf.append(wrap(request.getContext().getServletContext()
                    .getAttribute(attribute)));
        }
    }

    protected static class CookieElement implements AccessLogElement {
        private final String name;

        public CookieElement(String name) {
            this.name = name;
        }
        @Override
        public void addElement(StringBuilder buf, Date date, Request request,
                Response response, long time) {
            Cookie[] c = request.getCookies();
            for (int i = 0; c != null && i < c.length; i++) {
                if (name.equals(c[i].getName())) {
                    buf.append(wrap(c[i].getValue()));
                }
            }
        }
    }

    /**
     * write a specific response header - x-O(xxx)
     */
    protected static class ResponseAllHeaderElement implements AccessLogElement {
        private final String header;

        public ResponseAllHeaderElement(String header) {
            this.header = header;
        }

        @Override
        public void addElement(StringBuilder buf, Date date, Request request,
                Response response, long time) {
            if (null != response) {
                Iterator<String> iter = response.getHeaders(header).iterator();
                if (iter.hasNext()) {
                    StringBuilder buffer = new StringBuilder();
                    boolean first = true;
                    while (iter.hasNext()) {
                        if (first) {
                            first = false;
                        } else {
                            buffer.append(",");
                        }
                        buffer.append(iter.next());
                    }
                    buf.append(wrap(buffer.toString()));
                }
                return ;
            }
            buf.append("-");
        }
    }

    protected static class RequestAttributeElement implements AccessLogElement {
        private final String attribute;

        public RequestAttributeElement(String attribute) {
            this.attribute = attribute;
        }

        @Override
        public void addElement(StringBuilder buf, Date date, Request request,
                Response response, long time) {
            buf.append(wrap(request.getAttribute(attribute)));
        }
    }

    protected static class SessionAttributeElement implements AccessLogElement {
        private final String attribute;

        public SessionAttributeElement(String attribute) {
            this.attribute = attribute;
        }
        @Override
        public void addElement(StringBuilder buf, Date date, Request request,
                Response response, long time) {
            HttpSession session = null;
            if (request != null) {
                session = request.getSession(false);
                if (session != null) {
                    buf.append(wrap(session.getAttribute(attribute)));
                }
            }
        }
    }

    protected static class RequestParameterElement implements AccessLogElement {
        private final String parameter;

        public RequestParameterElement(String parameter) {
            this.parameter = parameter;
        }
        /**
         *  urlEncode the given string. If null or empty, return null.
         */
        private String urlEncode(String value) {
            if (null==value || value.length()==0) {
                return null;
            }
            try {
                return URLEncoder.encode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                // Should never happen - all JVMs are required to support UTF-8
                return null;
            }
        }

        @Override
        public void addElement(StringBuilder buf, Date date, Request request,
                Response response, long time) {
            buf.append(wrap(urlEncode(request.getParameter(parameter))));
        }
    }

    protected static class PatternTokenizer {
        private StringReader sr = null;
        private StringBuilder buf = new StringBuilder();
        private boolean ended = false;
        private boolean subToken;
        private boolean parameter;

        public PatternTokenizer(String str) {
            sr = new StringReader(str);
        }

        public boolean hasSubToken() {
            return subToken;
        }

        public boolean hasParameter() {
            return parameter;
        }

        public String getToken() throws IOException {
            if(ended) {
                return null ;
            }

            String result = null;
            subToken = false;
            parameter = false;

            int c = sr.read();
            while (c != -1) {
                switch (c) {
                case ' ':
                    result = buf.toString();
                    buf = new StringBuilder();
                    buf.append((char) c);
                    return result;
                case '-':
                    result = buf.toString();
                    buf = new StringBuilder();
                    subToken = true;
                    return result;
                case '(':
                    result = buf.toString();
                    buf = new StringBuilder();
                    parameter = true;
                    return result;
                case ')':
                    result = buf.toString();
                    buf = new StringBuilder();
                    break;
                default:
                    buf.append((char) c);
                }
                c = sr.read();
            }
            ended = true;
            if (buf.length() != 0) {
                return buf.toString();
            } else {
                return null;
            }
        }

        public String getParameter()throws IOException {
            String result;
            if (!parameter) {
                return null;
            }
            parameter = false;
            int c = sr.read();
            while (c != -1) {
                if (c == ')') {
                    result = buf.toString();
                    buf = new StringBuilder();
                    return result;
                }
                buf.append((char) c);
                c = sr.read();
            }
            return null;
        }

        public String getWhiteSpaces() throws IOException {
            if(isEnded()) {
                return "" ;
            }
            StringBuilder whiteSpaces = new StringBuilder();
            if (buf.length() > 0) {
                whiteSpaces.append(buf);
                buf = new StringBuilder();
            }
            int c = sr.read();
            while (Character.isWhitespace((char) c)) {
                whiteSpaces.append((char) c);
                c = sr.read();
            }
            if (c == -1) {
                ended = true;
            } else {
                buf.append((char) c);
            }
            return whiteSpaces.toString();
        }

        public boolean isEnded() {
            return ended;
        }

        public String getRemains() throws IOException {
            StringBuilder remains = new StringBuilder();
            for(int c = sr.read(); c != -1; c = sr.read()) {
                remains.append((char) c);
            }
            return remains.toString();
        }

    }

    @Override
    protected AccessLogElement[] createLogElements() {
        if (log.isDebugEnabled()) {
            log.debug("decodePattern, pattern =" + pattern);
        }
        List<AccessLogElement> list = new ArrayList<AccessLogElement>();

        PatternTokenizer tokenizer = new PatternTokenizer(pattern);
        try {

            // Ignore leading whitespace.
            tokenizer.getWhiteSpaces();

            if (tokenizer.isEnded()) {
                log.info("pattern was just empty or whitespace");
                return null;
            }

            String token = tokenizer.getToken();
            while (token != null) {
                if (log.isDebugEnabled()) {
                    log.debug("token = " + token);
                }
                AccessLogElement element = getLogElement(token, tokenizer);
                if (element == null) {
                    break;
                }
                list.add(element);
                String whiteSpaces = tokenizer.getWhiteSpaces();
                if (whiteSpaces.length() > 0) {
                    list.add(new StringElement(whiteSpaces));
                }
                if (tokenizer.isEnded()) {
                    break;
                }
                token = tokenizer.getToken();
            }
            if (log.isDebugEnabled()) {
                log.debug("finished decoding with element size of: " + list.size());
            }
            return list.toArray(new AccessLogElement[0]);
        } catch (IOException e) {
            log.error("parse error", e);
            return null;
        }
    }

    protected AccessLogElement getLogElement(String token, PatternTokenizer tokenizer) throws IOException {
        if ("date".equals(token)) {
            return new DateElement();
        } else if ("time".equals(token)) {
            if (tokenizer.hasSubToken()) {
                String nextToken = tokenizer.getToken();
                if ("taken".equals(nextToken)) {
                    return new ElapsedTimeElement(false);
                }
            } else {
                return new TimeElement();
            }
        } else if ("bytes".equals(token)) {
            return new ByteSentElement(true);
        } else if ("cached".equals(token)) {
            /* I don't know how to evaluate this! */
            return new StringElement("-");
        } else if ("c".equals(token)) {
            String nextToken = tokenizer.getToken();
            if ("ip".equals(nextToken)) {
                return new RemoteAddrElement();
            } else if ("dns".equals(nextToken)) {
                return new HostElement();
            }
        } else if ("s".equals(token)) {
            String nextToken = tokenizer.getToken();
            if ("ip".equals(nextToken)) {
                return new LocalAddrElement();
            } else if ("dns".equals(nextToken)) {
                return new AccessLogElement() {
                    @Override
                    public void addElement(StringBuilder buf, Date date,
                            Request request, Response response, long time) {
                        String value;
                        try {
                            value = InetAddress.getLocalHost().getHostName();
                        } catch (Throwable e) {
                            ExceptionUtils.handleThrowable(e);
                            value = "localhost";
                        }
                        buf.append(value);
                    }
                };
            }
        } else if ("cs".equals(token)) {
            return getClientToServerElement(tokenizer);
        } else if ("sc".equals(token)) {
            return getServerToClientElement(tokenizer);
        } else if ("sr".equals(token) || "rs".equals(token)) {
            return getProxyElement(tokenizer);
        } else if ("x".equals(token)) {
            return getXParameterElement(tokenizer);
        }
        log.error("unable to decode with rest of chars starting: " + token);
        return null;
    }

    protected AccessLogElement getClientToServerElement(
            PatternTokenizer tokenizer) throws IOException {
        if (tokenizer.hasSubToken()) {
            String token = tokenizer.getToken();
            if ("method".equals(token)) {
                return new MethodElement();
            } else if ("uri".equals(token)) {
                if (tokenizer.hasSubToken()) {
                    token = tokenizer.getToken();
                    if ("stem".equals(token)) {
                        return new RequestURIElement();
                    } else if ("query".equals(token)) {
                        return new AccessLogElement() {
                            @Override
                            public void addElement(StringBuilder buf, Date date,
                                    Request request, Response response,
                                    long time) {
                                String query = request.getQueryString();
                                if (query != null) {
                                    buf.append(query);
                                } else {
                                    buf.append('-');
                                }
                            }
                        };
                    }
                } else {
                    return new AccessLogElement() {
                        @Override
                        public void addElement(StringBuilder buf, Date date,
                                Request request, Response response, long time) {
                            String query = request.getQueryString();
                            if (query == null) {
                                buf.append(request.getRequestURI());
                            } else {
                                buf.append(request.getRequestURI());
                                buf.append('?');
                                buf.append(request.getQueryString());
                            }
                        }
                    };
                }
            }
        } else if (tokenizer.hasParameter()) {
            String parameter = tokenizer.getParameter();
            if (parameter == null) {
                log.error("No closing ) found for in decode");
                return null;
            }
            return new RequestHeaderElement(parameter);
        }
        log.error("The next characters couldn't be decoded: "
                + tokenizer.getRemains());
        return null;
    }

    protected AccessLogElement getServerToClientElement(
            PatternTokenizer tokenizer) throws IOException {
        if (tokenizer.hasSubToken()) {
            String token = tokenizer.getToken();
            if ("status".equals(token)) {
                return new HttpStatusCodeElement();
            } else if ("comment".equals(token)) {
                return new StringElement("?");
            }
        } else if (tokenizer.hasParameter()) {
            String parameter = tokenizer.getParameter();
            if (parameter == null) {
                log.error("No closing ) found for in decode");
                return null;
            }
            return new ResponseHeaderElement(parameter);
        }
        log.error("The next characters couldn't be decoded: "
                + tokenizer.getRemains());
        return null;
    }

    protected AccessLogElement getProxyElement(PatternTokenizer tokenizer)
        throws IOException {
        String token = null;
        if (tokenizer.hasSubToken()) {
            tokenizer.getToken();
            return new StringElement("-");
        } else if (tokenizer.hasParameter()) {
            tokenizer.getParameter();
            return new StringElement("-");
        }
        log.error("The next characters couldn't be decoded: " + token);
        return null;
    }

    protected AccessLogElement getXParameterElement(PatternTokenizer tokenizer)
            throws IOException {
        if (!tokenizer.hasSubToken()) {
            log.error("x param in wrong format. Needs to be 'x-#(...)' read the docs!");
            return null;
        }
        String token = tokenizer.getToken();
        if ("threadname".equals(token)) {
            return new ThreadNameElement();
        }

        if (!tokenizer.hasParameter()) {
            log.error("x param in wrong format. Needs to be 'x-#(...)' read the docs!");
            return null;
        }
        String parameter = tokenizer.getParameter();
        if (parameter == null) {
            log.error("No closing ) found for in decode");
            return null;
        }
        if ("A".equals(token)) {
            return new ServletContextElement(parameter);
        } else if ("C".equals(token)) {
            return new CookieElement(parameter);
        } else if ("R".equals(token)) {
            return new RequestAttributeElement(parameter);
        } else if ("S".equals(token)) {
            return new SessionAttributeElement(parameter);
        } else if ("H".equals(token)) {
            return getServletRequestElement(parameter);
        } else if ("P".equals(token)) {
            return new RequestParameterElement(parameter);
        } else if ("O".equals(token)) {
            return new ResponseAllHeaderElement(parameter);
        }
        log.error("x param for servlet request, couldn't decode value: "
                + token);
        return null;
    }

    protected AccessLogElement getServletRequestElement(String parameter) {
        if ("authType".equals(parameter)) {
            return new AccessLogElement() {
                @Override
                public void addElement(StringBuilder buf, Date date,
                        Request request, Response response, long time) {
                    buf.append(wrap(request.getAuthType()));
                }
            };
        } else if ("remoteUser".equals(parameter)) {
            return new AccessLogElement() {
                @Override
                public void addElement(StringBuilder buf, Date date,
                        Request request, Response response, long time) {
                    buf.append(wrap(request.getRemoteUser()));
                }
            };
        } else if ("requestedSessionId".equals(parameter)) {
            return new AccessLogElement() {
                @Override
                public void addElement(StringBuilder buf, Date date,
                        Request request, Response response, long time) {
                    buf.append(wrap(request.getRequestedSessionId()));
                }
            };
        } else if ("requestedSessionIdFromCookie".equals(parameter)) {
            return new AccessLogElement() {
                @Override
                public void addElement(StringBuilder buf, Date date,
                        Request request, Response response, long time) {
                    buf.append(wrap(""
                            + request.isRequestedSessionIdFromCookie()));
                }
            };
        } else if ("requestedSessionIdValid".equals(parameter)) {
            return new AccessLogElement() {
                @Override
                public void addElement(StringBuilder buf, Date date,
                        Request request, Response response, long time) {
                    buf.append(wrap("" + request.isRequestedSessionIdValid()));
                }
            };
        } else if ("contentLength".equals(parameter)) {
            return new AccessLogElement() {
                @Override
                public void addElement(StringBuilder buf, Date date,
                        Request request, Response response, long time) {
                    buf.append(wrap("" + request.getContentLength()));
                }
            };
        } else if ("characterEncoding".equals(parameter)) {
            return new AccessLogElement() {
                @Override
                public void addElement(StringBuilder buf, Date date,
                        Request request, Response response, long time) {
                    buf.append(wrap(request.getCharacterEncoding()));
                }
            };
        } else if ("locale".equals(parameter)) {
            return new AccessLogElement() {
                @Override
                public void addElement(StringBuilder buf, Date date,
                        Request request, Response response, long time) {
                    buf.append(wrap(request.getLocale()));
                }
            };
        } else if ("protocol".equals(parameter)) {
            return new AccessLogElement() {
                @Override
                public void addElement(StringBuilder buf, Date date,
                        Request request, Response response, long time) {
                    buf.append(wrap(request.getProtocol()));
                }
            };
        } else if ("scheme".equals(parameter)) {
            return new AccessLogElement() {
                @Override
                public void addElement(StringBuilder buf, Date date,
                        Request request, Response response, long time) {
                    buf.append(request.getScheme());
                }
            };
        } else if ("secure".equals(parameter)) {
            return new AccessLogElement() {
                @Override
                public void addElement(StringBuilder buf, Date date,
                        Request request, Response response, long time) {
                    buf.append(wrap("" + request.isSecure()));
                }
            };
        }
        log.error("x param for servlet request, couldn't decode value: "
                + parameter);
        return null;
    }

    private static class ElementTimestampStruct {
        private final Date currentTimestamp = new Date(0);
        private final SimpleDateFormat currentTimestampFormat;
        private String currentTimestampString;

        ElementTimestampStruct(String format) {
            currentTimestampFormat = new SimpleDateFormat(format, Locale.US);
            currentTimestampFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        }
    }
}
