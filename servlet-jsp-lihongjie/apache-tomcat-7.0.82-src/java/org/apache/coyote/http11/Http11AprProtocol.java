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

import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.upgrade.AprProcessor;
import org.apache.coyote.http11.upgrade.servlet31.HttpUpgradeHandler;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.net.AbstractEndpoint;
import org.apache.tomcat.util.net.AprEndpoint;
import org.apache.tomcat.util.net.AprEndpoint.Handler;
import org.apache.tomcat.util.net.AprEndpoint.Poller;
import org.apache.tomcat.util.net.SocketStatus;
import org.apache.tomcat.util.net.SocketWrapper;


/**
 * Abstract the protocol implementation, including threading, etc.
 * Processor is single threaded and specific to stream-based protocols,
 * will not fit Jk protocols like JNI.
 *
 * @author Remy Maucherat
 * @author Costin Manolache
 */
public class Http11AprProtocol extends AbstractHttp11Protocol<Long> {

    private static final Log log = LogFactory.getLog(Http11AprProtocol.class);


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


    public Http11AprProtocol() {
        endpoint = new AprEndpoint();
        cHandler = new Http11ConnectionHandler(this);
        ((AprEndpoint) endpoint).setHandler(cHandler);
        setSoLinger(Constants.DEFAULT_CONNECTION_LINGER);
        setSoTimeout(Constants.DEFAULT_CONNECTION_TIMEOUT);
        setTcpNoDelay(Constants.DEFAULT_TCP_NO_DELAY);
    }

    private final Http11ConnectionHandler cHandler;

    public boolean getUseSendfile() { return ((AprEndpoint)endpoint).getUseSendfile(); }
    public void setUseSendfile(boolean useSendfile) { ((AprEndpoint)endpoint).setUseSendfile(useSendfile); }

    public int getPollTime() { return ((AprEndpoint)endpoint).getPollTime(); }
    public void setPollTime(int pollTime) { ((AprEndpoint)endpoint).setPollTime(pollTime); }

    public void setPollerSize(int pollerSize) { endpoint.setMaxConnections(pollerSize); }
    public int getPollerSize() { return endpoint.getMaxConnections(); }

    public int getSendfileSize() { return ((AprEndpoint)endpoint).getSendfileSize(); }
    public void setSendfileSize(int sendfileSize) { ((AprEndpoint)endpoint).setSendfileSize(sendfileSize); }
    
    public void setSendfileThreadCount(int sendfileThreadCount) { ((AprEndpoint)endpoint).setSendfileThreadCount(sendfileThreadCount); }
    public int getSendfileThreadCount() { return ((AprEndpoint)endpoint).getSendfileThreadCount(); }

    public boolean getDeferAccept() { return ((AprEndpoint)endpoint).getDeferAccept(); }
    public void setDeferAccept(boolean deferAccept) { ((AprEndpoint)endpoint).setDeferAccept(deferAccept); }

    // --------------------  SSL related properties --------------------

    /**
     * SSL protocol.
     */
    public String getSSLProtocol() { return ((AprEndpoint)endpoint).getSSLProtocol(); }
    public void setSSLProtocol(String SSLProtocol) { ((AprEndpoint)endpoint).setSSLProtocol(SSLProtocol); }


    /**
     * SSL password (if a cert is encrypted, and no password has been provided, a callback
     * will ask for a password).
     */
    public String getSSLPassword() { return ((AprEndpoint)endpoint).getSSLPassword(); }
    public void setSSLPassword(String SSLPassword) { ((AprEndpoint)endpoint).setSSLPassword(SSLPassword); }


    /**
     * SSL cipher suite.
     */
    public String getSSLCipherSuite() { return ((AprEndpoint)endpoint).getSSLCipherSuite(); }
    public void setSSLCipherSuite(String SSLCipherSuite) { ((AprEndpoint)endpoint).setSSLCipherSuite(SSLCipherSuite); }


    /**
     * SSL honor cipher order.
     *
     * Set to <code>true</code> to enforce the <i>server's</i> cipher order
     * instead of the default which is to allow the client to choose a
     * preferred cipher.
     */
    public boolean getSSLHonorCipherOrder() { return ((AprEndpoint)endpoint).getSSLHonorCipherOrder(); }
    public void setSSLHonorCipherOrder(boolean SSLHonorCipherOrder) { ((AprEndpoint)endpoint).setSSLHonorCipherOrder(SSLHonorCipherOrder); }


    /**
     * SSL certificate file.
     */
    public String getSSLCertificateFile() { return ((AprEndpoint)endpoint).getSSLCertificateFile(); }
    public void setSSLCertificateFile(String SSLCertificateFile) { ((AprEndpoint)endpoint).setSSLCertificateFile(SSLCertificateFile); }


    /**
     * SSL certificate key file.
     */
    public String getSSLCertificateKeyFile() { return ((AprEndpoint)endpoint).getSSLCertificateKeyFile(); }
    public void setSSLCertificateKeyFile(String SSLCertificateKeyFile) { ((AprEndpoint)endpoint).setSSLCertificateKeyFile(SSLCertificateKeyFile); }


    /**
     * SSL certificate chain file.
     */
    public String getSSLCertificateChainFile() { return ((AprEndpoint)endpoint).getSSLCertificateChainFile(); }
    public void setSSLCertificateChainFile(String SSLCertificateChainFile) { ((AprEndpoint)endpoint).setSSLCertificateChainFile(SSLCertificateChainFile); }


    /**
     * SSL CA certificate path.
     */
    public String getSSLCACertificatePath() { return ((AprEndpoint)endpoint).getSSLCACertificatePath(); }
    public void setSSLCACertificatePath(String SSLCACertificatePath) { ((AprEndpoint)endpoint).setSSLCACertificatePath(SSLCACertificatePath); }


    /**
     * SSL CA certificate file.
     */
    public String getSSLCACertificateFile() { return ((AprEndpoint)endpoint).getSSLCACertificateFile(); }
    public void setSSLCACertificateFile(String SSLCACertificateFile) { ((AprEndpoint)endpoint).setSSLCACertificateFile(SSLCACertificateFile); }


    /**
     * SSL CA revocation path.
     */
    public String getSSLCARevocationPath() { return ((AprEndpoint)endpoint).getSSLCARevocationPath(); }
    public void setSSLCARevocationPath(String SSLCARevocationPath) { ((AprEndpoint)endpoint).setSSLCARevocationPath(SSLCARevocationPath); }


    /**
     * SSL CA revocation file.
     */
    public String getSSLCARevocationFile() { return ((AprEndpoint)endpoint).getSSLCARevocationFile(); }
    public void setSSLCARevocationFile(String SSLCARevocationFile) { ((AprEndpoint)endpoint).setSSLCARevocationFile(SSLCARevocationFile); }


    /**
     * SSL verify client.
     */
    public String getSSLVerifyClient() { return ((AprEndpoint)endpoint).getSSLVerifyClient(); }
    public void setSSLVerifyClient(String SSLVerifyClient) { ((AprEndpoint)endpoint).setSSLVerifyClient(SSLVerifyClient); }


    /**
     * SSL verify depth.
     */
    public int getSSLVerifyDepth() { return ((AprEndpoint)endpoint).getSSLVerifyDepth(); }
    public void setSSLVerifyDepth(int SSLVerifyDepth) { ((AprEndpoint)endpoint).setSSLVerifyDepth(SSLVerifyDepth); }
    
    /**
     * Disable SSL compression.
     */
    public boolean getSSLDisableCompression() { return ((AprEndpoint)endpoint).getSSLDisableCompression(); }
    public void setSSLDisableCompression(boolean disable) { ((AprEndpoint)endpoint).setSSLDisableCompression(disable); }

    // ----------------------------------------------------- JMX related methods

    @Override
    protected String getNamePrefix() {
        return ("http-apr");
    }


    // --------------------  Connection handler --------------------

    protected static class Http11ConnectionHandler
            extends AbstractConnectionHandler<Long,Http11AprProcessor> implements Handler {
        
        protected Http11AprProtocol proto;
        
        Http11ConnectionHandler(Http11AprProtocol proto) {
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
        
        @Override
        public void recycle() {
            recycledProcessors.clear();
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
        public void release(SocketWrapper<Long> socket,
                Processor<Long> processor, boolean isSocketClosing,
                boolean addToPoller) {
            processor.recycle(isSocketClosing);
            recycledProcessors.offer(processor);
            if (addToPoller && proto.endpoint.isRunning()) {
                ((AprEndpoint)proto.endpoint).getPoller().add(
                        socket.getSocket().longValue(),
                        proto.endpoint.getKeepAliveTimeout(), true, false);
            }
        }

        @Override
        protected void initSsl(SocketWrapper<Long> socket,
                Processor<Long> processor) {
            // NOOP for APR
        }

        @SuppressWarnings("deprecation") // Inbound/Outbound based upgrade
        @Override
        protected void longPoll(SocketWrapper<Long> socket,
                Processor<Long> processor) {

            if (processor.isAsync()) {
                // Async
                socket.setAsync(true);
            } else if (processor.isComet()) {
                // Comet
                if (proto.endpoint.isRunning()) {
                    socket.setComet(true);
                    ((AprEndpoint) proto.endpoint).getPoller().add(
                            socket.getSocket().longValue(),
                            proto.endpoint.getSoTimeout(), true, false);
                } else {
                    // Process a STOP directly
                    ((AprEndpoint) proto.endpoint).processSocket(
                            socket.getSocket().longValue(),
                            SocketStatus.STOP);
                }
            } else if (processor.isUpgrade()) {
                // Upgraded
                Poller p = ((AprEndpoint) proto.endpoint).getPoller();
                if (p == null) {
                    // Connector has been stopped
                    release(socket, processor, true, false);
                } else {
                    p.add(socket.getSocket().longValue(), -1, true, false);
                }
            } else {
                // Tomcat 7 proprietary upgrade
                ((AprEndpoint) proto.endpoint).getPoller().add(
                        socket.getSocket().longValue(),
                        processor.getUpgradeInbound().getReadTimeout(),
                        true, false);
            }
        }

        @Override
        protected Http11AprProcessor createProcessor() {
            Http11AprProcessor processor = new Http11AprProcessor(
                    proto.getMaxHttpHeaderSize(), proto.getRejectIllegalHeaderName(),
                    (AprEndpoint)proto.endpoint, proto.getMaxTrailerSize(),
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
            processor.setClientCertProvider(proto.getClientCertProvider());
            processor.setMaxCookieCount(proto.getMaxCookieCount());
            register(processor);
            return processor;
        }

        /**
         * @deprecated  Will be removed in Tomcat 8.0.x.
         */
        @Deprecated
        @Override
        protected Processor<Long> createUpgradeProcessor(
                SocketWrapper<Long> socket,
                org.apache.coyote.http11.upgrade.UpgradeInbound inbound)
                throws IOException {
            return new org.apache.coyote.http11.upgrade.UpgradeAprProcessor(
                    socket, inbound);
        }
        
        @Override
        protected Processor<Long> createUpgradeProcessor(
                SocketWrapper<Long> socket,
                HttpUpgradeHandler httpUpgradeProcessor)
                throws IOException {
            return new AprProcessor(socket, httpUpgradeProcessor,
                    (AprEndpoint) proto.endpoint,
                    proto.getUpgradeAsyncWriteBufferSize());
        }
    }
}
