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
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * Web crawlers can trigger the creation of many thousands of sessions as they
 * crawl a site which may result in significant memory consumption. This Valve
 * ensures that crawlers are associated with a single session - just like normal
 * users - regardless of whether or not they provide a session token with their
 * requests.
 */
public class CrawlerSessionManagerValve extends ValveBase implements HttpSessionBindingListener {

    private static final Log log = LogFactory.getLog(CrawlerSessionManagerValve.class);

    private final Map<String, String> clientIpSessionId = new ConcurrentHashMap<String, String>();
    private final Map<String, String> sessionIdClientIp = new ConcurrentHashMap<String, String>();

    private String crawlerUserAgents = ".*[bB]ot.*|.*Yahoo! Slurp.*|.*Feedfetcher-Google.*";
    private Pattern uaPattern = null;

    private String crawlerIps = null;
    private Pattern ipPattern = null;

    private int sessionInactiveInterval = 60;


    /**
     * Specifies a default constructor so async support can be configured.
     */
    public CrawlerSessionManagerValve() {
        super(true);
    }


    /**
     * Specify the regular expression (using {@link Pattern}) that will be used
     * to identify crawlers based in the User-Agent header provided. The default
     * is ".*GoogleBot.*|.*bingbot.*|.*Yahoo! Slurp.*"
     *
     * @param crawlerUserAgents The regular expression using {@link Pattern}
     */
    public void setCrawlerUserAgents(String crawlerUserAgents) {
        this.crawlerUserAgents = crawlerUserAgents;
        if (crawlerUserAgents == null || crawlerUserAgents.length() == 0) {
            uaPattern = null;
        } else {
            uaPattern = Pattern.compile(crawlerUserAgents);
        }
    }

    /**
     * @see #setCrawlerUserAgents(String)
     * @return  The current regular expression being used to match user agents.
     */
    public String getCrawlerUserAgents() {
        return crawlerUserAgents;
    }


    /**
     * Specify the regular expression (using {@link Pattern}) that will be used
     * to identify crawlers based on their IP address. The default is no crawler
     * IPs.
     *
     * @param crawlerIps The regular expression using {@link Pattern}
     */
    public void setCrawlerIps(String crawlerIps) {
        this.crawlerIps = crawlerIps;
        if (crawlerIps == null || crawlerIps.length() == 0) {
            ipPattern = null;
        } else {
            ipPattern = Pattern.compile(crawlerIps);
        }
    }

    /**
     * @see #setCrawlerIps(String)
     * @return The current regular expression being used to match IP addresses.
     */
    public String getCrawlerIps() {
        return crawlerIps;
    }


    /**
     * Specify the session timeout (in seconds) for a crawler's session. This is
     * typically lower than that for a user session. The default is 60 seconds.
     *
     * @param sessionInactiveInterval   The new timeout for crawler sessions
     */
    public void setSessionInactiveInterval(int sessionInactiveInterval) {
        this.sessionInactiveInterval = sessionInactiveInterval;
    }

    /**
     * @see #setSessionInactiveInterval(int)
     * @return  The current timeout in seconds
     */
    public int getSessionInactiveInterval() {
        return sessionInactiveInterval;
    }


    public Map<String, String> getClientIpSessionId() {
        return clientIpSessionId;
    }


    @Override
    protected void initInternal() throws LifecycleException {
        super.initInternal();

        uaPattern = Pattern.compile(crawlerUserAgents);
    }


    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {

        boolean isBot = false;
        String sessionId = null;
        String clientIp = request.getRemoteAddr();

        if (log.isDebugEnabled()) {
            log.debug(request.hashCode() + ": ClientIp=" + clientIp + ", RequestedSessionId="
                    + request.getRequestedSessionId());
        }

        // If the incoming request has a valid session ID, no action is required
        if (request.getSession(false) == null) {

            // Is this a crawler - check the UA headers
            Enumeration<String> uaHeaders = request.getHeaders("user-agent");
            String uaHeader = null;
            if (uaHeaders.hasMoreElements()) {
                uaHeader = uaHeaders.nextElement();
            }

            // If more than one UA header - assume not a bot
            if (uaHeader != null && !uaHeaders.hasMoreElements()) {

                if (log.isDebugEnabled()) {
                    log.debug(request.hashCode() + ": UserAgent=" + uaHeader);
                }

                if (uaPattern.matcher(uaHeader).matches()) {
                    isBot = true;

                    if (log.isDebugEnabled()) {
                        log.debug(request.hashCode() + ": Bot found. UserAgent=" + uaHeader);
                    }
                }
            }

            if (ipPattern != null && ipPattern.matcher(clientIp).matches()) {
                isBot = true;

                if (log.isDebugEnabled()) {
                    log.debug(request.hashCode() + ": Bot found. IP=" + clientIp);
                }
            }

            // If this is a bot, is the session ID known?
            if (isBot) {
                sessionId = clientIpSessionId.get(clientIp);
                if (sessionId != null) {
                    request.setRequestedSessionId(sessionId);
                    if (log.isDebugEnabled()) {
                        log.debug(request.hashCode() + ": SessionID=" + sessionId);
                    }
                }
            }
        }

        getNext().invoke(request, response);

        if (isBot) {
            if (sessionId == null) {
                // Has bot just created a session, if so make a note of it
                HttpSession s = request.getSession(false);
                if (s != null) {
                    clientIpSessionId.put(clientIp, s.getId());
                    sessionIdClientIp.put(s.getId(), clientIp);
                    // #valueUnbound() will be called on session expiration
                    s.setAttribute(this.getClass().getName(), this);
                    s.setMaxInactiveInterval(sessionInactiveInterval);

                    if (log.isDebugEnabled()) {
                        log.debug(request.hashCode() + ": New bot session. SessionID=" + s.getId());
                    }
                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug(
                            request.hashCode() + ": Bot session accessed. SessionID=" + sessionId);
                }
            }
        }
    }


    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        // NOOP
    }


    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        String clientIp = sessionIdClientIp.remove(event.getSession().getId());
        if (clientIp != null) {
            clientIpSessionId.remove(clientIp);
        }
    }
}
