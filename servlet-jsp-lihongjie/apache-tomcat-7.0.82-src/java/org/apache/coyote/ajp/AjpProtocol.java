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

import java.net.Socket;

import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.Processor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.net.AbstractEndpoint;
import org.apache.tomcat.util.net.JIoEndpoint;
import org.apache.tomcat.util.net.JIoEndpoint.Handler;
import org.apache.tomcat.util.net.SSLImplementation;
import org.apache.tomcat.util.net.SocketWrapper;


/**
 * Abstract the protocol implementation, including threading, etc.
 * Processor is single threaded and specific to stream-based protocols,
 * will not fit Jk protocols like JNI.
 *
 * @author Remy Maucherat
 * @author Costin Manolache
 */
public class AjpProtocol extends AbstractAjpProtocol<Socket> {
    
    
    private static final Log log = LogFactory.getLog(AjpProtocol.class);

    @Override
    protected Log getLog() { return log; }


    @Override
    protected AbstractEndpoint.Handler getHandler() {
        return cHandler;
    }


    // ------------------------------------------------------------ Constructor


    public AjpProtocol() {
        endpoint = new JIoEndpoint();
        cHandler = new AjpConnectionHandler(this);
        ((JIoEndpoint) endpoint).setHandler(cHandler);
        setSoLinger(Constants.DEFAULT_CONNECTION_LINGER);
        setSoTimeout(Constants.DEFAULT_CONNECTION_TIMEOUT);
        setTcpNoDelay(Constants.DEFAULT_TCP_NO_DELAY);
    }

    
    // ----------------------------------------------------- Instance Variables

    
    /**
     * Connection handler for AJP.
     */
    private AjpConnectionHandler cHandler;


    // ----------------------------------------------------- JMX related methods

    @Override
    protected String getNamePrefix() {
        return ("ajp-bio");
    }


    // --------------------------------------  AjpConnectionHandler Inner Class


    protected static class AjpConnectionHandler
            extends AbstractAjpConnectionHandler<Socket,AjpProcessor>
            implements Handler {

        protected AjpProtocol proto;

        public AjpConnectionHandler(AjpProtocol proto) {
            this.proto = proto;
        }
        
        @Override
        protected AbstractProtocol<Socket> getProtocol() {
            return proto;
        }

        @Override
        protected Log getLog() {
            return log;
        }

        @Override
        public SSLImplementation getSslImplementation() {
            // AJP does not support SSL
            return null;
        }

        /**
         * Expected to be used by the handler once the processor is no longer
         * required.
         * 
         * @param socket            Ignored for BIO
         * @param processor
         * @param isSocketClosing
         * @param addToPoller       Ignored for BIO
         */
        @Override
        public void release(SocketWrapper<Socket> socket,
                Processor<Socket> processor, boolean isSocketClosing,
                boolean addToPoller) {
            processor.recycle(isSocketClosing);
            recycledProcessors.offer(processor);
        }


        @Override
        protected AjpProcessor createProcessor() {
            AjpProcessor processor = new AjpProcessor(proto.packetSize, (JIoEndpoint)proto.endpoint);
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
