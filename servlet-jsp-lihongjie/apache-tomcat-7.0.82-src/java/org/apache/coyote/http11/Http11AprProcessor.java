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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Set;

import org.apache.coyote.ActionCode;
import org.apache.coyote.ErrorState;
import org.apache.coyote.RequestInfo;
import org.apache.coyote.http11.filters.BufferedInputFilter;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.jni.Address;
import org.apache.tomcat.jni.SSL;
import org.apache.tomcat.jni.SSLSocket;
import org.apache.tomcat.jni.Sockaddr;
import org.apache.tomcat.jni.Socket;
import org.apache.tomcat.util.ExceptionUtils;
import org.apache.tomcat.util.net.AbstractEndpoint.Handler.SocketState;
import org.apache.tomcat.util.net.AprEndpoint;
import org.apache.tomcat.util.net.SSLSupport;
import org.apache.tomcat.util.net.SendfileKeepAliveState;
import org.apache.tomcat.util.net.SocketStatus;
import org.apache.tomcat.util.net.SocketWrapper;


/**
 * Processes HTTP requests.
 *
 * @author Remy Maucherat
 */
public class Http11AprProcessor extends AbstractHttp11Processor<Long> {


    private static final Log log = LogFactory.getLog(Http11AprProcessor.class);
    @Override
    protected Log getLog() {
        return log;
    }

    // ----------------------------------------------------------- Constructors


    public Http11AprProcessor(int headerBufferSize, boolean rejectIllegalHeaderName,
            AprEndpoint endpoint, int maxTrailerSize, Set<String> allowedTrailerHeaders,
            int maxExtensionSize, int maxSwallowSize) {

        super(endpoint);

        inputBuffer = new InternalAprInputBuffer(request, headerBufferSize, rejectIllegalHeaderName);
        request.setInputBuffer(inputBuffer);

        outputBuffer = new InternalAprOutputBuffer(response, headerBufferSize);
        response.setOutputBuffer(outputBuffer);

        initializeFilters(maxTrailerSize, allowedTrailerHeaders, maxExtensionSize, maxSwallowSize);
    }


    // ----------------------------------------------------- Instance Variables


    /**
     * Input.
     */
    protected InternalAprInputBuffer inputBuffer = null;


    /**
     * Output.
     */
    protected InternalAprOutputBuffer outputBuffer = null;


    /**
     * Sendfile data.
     */
    protected AprEndpoint.SendfileData sendfileData = null;


    /**
     * When client certificate information is presented in a form other than
     * instances of {@link java.security.cert.X509Certificate} it needs to be
     * converted before it can be used and this property controls which JSSE
     * provider is used to perform the conversion. For example it is used with
     * the AJP connectors, the HTTP APR connector and with the
     * {@link org.apache.catalina.valves.SSLValve}. If not specified, the
     * default provider will be used.
     */
    protected String clientCertProvider = null;
    public String getClientCertProvider() { return clientCertProvider; }
    public void setClientCertProvider(String s) { this.clientCertProvider = s; }


    // --------------------------------------------------------- Public Methods


    /**
     * Process pipelined HTTP requests using the specified input and output
     * streams.
     *
     * @throws IOException error during an I/O operation
     */
    @Override
    public SocketState event(SocketStatus status)
        throws IOException {

        RequestInfo rp = request.getRequestProcessor();

        try {
            rp.setStage(org.apache.coyote.Constants.STAGE_SERVICE);
            if (!getAdapter().event(request, response, status)) {
                setErrorState(ErrorState.CLOSE_NOW, null);
            }
        } catch (InterruptedIOException e) {
            setErrorState(ErrorState.CLOSE_NOW, e);
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
            // 500 - Internal Server Error
            response.setStatus(500);
            setErrorState(ErrorState.CLOSE_NOW, t);
            getAdapter().log(request, response, 0);
            log.error(sm.getString("http11processor.request.process"), t);
        }

        rp.setStage(org.apache.coyote.Constants.STAGE_ENDED);

        if (getErrorState().isError() || status==SocketStatus.STOP) {
            return SocketState.CLOSED;
        } else if (!comet) {
            inputBuffer.nextRequest();
            outputBuffer.nextRequest();
            return SocketState.OPEN;
        } else {
            return SocketState.LONG;
        }
    }

    @Override
    protected boolean disableKeepAlive() {
        return false;
    }


    @Override
    protected void setRequestLineReadTimeout() throws IOException {
        // Timeouts while in the poller are handled entirely by the poller
        // Only need to be concerned with socket timeouts

        // APR uses simulated blocking so if some request line data is present
        // then it must all be presented (with the normal socket timeout).

        // When entering the processing loop for the first time there will
        // always be some data to read so the keep-alive timeout is not required

        // For the second and subsequent executions of the processing loop, if
        // there is no request line data present then no further data will be
        // read from the socket. If there is request line data present then it
        // must all be presented (with the normal socket timeout)

        // When the socket is created it is given the correct timeout.
        // sendfile may change the timeout but will restore it
        // This processor may change the timeout for uploads but will restore it

        // NO-OP
    }


    @Override
    protected boolean handleIncompleteRequestLineRead() {
        // This means that no data is available right now
        // (long keepalive), so that the processor should be recycled
        // and the method should return true
        openSocket = true;
        return true;
    }


    @Override
    protected void setSocketTimeout(int timeout) {
        Socket.timeoutSet(socketWrapper.getSocket().longValue(), timeout * 1000);
    }


    @Override
    protected void setCometTimeouts(SocketWrapper<Long> socketWrapper) {
        // NO-OP for APR/native
    }


    @Override
    protected boolean breakKeepAliveLoop(SocketWrapper<Long> socketWrapper) {
        openSocket = keepAlive;
        // Do sendfile as needed: add socket to sendfile and end
        if (sendfileData != null && !getErrorState().isError()) {
            sendfileData.socket = socketWrapper.getSocket().longValue();
            if (keepAlive) {
                if (getInputBuffer().available() == 0) {
                    sendfileData.keepAliveState = SendfileKeepAliveState.OPEN;
                } else {
                    sendfileData.keepAliveState = SendfileKeepAliveState.PIPELINED;
                }
            } else {
                sendfileData.keepAliveState = SendfileKeepAliveState.NONE;
            }
            switch (((AprEndpoint)endpoint).getSendfile().add(sendfileData)) {
            case DONE:
                return false;
            case PENDING:
                // The sendfile Poller will add the socket to the main
                // Poller once sendfile processing is complete
                sendfileInProgress = true;
                return true;
            case ERROR:
                // Something went wrong.
                // Close the connection. Too late to set status code.
                if (log.isDebugEnabled()) {
                    log.debug(sm.getString(
                            "http11processor.sendfile.error"));
                }
                setErrorState(ErrorState.CLOSE_NOW, null);
                return true;
            }
        }
        return false;
    }


    @Override
    protected void resetTimeouts() {
        // NOOP for APR
    }


    @Override
    public void recycleInternal() {
        socketWrapper = null;
        sendfileData = null;
    }


    @Override
    public void setSslSupport(SSLSupport sslSupport) {
        // NOOP for APR
    }

    // ----------------------------------------------------- ActionHook Methods


    /**
     * Send an action to the connector.
     *
     * @param actionCode Type of the action
     * @param param Action parameter
     */
    @Override
    @SuppressWarnings("incomplete-switch") // Other cases are handled by action()
    public void actionInternal(ActionCode actionCode, Object param) {

        long socketRef = socketWrapper.getSocket().longValue();

        switch (actionCode) {
        case REQ_HOST_ADDR_ATTRIBUTE: {
            // Get remote host address
            if (remoteAddr == null && (socketRef != 0)) {
                try {
                    long sa = Address.get(Socket.APR_REMOTE, socketRef);
                    remoteAddr = Address.getip(sa);
                } catch (Exception e) {
                    log.warn(sm.getString("http11processor.socket.info"), e);
                }
            }
            request.remoteAddr().setString(remoteAddr);
            break;
        }
        case REQ_LOCAL_NAME_ATTRIBUTE: {
            // Get local host name
            if (localName == null && (socketRef != 0)) {
                try {
                    long sa = Address.get(Socket.APR_LOCAL, socketRef);
                    localName = Address.getnameinfo(sa, 0);
                } catch (Exception e) {
                    log.warn(sm.getString("http11processor.socket.info"), e);
                }
            }
            request.localName().setString(localName);
            break;
        }
        case REQ_HOST_ATTRIBUTE: {
            // Get remote host name
            if (remoteHost == null && (socketRef != 0)) {
                try {
                    long sa = Address.get(Socket.APR_REMOTE, socketRef);
                    remoteHost = Address.getnameinfo(sa, 0);
                    if (remoteHost == null) {
                        remoteHost = Address.getip(sa);
                    }
                } catch (Exception e) {
                    log.warn(sm.getString("http11processor.socket.info"), e);
                }
            }
            request.remoteHost().setString(remoteHost);
            break;
        }
        case REQ_LOCAL_ADDR_ATTRIBUTE: {
            // Get local host address
            if (localAddr == null && (socketRef != 0)) {
                try {
                    long sa = Address.get(Socket.APR_LOCAL, socketRef);
                    localAddr = Address.getip(sa);
                } catch (Exception e) {
                    log.warn(sm.getString("http11processor.socket.info"), e);
                }
            }

            request.localAddr().setString(localAddr);
            break;
        }
        case REQ_REMOTEPORT_ATTRIBUTE: {
            // Get remote port
            if (remotePort == -1 && (socketRef != 0)) {
                try {
                    long sa = Address.get(Socket.APR_REMOTE, socketRef);
                    Sockaddr addr = Address.getInfo(sa);
                    remotePort = addr.port;
                } catch (Exception e) {
                    log.warn(sm.getString("http11processor.socket.info"), e);
                }
            }
            request.setRemotePort(remotePort);
            break;
        }
        case REQ_LOCALPORT_ATTRIBUTE: {
            // Get local port
            if (localPort == -1 && (socketRef != 0)) {
                try {
                    long sa = Address.get(Socket.APR_LOCAL, socketRef);
                    Sockaddr addr = Address.getInfo(sa);
                    localPort = addr.port;
                } catch (Exception e) {
                    log.warn(sm.getString("http11processor.socket.info"), e);
                }
            }
            request.setLocalPort(localPort);
            break;
        }
        case REQ_SSL_ATTRIBUTE: {
            if (endpoint.isSSLEnabled() && (socketRef != 0)) {
                try {
                    // Cipher suite
                    Object sslO = SSLSocket.getInfoS(socketRef, SSL.SSL_INFO_CIPHER);
                    if (sslO != null) {
                        request.setAttribute(SSLSupport.CIPHER_SUITE_KEY, sslO);
                    }
                    // Get client certificate and the certificate chain if present
                    // certLength == -1 indicates an error unless TLS session tickets
                    // are in use in which case OpenSSL won't store the chain in the
                    // ticket.
                    int certLength = SSLSocket.getInfoI(socketRef, SSL.SSL_INFO_CLIENT_CERT_CHAIN);
                    byte[] clientCert = SSLSocket.getInfoB(socketRef, SSL.SSL_INFO_CLIENT_CERT);
                    X509Certificate[] certs = null;

                    if (clientCert != null) {
                        if (certLength < 0) {
                            certLength = 0;
                        }
                        certs = new X509Certificate[certLength + 1];
                        CertificateFactory cf;
                        if (clientCertProvider == null) {
                            cf = CertificateFactory.getInstance("X.509");
                        } else {
                            cf = CertificateFactory.getInstance("X.509",
                                    clientCertProvider);
                        }
                        certs[0] = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(clientCert));
                        for (int i = 0; i < certLength; i++) {
                            byte[] data = SSLSocket.getInfoB(socketRef, SSL.SSL_INFO_CLIENT_CERT_CHAIN + i);
                            certs[i+1] = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(data));
                        }
                    }
                    if (certs != null) {
                        request.setAttribute(SSLSupport.CERTIFICATE_KEY, certs);
                    }
                    // User key size
                    sslO = Integer.valueOf(SSLSocket.getInfoI(socketRef,
                            SSL.SSL_INFO_CIPHER_USEKEYSIZE));
                    request.setAttribute(SSLSupport.KEY_SIZE_KEY, sslO);

                    // SSL session ID
                    sslO = SSLSocket.getInfoS(socketRef, SSL.SSL_INFO_SESSION_ID);
                    if (sslO != null) {
                        request.setAttribute(SSLSupport.SESSION_ID_KEY, sslO);
                    }
                    sslO = SSLSocket.getInfoS(socketRef, SSL.SSL_INFO_PROTOCOL);
                    if (sslO != null) {
                        request.setAttribute
                        (SSLSupport.PROTOCOL_VERSION_KEY, sslO);
                    }
                    //TODO provide a hook to enable the SSL session to be
                    // invalidated. Set AprEndpoint.SESSION_MGR req attr
                } catch (Exception e) {
                    log.warn(sm.getString("http11processor.socket.ssl"), e);
                }
            }
            break;
        }
        case REQ_SSL_CERTIFICATE: {
            if (endpoint.isSSLEnabled() && (socketRef != 0)) {
                // Consume and buffer the request body, so that it does not
                // interfere with the client's handshake messages
                InputFilter[] inputFilters = inputBuffer.getFilters();
                ((BufferedInputFilter) inputFilters[Constants.BUFFERED_FILTER]).setLimit(maxSavePostSize);
                inputBuffer.addActiveFilter(inputFilters[Constants.BUFFERED_FILTER]);
                try {
                    // Configure connection to require a certificate
                    SSLSocket.setVerify(socketRef, SSL.SSL_CVERIFY_REQUIRE,
                            ((AprEndpoint)endpoint).getSSLVerifyDepth());
                    // Renegotiate certificates
                    if (SSLSocket.renegotiate(socketRef) == 0) {
                        // Don't look for certs unless we know renegotiation worked.
                        // Get client certificate and the certificate chain if present
                        // certLength == -1 indicates an error
                        int certLength = SSLSocket.getInfoI(socketRef,SSL.SSL_INFO_CLIENT_CERT_CHAIN);
                        byte[] clientCert = SSLSocket.getInfoB(socketRef, SSL.SSL_INFO_CLIENT_CERT);
                        X509Certificate[] certs = null;
                        if (clientCert != null && certLength > -1) {
                            certs = new X509Certificate[certLength + 1];
                            CertificateFactory cf = CertificateFactory.getInstance("X.509");
                            certs[0] = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(clientCert));
                            for (int i = 0; i < certLength; i++) {
                                byte[] data = SSLSocket.getInfoB(socketRef, SSL.SSL_INFO_CLIENT_CERT_CHAIN + i);
                                certs[i+1] = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(data));
                            }
                        }
                        if (certs != null) {
                            request.setAttribute(SSLSupport.CERTIFICATE_KEY, certs);
                        }
                    }
                } catch (Exception e) {
                    log.warn(sm.getString("http11processor.socket.ssl"), e);
                }
            }
            break;
        }
        case AVAILABLE: {
            request.setAvailable(inputBuffer.available());
            break;
        }
        case COMET_BEGIN: {
            comet = true;
            break;
        }
        case COMET_END: {
            comet = false;
            break;
        }
        case COMET_CLOSE: {
            ((AprEndpoint)endpoint).processSocketAsync(this.socketWrapper,
                    SocketStatus.OPEN_READ);
            break;
        }
        case COMET_SETTIMEOUT: {
            //no op
            break;
        }
        case ASYNC_COMPLETE: {
            if (asyncStateMachine.asyncComplete()) {
                ((AprEndpoint)endpoint).processSocketAsync(this.socketWrapper,
                        SocketStatus.OPEN_READ);
            }
            break;
        }
        case ASYNC_SETTIMEOUT: {
            if (param==null) {
                return;
            }
            long timeout = ((Long)param).longValue();
            socketWrapper.setTimeout(timeout);
            break;
        }
        case ASYNC_DISPATCH: {
            if (asyncStateMachine.asyncDispatch()) {
                ((AprEndpoint)endpoint).processSocketAsync(this.socketWrapper,
                        SocketStatus.OPEN_READ);
            }
            break;
        }
        }

    }


    // ------------------------------------------------------ Protected Methods


    @Override
    protected void prepareRequestInternal() {
        sendfileData = null;
    }

    @Override
    protected boolean prepareSendfile(OutputFilter[] outputFilters) {
        String fileName = (String) request.getAttribute(
                org.apache.coyote.Constants.SENDFILE_FILENAME_ATTR);
        if (fileName != null) {
            // No entity body sent here
            outputBuffer.addActiveFilter(outputFilters[Constants.VOID_FILTER]);
            contentDelimitation = true;
            sendfileData = new AprEndpoint.SendfileData();
            sendfileData.fileName = fileName;
            sendfileData.start = ((Long) request.getAttribute(
                    org.apache.coyote.Constants.SENDFILE_FILE_START_ATTR)).longValue();
            sendfileData.end = ((Long) request.getAttribute(
                    org.apache.coyote.Constants.SENDFILE_FILE_END_ATTR)).longValue();
            return true;
        }
        return false;
    }

    @Override
    protected AbstractInputBuffer<Long> getInputBuffer() {
        return inputBuffer;
    }

    @Override
    protected AbstractOutputBuffer<Long> getOutputBuffer() {
        return outputBuffer;
    }
}
