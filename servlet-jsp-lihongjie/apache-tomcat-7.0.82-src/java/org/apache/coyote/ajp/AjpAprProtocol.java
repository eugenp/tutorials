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
package org.apache.coyote.ajp;

import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.Processor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.net.AbstractEndpoint;
import org.apache.tomcat.util.net.AprEndpoint;
import org.apache.tomcat.util.net.AprEndpoint.Handler;
import org.apache.tomcat.util.net.SocketWrapper;


/**
 * Abstract the protocol implementation, including threading, etc.
 * Processor is single threaded and specific to stream-based protocols,
 * will not fit Jk protocols like JNI.
 *
 * @author Remy Maucherat
 * @author Costin Manolache
 */
public class AjpAprProtocol extends AbstractAjpProtocol<Long> {
    
    
    private static final Log log = LogFactory.getLog(AjpAprProtocol.class);

    @Override
    protected Log getLog() { return log; }


    @Override
    protected AbstractEndpoint.Handler getHandler() {
        return cHandler;
    }


    @Override
    public boolean isAprRequired() {
        // Override since this protocol implementation requires the APR/native
        // library
        return true;
    }


    // ------------------------------------------------------------ Constructor

    public AjpAprProtocol() {
        endpoint = new AprEndpoint();
        cHandler = new AjpConnectionHandler(this);
        ((AprEndpoint) endpoint).setHandler(cHandler);
        setSoLinger(Constants.DEFAULT_CONNECTION_LINGER);
        setSoTimeout(Constants.DEFAULT_CONNECTION_TIMEOUT);
        setTcpNoDelay(Constants.DEFAULT_TCP_NO_DELAY);
        // AJP does not use Send File
        ((AprEndpoint) endpoint).setUseSendfile(false);
    }

    
    // ----------------------------------------------------- Instance Variables


    /**
     * Connection handler for AJP.
     */
    private AjpConnectionHandler cHandler;


    // --------------------------------------------------------- Public Methods


    public int getPollTime() { return ((AprEndpoint)endpoint).getPollTime(); }
    public void setPollTime(int pollTime) { ((AprEndpoint)endpoint).setPollTime(pollTime); }

    // pollerSize is now a synonym for maxConnections
    public void setPollerSize(int pollerSize) { endpoint.setMaxConnections(pollerSize); }
    public int getPollerSize() { return endpoint.getMaxConnections(); }


    // ----------------------------------------------------- JMX related methods

    @Override
    protected String getNamePrefix() {
        return ("ajp-apr");
    }


    // --------------------------------------  AjpConnectionHandler Inner Class


    protected static class AjpConnectionHandler
            extends AbstractAjpConnectionHandler<Long,AjpAprProcessor>
            implements Handler {

        protected AjpAprProtocol proto;

        public AjpConnectionHandler(AjpAprProtocol proto) {
            this.proto = proto;
        }

        @Override
        protected AbstractProtocol<Long> getProtocol() {
            return proto;
        }

        @Override
        protected Log getLog() {
            return log;
        }

        /**
         * Expected to be used by the handler once the processor is no longer
         * required.
         */
        @Override
        public void release(SocketWrapper<Long> socket,
                Processor<Long> processor, boolean isSocketClosing,
                boolean addToPoller) {
            processor.recycle(isSocketClosing);
            recycledProcessors.offer(processor);
            if (addToPoller) {
                ((AprEndpoint)proto.endpoint).getPoller().add(
                        socket.getSocket().longValue(),
                        proto.endpoint.getKeepAliveTimeout(),
                        true, false);
            }
        }


        @Override
        protected AjpAprProcessor createProcessor() {
            AjpAprProcessor processor = new AjpAprProcessor(proto.packetSize, (AprEndpoint)proto.endpoint);
            processor.setAdapter(proto.adapter);
            processor.setAjpFlush(proto.getAjpFlush());
            processor.setTomcatAuthentication(proto.tomcatAuthentication);
            processor.setTomcatAuthorization(proto.getTomcatAuthorization());
            processor.setRequiredSecret(proto.requiredSecret);
            processor.setKeepAliveTimeout(proto.getKeepAliveTimeout());
            processor.setClientCertProvider(proto.getClientCertProvider());
            processor.setMaxCookieCount(proto.getMaxCookieCount());
            register(processor);
            return processor;
        }
    }
}
