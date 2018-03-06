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

import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.Processor;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.net.AbstractEndpoint;
import org.apache.tomcat.util.net.NioChannel;
import org.apache.tomcat.util.net.NioEndpoint;
import org.apache.tomcat.util.net.NioEndpoint.Handler;
import org.apache.tomcat.util.net.SSLImplementation;
import org.apache.tomcat.util.net.SocketWrapper;


/**
 * Abstract the protocol implementation, including threading, etc.
 * Processor is single threaded and specific to stream-based protocols,
 * will not fit Jk protocols like JNI.
 */
public class AjpNioProtocol extends AbstractAjpProtocol<NioChannel> {
    
    
    private static final Log log = LogFactory.getLog(AjpNioProtocol.class);

    @Override
    protected Log getLog() { return log; }


    @Override
    protected AbstractEndpoint.Handler getHandler() {
        return cHandler;
    }


    // ------------------------------------------------------------ Constructor


    public AjpNioProtocol() {
        endpoint = new NioEndpoint();
        cHandler = new AjpConnectionHandler(this);
        ((NioEndpoint) endpoint).setHandler(cHandler);
        setSoLinger(Constants.DEFAULT_CONNECTION_LINGER);
        setSoTimeout(Constants.DEFAULT_CONNECTION_TIMEOUT);
        setTcpNoDelay(Constants.DEFAULT_TCP_NO_DELAY);
        // AJP does not use Send File
        ((NioEndpoint) endpoint).setUseSendfile(false);
    }

    
    // ----------------------------------------------------- Instance Variables


    /**
     * Connection handler for AJP.
     */
    private AjpConnectionHandler cHandler;


    // ----------------------------------------------------- JMX related methods

    @Override
    protected String getNamePrefix() {
        return ("ajp-nio");
    }


    // --------------------------------------  AjpConnectionHandler Inner Class


    protected static class AjpConnectionHandler
            extends AbstractAjpConnectionHandler<NioChannel, AjpNioProcessor>
            implements Handler {

        protected AjpNioProtocol proto;

        public AjpConnectionHandler(AjpNioProtocol proto) {
            this.proto = proto;
        }

        @Override
        protected AbstractProtocol<NioChannel> getProtocol() {
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
         * Expected to be used by the Poller to release resources on socket
         * close, errors etc.
         */
        @Override
        public void release(SocketChannel socket) {
            if (log.isDebugEnabled()) 
                log.debug("Iterating through our connections to release a socket channel:"+socket);
            boolean released = false;
            Iterator<java.util.Map.Entry<NioChannel, Processor<NioChannel>>> it = connections.entrySet().iterator();
            while (it.hasNext()) {
                java.util.Map.Entry<NioChannel, Processor<NioChannel>> entry = it.next();
                if (entry.getKey().getIOChannel()==socket) {
                    it.remove();
                    Processor<NioChannel> result = entry.getValue();
                    result.recycle(true);
                    unregister(result);
                    released = true;
                    break;
                }
            }
            if (log.isDebugEnabled()) 
                log.debug("Done iterating through our connections to release a socket channel:"+socket +" released:"+released);
        }
        
        /**
         * Expected to be used by the Poller to release resources on socket
         * close, errors etc.
         */
        @Override
        public void release(SocketWrapper<NioChannel> socket) {
            Processor<NioChannel> processor =
                    connections.remove(socket.getSocket());
            if (processor != null) {
                processor.recycle(true);
                recycledProcessors.offer(processor);
            }
        }

        /**
         * Expected to be used by the handler once the processor is no longer
         * required.
         */
        @Override
        public void release(SocketWrapper<NioChannel> socket,
                Processor<NioChannel> processor, boolean isSocketClosing,
                boolean addToPoller) {
            processor.recycle(isSocketClosing);
            recycledProcessors.offer(processor);
            if (addToPoller) {
                // The only time this method is called with addToPoller == true
                // is when the socket is in keep-alive so set the appropriate
                // timeout.
                socket.setTimeout(getProtocol().getKeepAliveTimeout());
                socket.getSocket().getPoller().add(socket.getSocket());
            }
        }


        @Override
        protected AjpNioProcessor createProcessor() {
            AjpNioProcessor processor = new AjpNioProcessor(proto.packetSize, (NioEndpoint)proto.endpoint);
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
