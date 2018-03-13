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
package org.apache.coyote;

import java.io.IOException;
import java.util.concurrent.Executor;

import javax.servlet.RequestDispatcher;

import org.apache.juli.logging.Log;
import org.apache.tomcat.util.net.AbstractEndpoint;
import org.apache.tomcat.util.net.AbstractEndpoint.Handler.SocketState;
import org.apache.tomcat.util.net.SocketStatus;
import org.apache.tomcat.util.net.SocketWrapper;
import org.apache.tomcat.util.res.StringManager;

/**
 * Provides functionality and attributes common to all supported protocols
 * (currently HTTP and AJP).
 */
public abstract class AbstractProcessor<S> implements ActionHook, Processor<S> {

    protected static final StringManager sm = StringManager.getManager(Constants.Package);

    protected Adapter adapter;
    protected AsyncStateMachine<S> asyncStateMachine;
    protected AbstractEndpoint<S> endpoint;
    protected Request request;
    protected Response response;
    protected SocketWrapper<S> socketWrapper = null;
    private int maxCookieCount = 200;


    /**
     * Error state for the request/response currently being processed.
     */
    private ErrorState errorState = ErrorState.NONE;


    /**
     * Intended for use by the Upgrade sub-classes that have no need to
     * initialise the request, response, etc.
     */
    protected AbstractProcessor() {
        // NOOP
    }

    public AbstractProcessor(AbstractEndpoint<S> endpoint) {
        this.endpoint = endpoint;
        asyncStateMachine = new AsyncStateMachine<S>(this);
        request = new Request();
        response = new Response();
        response.setHook(this);
        request.setResponse(response);
    }


    /**
     * Update the current error state to the new error state if the new error
     * state is more severe than the current error state.
     */
    protected void setErrorState(ErrorState errorState, Throwable t) {
        boolean blockIo = this.errorState.isIoAllowed() && !errorState.isIoAllowed();
        this.errorState = this.errorState.getMostSevere(errorState);
        // Don't change the status code for IOException since that is almost
        // certainly a client disconnect in which case it is preferable to keep
        // the original status code http://markmail.org/message/4cxpwmxhtgnrwh7n
        if (response.getStatus() < 400 && !(t instanceof IOException)) {
            response.setStatus(500);
        }
        if (t != null) {
            request.setAttribute(RequestDispatcher.ERROR_EXCEPTION, t);
        }
        if (blockIo && !ContainerThreadMarker.isContainerThread() && isAsync()) {
            // The error occurred on a non-container thread during async
            // processing which means not all of the necessary clean-up will
            // have been completed. Dispatch to a container thread to do the
            // clean-up. Need to do it this way to ensure that all the necessary
            // clean-up is performed.
            asyncStateMachine.asyncMustError();
            getLog().info(sm.getString("abstractProcessor.nonContainerThreadError"), t);
            getEndpoint().processSocketAsync(socketWrapper, SocketStatus.ERROR);
        }
    }


    protected void resetErrorState() {
        errorState = ErrorState.NONE;
    }


    protected ErrorState getErrorState() {
        return errorState;
    }

    /**
     * The endpoint receiving connections that are handled by this processor.
     */
    protected AbstractEndpoint<S> getEndpoint() {
        return endpoint;
    }


    /**
     * The request associated with this processor.
     */
    @Override
    public Request getRequest() {
        return request;
    }


    /**
     * Set the associated adapter.
     *
     * @param adapter the new adapter
     */
    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
    }


    /**
     * Get the associated adapter.
     *
     * @return the associated adapter
     */
    public Adapter getAdapter() {
        return adapter;
    }


    /**
     * Set the socket wrapper being used.
     */
    protected final void setSocketWrapper(SocketWrapper<S> socketWrapper) {
        this.socketWrapper = socketWrapper;
    }


    /**
     * Get the socket wrapper being used.
     */
    protected final SocketWrapper<S> getSocketWrapper() {
        return socketWrapper;
    }


    /**
     * Obtain the Executor used by the underlying endpoint.
     */
    @Override
    public Executor getExecutor() {
        return endpoint.getExecutor();
    }


    @Override
    public boolean isAsync() {
        return (asyncStateMachine != null && asyncStateMachine.isAsync());
    }


    @Override
    public SocketState asyncPostProcess() {
        return asyncStateMachine.asyncPostProcess();
    }

    @Override
    public void errorDispatch() {
        getAdapter().errorDispatch(request, response);
    }

    @Override
    public abstract boolean isComet();

    @Override
    public abstract boolean isUpgrade();

    /**
     * Process HTTP requests. All requests are treated as HTTP requests to start
     * with although they may change type during processing.
     */
    @Override
    public abstract SocketState process(SocketWrapper<S> socket) throws IOException;

    /**
     * Process in-progress Comet requests. These will start as HTTP requests.
     */
    @Override
    public abstract SocketState event(SocketStatus status) throws IOException;

    /**
     * Process in-progress Servlet 3.0 Async requests. These will start as HTTP
     * requests.
     */
    @Override
    public abstract SocketState asyncDispatch(SocketStatus status);

    /**
     * Processes data received on a connection that has been through an HTTP
     * upgrade.
     */
    @Override
    public abstract SocketState upgradeDispatch() throws IOException;


    public int getMaxCookieCount() {
        return maxCookieCount;
    }


    public void setMaxCookieCount(int maxCookieCount) {
        this.maxCookieCount = maxCookieCount;
    }


     /**
     * @deprecated  Will be removed in Tomcat 8.0.x.
     */
    @Deprecated
    @Override
    public abstract org.apache.coyote.http11.upgrade.UpgradeInbound getUpgradeInbound();

    protected abstract Log getLog();
}
