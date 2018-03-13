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

/**
 * ActionCodes represent callbacks from the servlet container to the coyote
 * connector. Actions are implemented by ProtocolHandler, using the ActionHook
 * interface.
 * 
 * @see ProtocolHandler
 * @see ActionHook
 * @author Remy Maucherat
 */
public enum ActionCode {
    ACK,
    CLOSE,
    COMMIT,

    /**
     * A serious error occurred from which it is not possible to recover safely.
     * Further attempts to write to the response should be ignored and the
     * connection needs to be closed as soon as possible. This can also be used
     * to forcibly close a connection if an error occurs after the response has
     * been committed.
     */
    CLOSE_NOW,

    /**
     * A flush() operation originated by the client ( i.e. a flush() on the
     * servlet output stream or writer, called by a servlet ). Argument is the
     * Response.
     */
    CLIENT_FLUSH,

    CUSTOM,
    RESET,

    /**
     * Hook called after request, but before recycling. Can be used for logging,
     * to update counters, custom cleanup - the request is still visible
     */
    POST_REQUEST,

    /**
     * Has the processor been placed into the error state? Note that the
     * response may not have an appropriate error code set.
     */
    IS_ERROR,

    /**
     * Hook called if swallowing request input should be disabled.
     * Example: Cancel a large file upload.
     * 
     */
    DISABLE_SWALLOW_INPUT,

    /**
     * Callback for lazy evaluation - extract the remote host address.
     */
    REQ_HOST_ATTRIBUTE,

    /**
     * Callback for lazy evaluation - extract the remote host infos (address,
     * name, port) and local address.
     */
    REQ_HOST_ADDR_ATTRIBUTE,

    /**
     * Callback for lazy evaluation - extract the SSL-related attributes.
     */
    REQ_SSL_ATTRIBUTE,

    /**
     * Callback for lazy evaluation - extract the SSL-certificate (including
     * forcing a re-handshake if necessary)
     */
    REQ_SSL_CERTIFICATE,

    /**
     * Callback for lazy evaluation - socket remote port.
     */
    REQ_REMOTEPORT_ATTRIBUTE,

    /**
     * Callback for lazy evaluation - socket local port.
     */
    REQ_LOCALPORT_ATTRIBUTE,

    /**
     * Callback for lazy evaluation - local address.
     */
    REQ_LOCAL_ADDR_ATTRIBUTE,

    /**
     * Callback for lazy evaluation - local address.
     */
    REQ_LOCAL_NAME_ATTRIBUTE,

    /**
     * Callback for setting FORM auth body replay
     */
    REQ_SET_BODY_REPLAY,

    /**
     * Callback for begin Comet processing.
     */
    COMET_BEGIN,

    /**
     * Callback for end Comet processing.
     */
    COMET_END,

    /**
     * Callback for getting the amount of available bytes.
     */
    AVAILABLE,

    /**
     * Callback for an asynchronous close of the Comet event
     */
    COMET_CLOSE,

    /**
     * Callback for setting the timeout asynchronously
     */
    COMET_SETTIMEOUT,

    /**
     * Callback for an async request.
     */
    ASYNC_START,

    /**
     * Callback for an async call to
     * {@link javax.servlet.AsyncContext#dispatch()}.
     */
    ASYNC_DISPATCH,

    /**
     * Callback to indicate the the actual dispatch has started and that the
     * async state needs change.
     */
    ASYNC_DISPATCHED,

    /**
     * Callback for an async call to
     * {@link javax.servlet.AsyncContext#start(Runnable)}.
     */
    ASYNC_RUN,

    /**
     * Callback for an async call to
     * {@link javax.servlet.AsyncContext#complete()}.
     */
    ASYNC_COMPLETE,
    
    /**
     * Callback to trigger the processing of an async timeout.
     */
    ASYNC_TIMEOUT,
    
    /**
     * Callback to trigger the error processing.
     */
    ASYNC_ERROR,
    
    /**
     * Callback for an async call to
     * {@link javax.servlet.AsyncContext#setTimeout(long)}
     */
    ASYNC_SETTIMEOUT,
    
    /**
     * Callback to determine if async processing is in progress.
     */
    ASYNC_IS_ASYNC,
    
    /**
     * Callback to determine if async dispatch is in progress.
     */
    ASYNC_IS_STARTED,

    /**
     * Call back to determine if async complete is in progress.
     */
    ASYNC_IS_COMPLETING,

    /**
     * Callback to determine if async dispatch is in progress.
     */
    ASYNC_IS_DISPATCHING,

    /**
     * Callback to determine if async is timing out.
     */
    ASYNC_IS_TIMINGOUT,

    /**
    * Callback to determine if async is in error.
    */
    ASYNC_IS_ERROR,

    /**
     * Callback to trigger Tomcat's proprietary HTTP upgrade process.
     */
    UPGRADE_TOMCAT,

    /**
     * Callback to trigger post processing. Typically only used during error
     * handling to trigger essential processing that otherwise would be skipped.
     */
    ASYNC_POST_PROCESS,
    
    /**
     * Callback to trigger the Servlet 3.1 based HTTP upgrade process.
     */
    UPGRADE,

    /**
     * Trigger end of request processing (remaining input swallowed, write any
     * remaining parts of the response etc.).
     */
    END_REQUEST
}
