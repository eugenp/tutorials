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
package org.apache.catalina.core;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.naming.NamingException;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.AsyncDispatcher;
import org.apache.catalina.Context;
import org.apache.catalina.Globals;
import org.apache.catalina.Host;
import org.apache.catalina.Valve;
import org.apache.catalina.connector.Request;
import org.apache.catalina.util.URLEncoder;
import org.apache.coyote.ActionCode;
import org.apache.coyote.AsyncContextCallback;
import org.apache.coyote.RequestInfo;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.InstanceManager;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.res.StringManager;
import org.apache.tomcat.util.security.PrivilegedGetTccl;
import org.apache.tomcat.util.security.PrivilegedSetTccl;
/**
 *
 * @author fhanik
 *
 */
public class AsyncContextImpl implements AsyncContext, AsyncContextCallback {

    private static final Log log = LogFactory.getLog(AsyncContextImpl.class);

    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);

    /* When a request uses a sequence of multiple start(); dispatch() with
     * non-container threads it is possible for a previous dispatch() to
     * interfere with a following start(). This lock prevents that from
     * happening. It is a dedicated object as user code may lock on the
     * AsyncContext so if container code also locks on that object deadlocks may
     * occur.
     */
    private final Object asyncContextLock = new Object();

    private volatile ServletRequest servletRequest = null;
    private volatile ServletResponse servletResponse = null;
    private List<AsyncListenerWrapper> listeners = new ArrayList<AsyncListenerWrapper>();
    private boolean hasOriginalRequestAndResponse = true;
    private volatile Runnable dispatch = null;
    private Context context = null;
    private long timeout = -1;
    private AsyncEvent event = null;
    private Request request;
    private volatile InstanceManager instanceManager;

    public AsyncContextImpl(Request request) {
        if (log.isDebugEnabled()) {
            logDebug("Constructor");
        }
        this.request = request;
    }

    @Override
    public void complete() {
        if (log.isDebugEnabled()) {
            logDebug("complete   ");
        }
        check();
        request.getCoyoteRequest().action(ActionCode.ASYNC_COMPLETE, null);
    }

    @Override
    public void fireOnComplete() {
        List<AsyncListenerWrapper> listenersCopy =
                new ArrayList<AsyncListenerWrapper>();
        listenersCopy.addAll(listeners);

        ClassLoader oldCL;
        if (Globals.IS_SECURITY_ENABLED) {
            PrivilegedAction<ClassLoader> pa = new PrivilegedGetTccl();
            oldCL = AccessController.doPrivileged(pa);
        } else {
            oldCL = Thread.currentThread().getContextClassLoader();
        }
        ClassLoader newCL = context.getLoader().getClassLoader();

        try {
            if (Globals.IS_SECURITY_ENABLED) {
                PrivilegedAction<Void> pa = new PrivilegedSetTccl(newCL);
                AccessController.doPrivileged(pa);
            } else {
                Thread.currentThread().setContextClassLoader(newCL);
            }
            for (AsyncListenerWrapper listener : listenersCopy) {
                try {
                    listener.fireOnComplete(event);
                } catch (Throwable t) {
                    ExceptionUtils.handleThrowable(t);
                    log.warn("onComplete() failed for listener of type [" +
                            listener.getClass().getName() + "]", t);
                }
            }
        } finally {
            context.fireRequestDestroyEvent(request.getRequest());
            clearServletRequestResponse();
            if (Globals.IS_SECURITY_ENABLED) {
                PrivilegedAction<Void> pa = new PrivilegedSetTccl(oldCL);
                AccessController.doPrivileged(pa);
            } else {
                Thread.currentThread().setContextClassLoader(oldCL);
            }
        }
    }


    public boolean timeout() {
        AtomicBoolean result = new AtomicBoolean();
        request.getCoyoteRequest().action(ActionCode.ASYNC_TIMEOUT, result);

        if (result.get()) {

            ClassLoader oldCL = Thread.currentThread().getContextClassLoader();
            ClassLoader newCL = request.getContext().getLoader().getClassLoader();
            try {
                Thread.currentThread().setContextClassLoader(newCL);
                List<AsyncListenerWrapper> listenersCopy =
                    new ArrayList<AsyncListenerWrapper>();
                listenersCopy.addAll(listeners);
                for (AsyncListenerWrapper listener : listenersCopy) {
                    try {
                        listener.fireOnTimeout(event);
                    } catch (Throwable t) {
                        ExceptionUtils.handleThrowable(t);
                        log.warn("onTimeout() failed for listener of type [" +
                                listener.getClass().getName() + "]", t);
                    }
                }
                request.getCoyoteRequest().action(
                        ActionCode.ASYNC_IS_TIMINGOUT, result);
                return !result.get();
            } finally {
                Thread.currentThread().setContextClassLoader(oldCL);
            }
        }
        return true;
    }

    @Override
    public void dispatch() {
        check();
        String path;
        String cpath;
        ServletRequest servletRequest = getRequest();
        if (servletRequest instanceof HttpServletRequest) {
            HttpServletRequest sr = (HttpServletRequest) servletRequest;
            path = sr.getRequestURI();
            cpath = sr.getContextPath();
        } else {
            path = request.getRequestURI();
            cpath = request.getContextPath();
        }
        if (cpath.length() > 1) {
            path = path.substring(cpath.length());
        }
        if (!context.getDispatchersUseEncodedPaths()) {
            path = URLEncoder.DEFAULT.encode(path, "UTF-8");
        }
        dispatch(path);
    }

    @Override
    public void dispatch(String path) {
        check();
        dispatch(getRequest().getServletContext(), path);
    }

    @Override
    public void dispatch(ServletContext context, String path) {
        synchronized (asyncContextLock) {
            if (log.isDebugEnabled()) {
                logDebug("dispatch   ");
            }
            check();
            if (dispatch != null) {
                throw new IllegalStateException(
                        sm.getString("asyncContextImpl.dispatchingStarted"));
            }
            if (request.getAttribute(ASYNC_REQUEST_URI)==null) {
                request.setAttribute(ASYNC_REQUEST_URI, request.getRequestURI());
                request.setAttribute(ASYNC_CONTEXT_PATH, request.getContextPath());
                request.setAttribute(ASYNC_SERVLET_PATH, request.getServletPath());
                request.setAttribute(ASYNC_PATH_INFO, request.getPathInfo());
                request.setAttribute(ASYNC_QUERY_STRING, request.getQueryString());
            }
            final RequestDispatcher requestDispatcher = context.getRequestDispatcher(path);
            if (!(requestDispatcher instanceof AsyncDispatcher)) {
                throw new UnsupportedOperationException(
                        sm.getString("asyncContextImpl.noAsyncDispatcher"));
            }
            final AsyncDispatcher applicationDispatcher =
                    (AsyncDispatcher) requestDispatcher;
            final ServletRequest servletRequest = getRequest();
            final ServletResponse servletResponse = getResponse();
            Runnable run = new Runnable() {
                @Override
                public void run() {
                    request.getCoyoteRequest().action(ActionCode.ASYNC_DISPATCHED, null);
                    try {
                        applicationDispatcher.dispatch(servletRequest, servletResponse);
                    }catch (Exception x) {
                        //log.error("Async.dispatch",x);
                        throw new RuntimeException(x);
                    }
                }
            };

            this.dispatch = run;
            this.request.getCoyoteRequest().action(ActionCode.ASYNC_DISPATCH, null);
            clearServletRequestResponse();
        }
    }

    @Override
    public ServletRequest getRequest() {
        check();
        if (servletRequest == null) {
            throw new IllegalStateException(
                    sm.getString("asyncContextImpl.request.ise"));
        }
        return servletRequest;
    }

    @Override
    public ServletResponse getResponse() {
        check();
        if (servletResponse == null) {
            throw new IllegalStateException(
                    sm.getString("asyncContextImpl.response.ise"));
        }
        return servletResponse;
    }

    @Override
    public void start(final Runnable run) {
        if (log.isDebugEnabled()) {
            logDebug("start      ");
        }
        check();
        Runnable wrapper = new RunnableWrapper(run, context);
        this.request.getCoyoteRequest().action(ActionCode.ASYNC_RUN, wrapper);
    }

    @Override
    public void addListener(AsyncListener listener) {
        check();
        AsyncListenerWrapper wrapper = new AsyncListenerWrapper();
        wrapper.setListener(listener);
        listeners.add(wrapper);
    }

    @Override
    public void addListener(AsyncListener listener, ServletRequest servletRequest,
            ServletResponse servletResponse) {
        check();
        AsyncListenerWrapper wrapper = new AsyncListenerWrapper();
        wrapper.setListener(listener);
        wrapper.setServletRequest(servletRequest);
        wrapper.setServletResponse(servletResponse);
        listeners.add(wrapper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends AsyncListener> T createListener(Class<T> clazz)
            throws ServletException {
        check();
        T listener = null;
        try {
             listener = (T) getInstanceManager().newInstance(clazz.getName(),
                     clazz.getClassLoader());
        } catch (InstantiationException e) {
            ServletException se = new ServletException(e);
            throw se;
        } catch (IllegalAccessException e) {
            ServletException se = new ServletException(e);
            throw se;
        } catch (InvocationTargetException e) {
            ExceptionUtils.handleThrowable(e.getCause());
            ServletException se = new ServletException(e);
            throw se;
        } catch (NamingException e) {
            ServletException se = new ServletException(e);
            throw se;
        } catch (ClassNotFoundException e) {
            ServletException se = new ServletException(e);
            throw se;
        } catch (Exception e) {
            ExceptionUtils.handleThrowable(e.getCause());
            ServletException se = new ServletException(e);
            throw se;
        }
        return listener;
    }

    public void recycle() {
        if (log.isDebugEnabled()) {
            logDebug("recycle    ");
        }
        context = null;
        dispatch = null;
        event = null;
        hasOriginalRequestAndResponse = true;
        instanceManager = null;
        listeners.clear();
        request = null;
        clearServletRequestResponse();
        timeout = -1;
    }

    private void clearServletRequestResponse() {
        servletRequest = null;
        servletResponse = null;
    }

    public boolean isStarted() {
        AtomicBoolean result = new AtomicBoolean(false);
        request.getCoyoteRequest().action(
                ActionCode.ASYNC_IS_STARTED, result);
        return result.get();
    }

    public void setStarted(Context context, ServletRequest request,
            ServletResponse response, boolean originalRequestResponse) {

        synchronized (asyncContextLock) {
            this.request.getCoyoteRequest().action(
                    ActionCode.ASYNC_START, this);

            this.context = context;
            this.servletRequest = request;
            this.servletResponse = response;
            this.hasOriginalRequestAndResponse = originalRequestResponse;
            this.event = new AsyncEvent(this, request, response);

            List<AsyncListenerWrapper> listenersCopy =
                new ArrayList<AsyncListenerWrapper>();
            listenersCopy.addAll(listeners);
            listeners.clear();
            for (AsyncListenerWrapper listener : listenersCopy) {
                try {
                    listener.fireOnStartAsync(event);
                } catch (Throwable t) {
                    ExceptionUtils.handleThrowable(t);
                    log.warn("onStartAsync() failed for listener of type [" +
                            listener.getClass().getName() + "]", t);
                }
            }
        }
    }

    @Override
    public boolean hasOriginalRequestAndResponse() {
        check();
        return hasOriginalRequestAndResponse;
    }

    protected void doInternalDispatch() throws ServletException, IOException {
        if (log.isDebugEnabled()) {
            logDebug("intDispatch");
        }
        try {
            Runnable runnable = dispatch;
            dispatch = null;
            runnable.run();
            if (!request.isAsync()) {
                fireOnComplete();
            }
        } catch (RuntimeException x) {
            // doInternalComplete(true);
            if (x.getCause() instanceof ServletException) {
                throw (ServletException)x.getCause();
            }
            if (x.getCause() instanceof IOException) {
                throw (IOException)x.getCause();
            }
            throw new ServletException(x);
        }
    }


    @Override
    public long getTimeout() {
        check();
        return timeout;
    }


    @Override
    public void setTimeout(long timeout) {
        check();
        this.timeout = timeout;
        request.getCoyoteRequest().action(ActionCode.ASYNC_SETTIMEOUT,
                Long.valueOf(timeout));
    }


    public void setErrorState(Throwable t, boolean fireOnError) {
        if (t!=null) request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, t);
        request.getCoyoteRequest().action(ActionCode.ASYNC_ERROR, null);

        if (fireOnError) {
            AsyncEvent errorEvent = new AsyncEvent(event.getAsyncContext(),
                    event.getSuppliedRequest(), event.getSuppliedResponse(), t);
            List<AsyncListenerWrapper> listenersCopy =
                    new ArrayList<AsyncListenerWrapper>();
            listenersCopy.addAll(listeners);
            for (AsyncListenerWrapper listener : listenersCopy) {
                try {
                    listener.fireOnError(errorEvent);
                } catch (Throwable t2) {
                    ExceptionUtils.handleThrowable(t);
                    log.warn("onError() failed for listener of type [" +
                            listener.getClass().getName() + "]", t2);
                }
            }
        }


        AtomicBoolean result = new AtomicBoolean();
        request.getCoyoteRequest().action(ActionCode.ASYNC_IS_ERROR, result);
        if (result.get()) {
            // No listener called dispatch() or complete(). This is an error.
            // SRV.2.3.3.3 (search for "error dispatch")
            // Take a local copy to avoid threading issues if another thread
            // clears this (can happen during error handling with non-container
            // threads)
            ServletResponse servletResponse = this.servletResponse;
            if (servletResponse instanceof HttpServletResponse) {
                ((HttpServletResponse) servletResponse).setStatus(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

            Host host = (Host) context.getParent();
            Valve stdHostValve = host.getPipeline().getBasic();
            if (stdHostValve instanceof StandardHostValve) {
                ((StandardHostValve) stdHostValve).throwable(request,
                        request.getResponse(), t);
            }

            request.getCoyoteRequest().action(
                    ActionCode.ASYNC_IS_ERROR, result);
            if (result.get()) {
                // Still in the error state. The error page did not call
                // complete() or dispatch(). Complete the async processing.
                complete();
            }
        }
    }


    private void logDebug(String method) {
        String rHashCode;
        String crHashCode;
        String rpHashCode;
        String stage;
        StringBuilder uri = new StringBuilder();
        if (request == null) {
            rHashCode = "null";
            crHashCode = "null";
            rpHashCode = "null";
            stage = "-";
            uri.append("N/A");
        } else {
            rHashCode = Integer.toHexString(request.hashCode());
            org.apache.coyote.Request coyoteRequest = request.getCoyoteRequest();
            if (coyoteRequest == null) {
                crHashCode = "null";
                rpHashCode = "null";
                stage = "-";
            } else {
                crHashCode = Integer.toHexString(coyoteRequest.hashCode());
                RequestInfo rp = coyoteRequest.getRequestProcessor();
                if (rp == null) {
                    rpHashCode = "null";
                    stage = "-";
                } else {
                    rpHashCode = Integer.toHexString(rp.hashCode());
                    stage = Integer.toString(rp.getStage());
                }
            }
            uri.append(request.getRequestURI());
            if (request.getQueryString() != null) {
                uri.append('?');
                uri.append(request.getQueryString());
            }
        }
        String threadName = Thread.currentThread().getName();
        int len = threadName.length();
        if (len > 20) {
            threadName = threadName.substring(len - 20, len);
        }
        String msg = String.format(
                "Req: %1$8s  CReq: %2$8s  RP: %3$8s  Stage: %4$s  " +
                "Thread: %5$20s  State: %6$20s  Method: %7$11s  URI: %8$s",
                rHashCode, crHashCode, rpHashCode, stage,
                threadName, "N/A", method, uri);
        if (log.isTraceEnabled()) {
            log.trace(msg, new DebugException());
        } else {
            log.debug(msg);
        }
    }

    private InstanceManager getInstanceManager() {
        if (instanceManager == null) {
            if (context instanceof StandardContext) {
                instanceManager = ((StandardContext)context).getInstanceManager();
            } else {
                instanceManager = new DefaultInstanceManager(null,
                        new HashMap<String, Map<String, String>>(),
                        context,
                        getClass().getClassLoader());
            }
        }
        return instanceManager;
    }

    private void check() {
        if (request == null) {
            // AsyncContext has been recycled and should not be being used
            throw new IllegalStateException(sm.getString(
                    "asyncContextImpl.requestEnded"));
        }
    }
    private static class DebugException extends Exception {
        private static final long serialVersionUID = 1L;
    }

    private static class RunnableWrapper implements Runnable {

        private Runnable wrapped = null;
        private Context context = null;

        public RunnableWrapper(Runnable wrapped, Context ctxt) {
            this.wrapped = wrapped;
            this.context = ctxt;
        }

        @Override
        public void run() {
            ClassLoader oldCL;
            if (Globals.IS_SECURITY_ENABLED) {
                PrivilegedAction<ClassLoader> pa = new PrivilegedGetTccl();
                oldCL = AccessController.doPrivileged(pa);
            } else {
                oldCL = Thread.currentThread().getContextClassLoader();
            }

            try {
                if (Globals.IS_SECURITY_ENABLED) {
                    PrivilegedAction<Void> pa = new PrivilegedSetTccl(
                            context.getLoader().getClassLoader());
                    AccessController.doPrivileged(pa);
                } else {
                    Thread.currentThread().setContextClassLoader
                            (context.getLoader().getClassLoader());
                }
                wrapped.run();
            } finally {
                if (Globals.IS_SECURITY_ENABLED) {
                    PrivilegedAction<Void> pa = new PrivilegedSetTccl(
                            oldCL);
                    AccessController.doPrivileged(pa);
                } else {
                    Thread.currentThread().setContextClassLoader(oldCL);
                }
            }
        }

    }
}
