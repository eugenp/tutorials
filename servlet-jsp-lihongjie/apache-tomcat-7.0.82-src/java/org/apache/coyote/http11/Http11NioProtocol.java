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
package org.apache.coyote.http11;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.upgrade.NioProcessor;
import org.apache.coyote.http11.upgrade.servlet31.HttpUpgradeHandler;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.net.AbstractEndpoint;
import org.apache.tomcat.util.net.NioChannel;
import org.apache.tomcat.util.net.NioEndpoint;
import org.apache.tomcat.util.net.NioEndpoint.Handler;
import org.apache.tomcat.util.net.SSLImplementation;
import org.apache.tomcat.util.net.SecureNioChannel;
import org.apache.tomcat.util.net.SocketWrapper;


/**
 * Abstract the protocol implementation, including threading, etc.
 * Processor is single threaded and specific to stream-based protocols,
 * will not fit Jk protocols like JNI.
 *
 * @author Remy Maucherat
 * @author Costin Manolache
 * @author Filip Hanik
 */
public class Http11NioProtocol extends AbstractHttp11JsseProtocol<NioChannel> {

    private static final Log log = LogFactory.getLog(Http11NioProtocol.class);


    @Override
    protected Log getLog() { return log; }


    @Override
    protected AbstractEndpoint.Handler getHandler() {
        return cHandler;
    }


    public Http11NioProtocol() {
        endpoint=new NioEndpoint();
        cHandler = new Http11ConnectionHandler(this);
        ((NioEndpoint) endpoint).setHandler(cHandler);
        setSoLinger(Constants.DEFAULT_CONNECTION_LINGER);
        setSoTimeout(Constants.DEFAULT_CONNECTION_TIMEOUT);
        setTcpNoDelay(Constants.DEFAULT_TCP_NO_DELAY);
    }


    public NioEndpoint getEndpoint() {
        return ((NioEndpoint)endpoint);
    }


    // -------------------- Properties--------------------

    private Http11ConnectionHandler cHandler;

    // -------------------- Pool setup --------------------

    public void setPollerThreadCount(int count) {
        ((NioEndpoint)endpoint).setPollerThreadCount(count);
    }

    public int getPollerThreadCount() {
        return ((NioEndpoint)endpoint).getPollerThreadCount();
    }

    public void setSelectorTimeout(long timeout) {
        ((NioEndpoint)endpoint).setSelectorTimeout(timeout);
    }

    public long getSelectorTimeout() {
        return ((NioEndpoint)endpoint).getSelectorTimeout();
    }

    public void setAcceptorThreadPriority(int threadPriority) {
        ((NioEndpoint)endpoint).setAcceptorThreadPriority(threadPriority);
    }

    public void setPollerThreadPriority(int threadPriority) {
        ((NioEndpoint)endpoint).setPollerThreadPriority(threadPriority);
    }

    public int getAcceptorThreadPriority() {
      return ((NioEndpoint)endpoint).getAcceptorThreadPriority();
    }

    public int getPollerThreadPriority() {
      return ((NioEndpoint)endpoint).getThreadPriority();
    }


    public boolean getUseSendfile() {
        return ((NioEndpoint)endpoint).getUseSendfile();
    }

    public void setUseSendfile(boolean useSendfile) {
        ((NioEndpoint)endpoint).setUseSendfile(useSendfile);
    }

    // -------------------- Tcp setup --------------------
    public void setOomParachute(int oomParachute) {
        ((NioEndpoint)endpoint).setOomParachute(oomParachute);
    }

    // ----------------------------------------------------- JMX related methods

    @Override
    protected String getNamePrefix() {
        return ("http-nio");
    }


    // --------------------  Connection handler --------------------

    protected static class Http11ConnectionHandler
            extends AbstractConnectionHandler<NioChannel,Http11NioProcessor>
            implements Handler {

        protected Http11NioProtocol proto;

        Http11ConnectionHandler(Http11NioProtocol proto) {
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
            return proto.sslImplementation;
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
                if (!processor.isUpgrade()) {
                    recycledProcessors.offer(processor);
                }
            }
        }


        /**
         * Expected to be used by the handler once the processor is no longer
         * required.
         *
         * @param socket
         * @param processor
         * @param isSocketClosing   Not used in HTTP
         * @param addToPoller
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
        protected void initSsl(SocketWrapper<NioChannel> socket,
                Processor<NioChannel> processor) {
            if (proto.isSSLEnabled() &&
                    (proto.sslImplementation != null)
                    && (socket.getSocket() instanceof SecureNioChannel)) {
                SecureNioChannel ch = (SecureNioChannel)socket.getSocket();
                processor.setSslSupport(
                        proto.sslImplementation.getSSLSupport(
                                ch.getSslEngine().getSession()));
            } else {
                processor.setSslSupport(null);
            }

        }

        @Override
        protected void longPoll(SocketWrapper<NioChannel> socket,
                Processor<NioChannel> processor) {

            if (processor.isAsync()) {
                socket.setAsync(true);
            } else {
                // Either:
                //  - this is comet request
                //  - this is an upgraded connection
                //  - the request line/headers have not been completely
                //    read
                socket.getSocket().getPoller().add(socket.getSocket());
            }
        }

        @Override
        public Http11NioProcessor createProcessor() {
            Http11NioProcessor processor = new Http11NioProcessor(
                    proto.getMaxHttpHeaderSize(), proto.getRejectIllegalHeaderName(),
                    (NioEndpoint)proto.endpoint, proto.getMaxTrailerSize(),
                    proto.getAllowedTrailerHeadersAsSet(), proto.getMaxExtensionSize(),
                    proto.getMaxSwallowSize());
            processor.setAdapter(proto.adapter);
            processor.setMaxKeepAliveRequests(proto.getMaxKeepAliveRequests());
            processor.setKeepAliveTimeout(proto.getKeepAliveTimeout());
            processor.setConnectionUploadTimeout(
                    proto.getConnectionUploadTimeout());
            processor.setDisableUploadTimeout(proto.getDisableUploadTimeout());
            processor.setCompressionMinSize(proto.getCompressionMinSize());
            processor.setCompression(proto.getCompression());
            processor.setNoCompressionUserAgents(proto.getNoCompressionUserAgents());
            processor.setCompressableMimeTypes(proto.getCompressableMimeTypes());
            processor.setRestrictedUserAgents(proto.getRestrictedUserAgents());
            processor.setSocketBuffer(proto.getSocketBuffer());
            processor.setMaxSavePostSize(proto.getMaxSavePostSize());
            processor.setServer(proto.getServer());
            processor.setMaxCookieCount(proto.getMaxCookieCount());
            register(processor);
            return processor;
        }

        /**
         * @deprecated  Will be removed in Tomcat 8.0.x.
         */
        @Deprecated
        @Override
        protected Processor<NioChannel> createUpgradeProcessor(
                SocketWrapper<NioChannel> socket,
                org.apache.coyote.http11.upgrade.UpgradeInbound inbound)
                throws IOException {
            return new org.apache.coyote.http11.upgrade.UpgradeNioProcessor(
                    socket, inbound,
                    ((Http11NioProtocol) getProtocol()).getEndpoint().getSelectorPool());
        }
        
        @Override
        protected Processor<NioChannel> createUpgradeProcessor(
                SocketWrapper<NioChannel> socket,
                HttpUpgradeHandler httpUpgradeProcessor)
                throws IOException {
            return new NioProcessor(socket, httpUpgradeProcessor,
                    proto.getEndpoint().getSelectorPool(),
                    proto.getUpgradeAsyncWriteBufferSize());
        }
    }
}
