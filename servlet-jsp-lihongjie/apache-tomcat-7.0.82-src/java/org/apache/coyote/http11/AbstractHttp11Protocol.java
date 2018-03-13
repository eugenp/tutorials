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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.coyote.AbstractProtocol;
import org.apache.tomcat.util.res.StringManager;

public abstract class AbstractHttp11Protocol<S> extends AbstractProtocol<S> {

    /**
     * The string manager for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);


    @Override
    protected String getProtocolName() {
        return "Http";
    }


    // ------------------------------------------------ HTTP specific properties
    // ------------------------------------------ managed in the ProtocolHandler

    private boolean rejectIllegalHeaderName = false;
    /**
     * If an HTTP request is received that contains an illegal header name (i.e.
     * the header name is not a token) will the request be rejected (with a 400
     * response) or will the illegal header be ignored.
     *
     * @return {@code true} if the request will be rejected or {@code false} if
     *         the header will be ignored
     */
    public boolean getRejectIllegalHeaderName() { return rejectIllegalHeaderName; }
    /**
     * If an HTTP request is received that contains an illegal header name (i.e.
     * the header name is not a token) should the request be rejected (with a
     * 400 response) or should the illegal header be ignored.
     *
     * @param rejectIllegalHeaderName   {@code true} to reject requests with
     *                                  illegal header names, {@code false} to
     *                                  ignore the header
     */
    public void setRejectIllegalHeaderName(boolean rejectIllegalHeaderName) {
        this.rejectIllegalHeaderName = rejectIllegalHeaderName;
    }


    private int socketBuffer = 9000;
    public int getSocketBuffer() { return socketBuffer; }
    public void setSocketBuffer(int socketBuffer) {
        this.socketBuffer = socketBuffer;
    }


    /**
     * Maximum size of the post which will be saved when processing certain
     * requests, such as a POST.
     */
    private int maxSavePostSize = 4 * 1024;
    public int getMaxSavePostSize() { return maxSavePostSize; }
    public void setMaxSavePostSize(int valueI) { maxSavePostSize = valueI; }


    /**
     * Maximum size of the HTTP message header.
     */
    private int maxHttpHeaderSize = 8 * 1024;
    public int getMaxHttpHeaderSize() { return maxHttpHeaderSize; }
    public void setMaxHttpHeaderSize(int valueI) { maxHttpHeaderSize = valueI; }


    /**
     * Specifies a different (usually  longer) connection timeout during data
     * upload.
     */
    private int connectionUploadTimeout = 300000;
    public int getConnectionUploadTimeout() { return connectionUploadTimeout; }
    public void setConnectionUploadTimeout(int i) {
        connectionUploadTimeout = i;
    }


    /**
     * If true, the connectionUploadTimeout will be ignored and the regular
     * socket timeout will be used for the full duration of the connection.
     */
    private boolean disableUploadTimeout = true;
    public boolean getDisableUploadTimeout() { return disableUploadTimeout; }
    public void setDisableUploadTimeout(boolean isDisabled) {
        disableUploadTimeout = isDisabled;
    }


    /**
     * Integrated compression support.
     */
    private String compression = "off";
    public String getCompression() { return compression; }
    public void setCompression(String valueS) { compression = valueS; }


    private String noCompressionUserAgents = null;
    public String getNoCompressionUserAgents() {
        return noCompressionUserAgents;
    }
    public void setNoCompressionUserAgents(String valueS) {
        noCompressionUserAgents = valueS;
    }


    private String compressibleMimeTypes = "text/html,text/xml,text/plain,text/css,text/javascript,application/javascript";
    @Deprecated
    public String getCompressableMimeType() {
        return getCompressibleMimeType();
    }
    @Deprecated
    public void setCompressableMimeType(String valueS) {
        setCompressibleMimeType(valueS);
    }
    @Deprecated
    public String getCompressableMimeTypes() {
        return getCompressibleMimeType();
    }
    @Deprecated
    public void setCompressableMimeTypes(String valueS) {
        setCompressibleMimeType(valueS);
    }
    public String getCompressibleMimeType() { return compressibleMimeTypes; }
    public void setCompressibleMimeType(String valueS) {
        compressibleMimeTypes = valueS;
    }


    private int compressionMinSize = 2048;
    public int getCompressionMinSize() { return compressionMinSize; }
    public void setCompressionMinSize(int valueI) {
        compressionMinSize = valueI;
    }


    /**
     * Regular expression that defines the User agents which should be
     * restricted to HTTP/1.0 support.
     */
    private String restrictedUserAgents = null;
    public String getRestrictedUserAgents() { return restrictedUserAgents; }
    public void setRestrictedUserAgents(String valueS) {
        restrictedUserAgents = valueS;
    }


    /**
     * Server header.
     */
    private String server;
    public String getServer() { return server; }
    public void setServer( String server ) {
        this.server = server;
    }


    /**
     * Maximum size of trailing headers in bytes
     */
    private int maxTrailerSize = 8192;
    public int getMaxTrailerSize() { return maxTrailerSize; }
    public void setMaxTrailerSize(int maxTrailerSize) {
        this.maxTrailerSize = maxTrailerSize;
    }


    /**
     * Maximum size of extension information in chunked encoding
     */
    private int maxExtensionSize = 8192;
    public int getMaxExtensionSize() { return maxExtensionSize; }
    public void setMaxExtensionSize(int maxExtensionSize) {
        this.maxExtensionSize = maxExtensionSize;
    }


    /**
     * Maximum amount of request body to swallow.
     */
    private int maxSwallowSize = 2 * 1024 * 1024;
    public int getMaxSwallowSize() { return maxSwallowSize; }
    public void setMaxSwallowSize(int maxSwallowSize) {
        this.maxSwallowSize = maxSwallowSize;
    }


    /**
     * This field indicates if the protocol is treated as if it is secure. This
     * normally means https is being used but can be used to fake https e.g
     * behind a reverse proxy.
     */
    private boolean secure;
    public boolean getSecure() { return secure; }
    public void setSecure(boolean b) {
        secure = b;
    }


    /**
     * The size of the buffer used by the ServletOutputStream when performing
     * delayed asynchronous writes using HTTP upgraded connections.
     */
    private int upgradeAsyncWriteBufferSize = 8192;
    public int getUpgradeAsyncWriteBufferSize() { return upgradeAsyncWriteBufferSize; }
    public void setUpgradeAsyncWriteBufferSize(int upgradeAsyncWriteBufferSize) {
        this.upgradeAsyncWriteBufferSize = upgradeAsyncWriteBufferSize;
    }


    /**
     * The names of headers that are allowed to be sent via a trailer when using
     * chunked encoding. They are stored in lower case.
     */
    private Set<String> allowedTrailerHeaders =
            Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());
    public void setAllowedTrailerHeaders(String commaSeparatedHeaders) {
        // Jump through some hoops so we don't end up with an empty set while
        // doing updates.
        Set<String> toRemove = new HashSet<String>();
        toRemove.addAll(allowedTrailerHeaders);
        if (commaSeparatedHeaders != null) {
            String[] headers = commaSeparatedHeaders.split(",");
            for (String header : headers) {
                String trimmedHeader = header.trim().toLowerCase(Locale.ENGLISH);
                if (toRemove.contains(trimmedHeader)) {
                    toRemove.remove(trimmedHeader);
                } else {
                    allowedTrailerHeaders.add(trimmedHeader);
                }
            }
            allowedTrailerHeaders.removeAll(toRemove);
        }
    }
    public String getAllowedTrailerHeaders() {
        // Chances of a size change between these lines are small enough that a
        // sync is unnecessary.
        List<String> copy = new ArrayList<String>(allowedTrailerHeaders.size());
        copy.addAll(allowedTrailerHeaders);
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (String header : copy) {
            if (first) {
                first = false;
            } else {
                result.append(',');
            }
            result.append(header);
        }
        return result.toString();
    }
    public void addAllowedTrailerHeader(String header) {
        if (header != null) {
            allowedTrailerHeaders.add(header.trim().toLowerCase(Locale.ENGLISH));
        }
    }
    public void removeAllowedTrailerHeader(String header) {
        if (header != null) {
            allowedTrailerHeaders.remove(header.trim().toLowerCase(Locale.ENGLISH));
        }
    }
    protected Set<String> getAllowedTrailerHeadersAsSet() {
        return allowedTrailerHeaders;
    }


    // ------------------------------------------------ HTTP specific properties
    // ------------------------------------------ passed through to the EndPoint

    public boolean isSSLEnabled() { return endpoint.isSSLEnabled();}
    public void setSSLEnabled(boolean SSLEnabled) {
        endpoint.setSSLEnabled(SSLEnabled);
    }


    /**
     * Maximum number of requests which can be performed over a keepalive
     * connection. The default is the same as for Apache HTTP Server.
     */
    public int getMaxKeepAliveRequests() {
        return endpoint.getMaxKeepAliveRequests();
    }
    public void setMaxKeepAliveRequests(int mkar) {
        endpoint.setMaxKeepAliveRequests(mkar);
    }
    // ------------------------------------------------------------- Common code

    // Common configuration required for all new HTTP11 processors
    protected void configureProcessor(AbstractHttp11Processor<S> processor) {
        processor.setAdapter(getAdapter());
        processor.setMaxKeepAliveRequests(getMaxKeepAliveRequests());
        processor.setKeepAliveTimeout(getKeepAliveTimeout());
        processor.setConnectionUploadTimeout(getConnectionUploadTimeout());
        processor.setDisableUploadTimeout(getDisableUploadTimeout());
        processor.setCompressionMinSize(getCompressionMinSize());
        processor.setCompression(getCompression());
        processor.setNoCompressionUserAgents(getNoCompressionUserAgents());
        processor.setCompressibleMimeTypes(getCompressibleMimeType());
        processor.setRestrictedUserAgents(getRestrictedUserAgents());
        processor.setSocketBuffer(getSocketBuffer());
        processor.setMaxSavePostSize(getMaxSavePostSize());
        processor.setServer(getServer());
        processor.setMaxCookieCount(getMaxCookieCount());
    }
}
