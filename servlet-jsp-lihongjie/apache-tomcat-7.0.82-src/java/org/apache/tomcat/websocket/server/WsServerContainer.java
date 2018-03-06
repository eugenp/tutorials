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
package org.apache.tomcat.websocket.server;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.DeploymentException;
import javax.websocket.Encoder;
import javax.websocket.Endpoint;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.util.res.StringManager;
import org.apache.tomcat.websocket.WsSession;
import org.apache.tomcat.websocket.WsWebSocketContainer;
import org.apache.tomcat.websocket.pojo.PojoEndpointServer;
import org.apache.tomcat.websocket.pojo.PojoMethodMapping;

/**
 * Provides a per class loader (i.e. per web application) instance of a
 * ServerContainer. Web application wide defaults may be configured by setting
 * the following servlet context initialisation parameters to the desired
 * values.
 * <ul>
 * <li>{@link Constants#BINARY_BUFFER_SIZE_SERVLET_CONTEXT_INIT_PARAM}</li>
 * <li>{@link Constants#TEXT_BUFFER_SIZE_SERVLET_CONTEXT_INIT_PARAM}</li>
 * </ul>
 */
public class WsServerContainer extends WsWebSocketContainer
        implements ServerContainer {

    private static final StringManager sm =
            StringManager.getManager(Constants.PACKAGE_NAME);
    private static final Log log = LogFactory.getLog(WsServerContainer.class);

    private static final CloseReason AUTHENTICATED_HTTP_SESSION_CLOSED =
            new CloseReason(CloseCodes.VIOLATED_POLICY,
                    "This connection was established under an authenticated " +
                    "HTTP session that has ended.");

    private final WsWriteTimeout wsWriteTimeout = new WsWriteTimeout();

    private final ServletContext servletContext;
    private final Map<String,ServerEndpointConfig> configExactMatchMap =
            new ConcurrentHashMap<String, ServerEndpointConfig>();
    private final ConcurrentMap<Integer,SortedSet<TemplatePathMatch>>
            configTemplateMatchMap = new ConcurrentHashMap<Integer, SortedSet<TemplatePathMatch>>();
    private volatile boolean enforceNoAddAfterHandshake =
            org.apache.tomcat.websocket.Constants.STRICT_SPEC_COMPLIANCE;
    private volatile boolean addAllowed = true;
    private final ConcurrentMap<String,Set<WsSession>> authenticatedSessions =
            new ConcurrentHashMap<String, Set<WsSession>>();
    private final ExecutorService executorService;
    private final ThreadGroup threadGroup;
    private volatile boolean endpointsRegistered = false;

    WsServerContainer(ServletContext servletContext) {

        this.servletContext = servletContext;
        setInstanceManager((InstanceManager) servletContext.getAttribute(InstanceManager.class.getName()));

        // Configure servlet context wide defaults
        String value = servletContext.getInitParameter(
                Constants.BINARY_BUFFER_SIZE_SERVLET_CONTEXT_INIT_PARAM);
        if (value != null) {
            setDefaultMaxBinaryMessageBufferSize(Integer.parseInt(value));
        }

        value = servletContext.getInitParameter(
                Constants.TEXT_BUFFER_SIZE_SERVLET_CONTEXT_INIT_PARAM);
        if (value != null) {
            setDefaultMaxTextMessageBufferSize(Integer.parseInt(value));
        }

        value = servletContext.getInitParameter(
                Constants.ENFORCE_NO_ADD_AFTER_HANDSHAKE_CONTEXT_INIT_PARAM);
        if (value != null) {
            setEnforceNoAddAfterHandshake(Boolean.parseBoolean(value));
        }
        // Executor config
        int executorCoreSize = 0;
        long executorKeepAliveTimeSeconds = 60;
        value = servletContext.getInitParameter(
                Constants.EXECUTOR_CORE_SIZE_INIT_PARAM);
        if (value != null) {
            executorCoreSize = Integer.parseInt(value);
        }
        value = servletContext.getInitParameter(
                Constants.EXECUTOR_KEEPALIVETIME_SECONDS_INIT_PARAM);
        if (value != null) {
            executorKeepAliveTimeSeconds = Long.parseLong(value);
        }

        FilterRegistration.Dynamic fr = servletContext.addFilter(
                "Tomcat WebSocket (JSR356) Filter", new WsFilter());
        fr.setAsyncSupported(true);

        EnumSet<DispatcherType> types = EnumSet.of(DispatcherType.REQUEST,
                DispatcherType.FORWARD);

        fr.addMappingForUrlPatterns(types, true, "/*");

        // Use a per web application executor for any threads that the WebSocket
        // server code needs to create. Group all of the threads under a single
        // ThreadGroup.
        StringBuffer threadGroupName = new StringBuffer("WebSocketServer-");
        if ("".equals(servletContext.getContextPath())) {
            threadGroupName.append("ROOT");
        } else {
            threadGroupName.append(servletContext.getContextPath());
        }
        threadGroup = new ThreadGroup(threadGroupName.toString());
        WsThreadFactory wsThreadFactory = new WsThreadFactory(threadGroup);

        executorService = new ThreadPoolExecutor(executorCoreSize,
                Integer.MAX_VALUE, executorKeepAliveTimeSeconds, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), wsThreadFactory);
    }


    /**
     * Published the provided endpoint implementation at the specified path with
     * the specified configuration. {@link #WsServerContainer(ServletContext)}
     * must be called before calling this method.
     *
     * @param sec   The configuration to use when creating endpoint instances
     * @throws DeploymentException
     */
    @Override
    public void addEndpoint(ServerEndpointConfig sec)
            throws DeploymentException {

        if (enforceNoAddAfterHandshake && !addAllowed) {
            throw new DeploymentException(
                    sm.getString("serverContainer.addNotAllowed"));
        }

        if (servletContext == null) {
            throw new DeploymentException(
                    sm.getString("serverContainer.servletContextMissing"));
        }
        String path = sec.getPath();

        // Add method mapping to user properties
        PojoMethodMapping methodMapping = new PojoMethodMapping(sec.getEndpointClass(),
                sec.getDecoders(), path);
        if (methodMapping.getOnClose() != null || methodMapping.getOnOpen() != null
                || methodMapping.getOnError() != null || methodMapping.hasMessageHandlers()) {
            sec.getUserProperties().put(
                    PojoEndpointServer.POJO_METHOD_MAPPING_KEY,
                    methodMapping);
        }

        UriTemplate uriTemplate = new UriTemplate(path);
        if (uriTemplate.hasParameters()) {
            Integer key = Integer.valueOf(uriTemplate.getSegmentCount());
            SortedSet<TemplatePathMatch> templateMatches =
                    configTemplateMatchMap.get(key);
            if (templateMatches == null) {
                // Ensure that if concurrent threads execute this block they
                // both end up using the same TreeSet instance
                templateMatches = new TreeSet<TemplatePathMatch>(
                        TemplatePathMatchComparator.getInstance());
                configTemplateMatchMap.putIfAbsent(key, templateMatches);
                templateMatches = configTemplateMatchMap.get(key);
            }
            if (!templateMatches.add(new TemplatePathMatch(sec, uriTemplate))) {
                // Duplicate uriTemplate;
                throw new DeploymentException(
                        sm.getString("serverContainer.duplicatePaths", path,
                                     sec.getEndpointClass(),
                                     sec.getEndpointClass()));
            }
        } else {
            // Exact match
            ServerEndpointConfig old = configExactMatchMap.put(path, sec);
            if (old != null) {
                // Duplicate path mappings
                throw new DeploymentException(
                        sm.getString("serverContainer.duplicatePaths", path,
                                     old.getEndpointClass(),
                                     sec.getEndpointClass()));
            }
        }

        endpointsRegistered = true;
    }


    /**
     * Provides the equivalent of {@link #addEndpoint(ServerEndpointConfig)}
     * for publishing plain old java objects (POJOs) that have been annotated as
     * WebSocket endpoints.
     *
     * @param pojo   The annotated POJO
     */
    @Override
    public void addEndpoint(Class<?> pojo) throws DeploymentException {

        ServerEndpoint annotation = pojo.getAnnotation(ServerEndpoint.class);
        if (annotation == null) {
            throw new DeploymentException(
                    sm.getString("serverContainer.missingAnnotation",
                            pojo.getName()));
        }
        String path = annotation.value();

        // Validate encoders
        validateEncoders(annotation.encoders());

        // ServerEndpointConfig
        ServerEndpointConfig sec;
        Class<? extends Configurator> configuratorClazz =
                annotation.configurator();
        Configurator configurator = null;
        if (!configuratorClazz.equals(Configurator.class)) {
            try {
                configurator = annotation.configurator().newInstance();
            } catch (InstantiationException e) {
                throw new DeploymentException(sm.getString(
                        "serverContainer.configuratorFail",
                        annotation.configurator().getName(),
                        pojo.getClass().getName()), e);
            } catch (IllegalAccessException e) {
                throw new DeploymentException(sm.getString(
                        "serverContainer.configuratorFail",
                        annotation.configurator().getName(),
                        pojo.getClass().getName()), e);
            }
        }
        sec = ServerEndpointConfig.Builder.create(pojo, path).
                decoders(Arrays.asList(annotation.decoders())).
                encoders(Arrays.asList(annotation.encoders())).
                subprotocols(Arrays.asList(annotation.subprotocols())).
                configurator(configurator).
                build();

        addEndpoint(sec);
    }


    @Override
    public void destroy() {
        shutdownExecutor();
        super.destroy();
        // If the executor hasn't fully shutdown it won't be possible to
        // destroy this thread group as there will still be threads running.
        // Mark the thread group as daemon one, so that it destroys itself
        // when thread count reaches zero.
        // Synchronization on threadGroup is needed, as there is a race between
        // destroy() call from termination of the last thread in thread group
        // marked as daemon versus the explicit destroy() call.
        int threadCount = threadGroup.activeCount();
        boolean success = false;
        try {
            while (true) {
                int oldThreadCount = threadCount;
                synchronized (threadGroup) {
                    if (threadCount > 0) {
                        Thread.yield();
                        threadCount = threadGroup.activeCount();
                    }
                    if (threadCount > 0 && threadCount != oldThreadCount) {
                        // Value not stabilized. Retry.
                        continue;
                    }
                    if (threadCount > 0) {
                        threadGroup.setDaemon(true);
                    } else {
                        threadGroup.destroy();
                        success = true;
                    }
                    break;
                }
            }
        } catch (IllegalThreadStateException exception) {
            // Fall-through
        }
        if (!success) {
            log.warn(sm.getString("serverContainer.threadGroupNotDestroyed",
                    threadGroup.getName(), Integer.valueOf(threadCount)));
        }
    }


    boolean areEndpointsRegistered() {
        return endpointsRegistered;
    }


    /**
     * Until the WebSocket specification provides such a mechanism, this Tomcat
     * proprietary method is provided to enable applications to programmatically
     * determine whether or not to upgrade an individual request to WebSocket.
     * <p>
     * Note: This method is not used by Tomcat but is used directly by
     *       third-party code and must not be removed.
     *
     * @param request The request object to be upgraded
     * @param response The response object to be populated with the result of
     *                 the upgrade
     * @param sec The server endpoint to use to process the upgrade request
     * @param pathParams The path parameters associated with the upgrade request
     *
     * @throws ServletException If a configuration error prevents the upgrade
     *         from taking place
     * @throws IOException If an I/O error occurs during the upgrade process
     */
    public void doUpgrade(HttpServletRequest request,
            HttpServletResponse response, ServerEndpointConfig sec,
            Map<String,String> pathParams)
            throws ServletException, IOException {
        UpgradeUtil.doUpgrade(this, request, response, sec, pathParams);
    }


    public WsMappingResult findMapping(String path) {

        // Prevent registering additional endpoints once the first attempt has
        // been made to use one
        if (addAllowed) {
            addAllowed = false;
        }

        // Check an exact match. Simple case as there are no templates.
        ServerEndpointConfig sec = configExactMatchMap.get(path);
        if (sec != null) {
            return new WsMappingResult(sec, Collections.<String, String>emptyMap());
        }

        // No exact match. Need to look for template matches.
        UriTemplate pathUriTemplate = null;
        try {
            pathUriTemplate = new UriTemplate(path);
        } catch (DeploymentException e) {
            // Path is not valid so can't be matched to a WebSocketEndpoint
            return null;
        }

        // Number of segments has to match
        Integer key = Integer.valueOf(pathUriTemplate.getSegmentCount());
        SortedSet<TemplatePathMatch> templateMatches =
                configTemplateMatchMap.get(key);

        if (templateMatches == null) {
            // No templates with an equal number of segments so there will be
            // no matches
            return null;
        }

        // List is in alphabetical order of normalised templates.
        // Correct match is the first one that matches.
        Map<String,String> pathParams = null;
        for (TemplatePathMatch templateMatch : templateMatches) {
            pathParams = templateMatch.getUriTemplate().match(pathUriTemplate);
            if (pathParams != null) {
                sec = templateMatch.getConfig();
                break;
            }
        }

        if (sec == null) {
            // No match
            return null;
        }

        return new WsMappingResult(sec, pathParams);
    }



    public boolean isEnforceNoAddAfterHandshake() {
        return enforceNoAddAfterHandshake;
    }


    public void setEnforceNoAddAfterHandshake(
            boolean enforceNoAddAfterHandshake) {
        this.enforceNoAddAfterHandshake = enforceNoAddAfterHandshake;
    }


    protected WsWriteTimeout getTimeout() {
        return wsWriteTimeout;
    }


    /**
     * {@inheritDoc}
     *
     * Overridden to make it visible to other classes in this package.
     */
    @Override
    protected void registerSession(Endpoint endpoint, WsSession wsSession) {
        super.registerSession(endpoint, wsSession);
        if (wsSession.isOpen() &&
                wsSession.getUserPrincipal() != null &&
                wsSession.getHttpSessionId() != null) {
            registerAuthenticatedSession(wsSession,
                    wsSession.getHttpSessionId());
        }
    }


    /**
     * {@inheritDoc}
     *
     * Overridden to make it visible to other classes in this package.
     */
    @Override
    protected void unregisterSession(Endpoint endpoint, WsSession wsSession) {
        if (wsSession.getUserPrincipal() != null &&
                wsSession.getHttpSessionId() != null) {
            unregisterAuthenticatedSession(wsSession,
                    wsSession.getHttpSessionId());
        }
        super.unregisterSession(endpoint, wsSession);
    }


    private void registerAuthenticatedSession(WsSession wsSession,
            String httpSessionId) {
        Set<WsSession> wsSessions = authenticatedSessions.get(httpSessionId);
        if (wsSessions == null) {
            wsSessions = Collections.newSetFromMap(
                     new ConcurrentHashMap<WsSession,Boolean>());
             authenticatedSessions.putIfAbsent(httpSessionId, wsSessions);
             wsSessions = authenticatedSessions.get(httpSessionId);
        }
        wsSessions.add(wsSession);
    }


    private void unregisterAuthenticatedSession(WsSession wsSession,
            String httpSessionId) {
        Set<WsSession> wsSessions = authenticatedSessions.get(httpSessionId);
        // wsSessions will be null if the HTTP session has ended
        if (wsSessions != null) {
            wsSessions.remove(wsSession);
        }
    }


    public void closeAuthenticatedSession(String httpSessionId) {
        Set<WsSession> wsSessions = authenticatedSessions.remove(httpSessionId);

        if (wsSessions != null && !wsSessions.isEmpty()) {
            for (WsSession wsSession : wsSessions) {
                try {
                    wsSession.close(AUTHENTICATED_HTTP_SESSION_CLOSED);
                } catch (IOException e) {
                    // Any IOExceptions during close will have been caught and the
                    // onError method called.
                }
            }
        }
    }


    ExecutorService getExecutorService() {
        return executorService;
    }


    private void shutdownExecutor() {
        if (executorService == null) {
            return;
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // Ignore the interruption and carry on
        }
    }

    private static void validateEncoders(Class<? extends Encoder>[] encoders)
            throws DeploymentException {

        for (Class<? extends Encoder> encoder : encoders) {
            // Need to instantiate decoder to ensure it is valid and that
            // deployment can be failed if it is not
            @SuppressWarnings("unused")
            Encoder instance;
            try {
                encoder.newInstance();
            } catch(InstantiationException e) {
                throw new DeploymentException(sm.getString(
                        "serverContainer.encoderFail", encoder.getName()), e);
            } catch (IllegalAccessException e) {
                throw new DeploymentException(sm.getString(
                        "serverContainer.encoderFail", encoder.getName()), e);
            }
        }
    }


    private static class TemplatePathMatch {
        private final ServerEndpointConfig config;
        private final UriTemplate uriTemplate;

        public TemplatePathMatch(ServerEndpointConfig config,
                UriTemplate uriTemplate) {
            this.config = config;
            this.uriTemplate = uriTemplate;
        }


        public ServerEndpointConfig getConfig() {
            return config;
        }


        public UriTemplate getUriTemplate() {
            return uriTemplate;
        }
    }


    /**
     * This Comparator implementation is thread-safe so only create a single
     * instance.
     */
    private static class TemplatePathMatchComparator
            implements Comparator<TemplatePathMatch> {

        private static final TemplatePathMatchComparator INSTANCE =
                new TemplatePathMatchComparator();

        public static TemplatePathMatchComparator getInstance() {
            return INSTANCE;
        }

        private TemplatePathMatchComparator() {
            // Hide default constructor
        }

        @Override
        public int compare(TemplatePathMatch tpm1, TemplatePathMatch tpm2) {
            return tpm1.getUriTemplate().getNormalizedPath().compareTo(
                    tpm2.getUriTemplate().getNormalizedPath());
        }
    }


    private static class WsThreadFactory implements ThreadFactory {

        private final ThreadGroup tg;
        private final AtomicLong count = new AtomicLong(0);

        private WsThreadFactory(ThreadGroup tg) {
            this.tg = tg;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(tg, r);
            t.setName(tg.getName() + "-" + count.incrementAndGet());
            return t;
        }
    }
}
