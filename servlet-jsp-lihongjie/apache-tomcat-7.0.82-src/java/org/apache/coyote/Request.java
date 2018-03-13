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
import java.util.HashMap;

import org.apache.tomcat.util.buf.ByteChunk;
import org.apache.tomcat.util.buf.MessageBytes;
import org.apache.tomcat.util.buf.UDecoder;
import org.apache.tomcat.util.http.ContentType;
import org.apache.tomcat.util.http.Cookies;
import org.apache.tomcat.util.http.MimeHeaders;
import org.apache.tomcat.util.http.Parameters;

/**
 * This is a low-level, efficient representation of a server request. Most 
 * fields are GC-free, expensive operations are delayed until the  user code 
 * needs the information.
 *
 * Processing is delegated to modules, using a hook mechanism.
 * 
 * This class is not intended for user code - it is used internally by tomcat
 * for processing the request in the most efficient way. Users ( servlets ) can
 * access the information using a facade, which provides the high-level view
 * of the request.
 *
 * For lazy evaluation, the request uses the getInfo() hook. The following ids
 * are defined:
 * <ul>
 *  <li>req.encoding - returns the request encoding
 *  <li>req.attribute - returns a module-specific attribute ( like SSL keys, etc ).
 * </ul>
 *
 * Tomcat defines a number of attributes:
 * <ul>
 *   <li>"org.apache.tomcat.request" - allows access to the low-level
 *       request object in trusted applications 
 * </ul>
 *
 * @author James Duncan Davidson [duncan@eng.sun.com]
 * @author James Todd [gonzo@eng.sun.com]
 * @author Jason Hunter [jch@eng.sun.com]
 * @author Harish Prabandham
 * @author Alex Cruikshank [alex@epitonic.com]
 * @author Hans Bergsten [hans@gefionsoftware.com]
 * @author Costin Manolache
 * @author Remy Maucherat
 */
public final class Request {


    // ----------------------------------------------------------- Constructors


    public Request() {

        parameters.setQuery(queryMB);
        parameters.setURLDecoder(urlDecoder);

    }


    // ----------------------------------------------------- Instance Variables


    private int serverPort = -1;
    private MessageBytes serverNameMB = MessageBytes.newInstance();

    private int remotePort;
    private int localPort;

    private MessageBytes schemeMB = MessageBytes.newInstance();

    private MessageBytes methodMB = MessageBytes.newInstance();
    private MessageBytes unparsedURIMB = MessageBytes.newInstance();
    private MessageBytes uriMB = MessageBytes.newInstance();
    private MessageBytes decodedUriMB = MessageBytes.newInstance();
    private MessageBytes queryMB = MessageBytes.newInstance();
    private MessageBytes protoMB = MessageBytes.newInstance();

    // remote address/host
    private MessageBytes remoteAddrMB = MessageBytes.newInstance();
    private MessageBytes localNameMB = MessageBytes.newInstance();
    private MessageBytes remoteHostMB = MessageBytes.newInstance();
    private MessageBytes localAddrMB = MessageBytes.newInstance();
     
    private MimeHeaders headers = new MimeHeaders();

    private MessageBytes instanceId = MessageBytes.newInstance();

    /**
     * Notes.
     */
    private Object notes[] = new Object[Constants.MAX_NOTES];


    /**
     * Associated input buffer.
     */
    private InputBuffer inputBuffer = null;


    /**
     * URL decoder.
     */
    private UDecoder urlDecoder = new UDecoder();


    /**
     * HTTP specific fields. (remove them ?)
     */
    private long contentLength = -1;
    private MessageBytes contentTypeMB = null;
    private String charEncoding = null;
    private Cookies cookies = new Cookies(headers);
    private Parameters parameters = new Parameters();

    private MessageBytes remoteUser = MessageBytes.newInstance();
    private boolean remoteUserNeedsAuthorization = false;
    private MessageBytes authType = MessageBytes.newInstance();
    private HashMap<String,Object> attributes = new HashMap<String,Object>();

    private Response response;
    private ActionHook hook;

    private long bytesRead=0;
    // Time of the request - useful to avoid repeated calls to System.currentTime
    private long startTime = -1;
    private int available = 0;

    private RequestInfo reqProcessorMX=new RequestInfo(this);
    // ------------------------------------------------------------- Properties


    /**
     * Get the instance id (or JVM route). Currently Ajp is sending it with each
     * request. In future this should be fixed, and sent only once ( or
     * 'negotiated' at config time so both tomcat and apache share the same name.
     * 
     * @return the instance id
     */
    public MessageBytes instanceId() {
        return instanceId;
    }


    public MimeHeaders getMimeHeaders() {
        return headers;
    }


    public UDecoder getURLDecoder() {
        return urlDecoder;
    }

    // -------------------- Request data --------------------


    public MessageBytes scheme() {
        return schemeMB;
    }
    
    public MessageBytes method() {
        return methodMB;
    }
    
    public MessageBytes unparsedURI() {
        return unparsedURIMB;
    }

    public MessageBytes requestURI() {
        return uriMB;
    }

    public MessageBytes decodedURI() {
        return decodedUriMB;
    }

    public MessageBytes queryString() {
        return queryMB;
    }

    public MessageBytes protocol() {
        return protoMB;
    }
    
    /** 
     * Return the buffer holding the server name, if
     * any. Use isNull() to check if there is no value
     * set.
     * This is the "virtual host", derived from the
     * Host: header.
     */
    public MessageBytes serverName() {
        return serverNameMB;
    }

    public int getServerPort() {
        return serverPort;
    }
    
    public void setServerPort(int serverPort ) {
        this.serverPort=serverPort;
    }

    public MessageBytes remoteAddr() {
        return remoteAddrMB;
    }

    public MessageBytes remoteHost() {
        return remoteHostMB;
    }

    public MessageBytes localName() {
        return localNameMB;
    }    

    public MessageBytes localAddr() {
        return localAddrMB;
    }
    
    public int getRemotePort(){
        return remotePort;
    }
        
    public void setRemotePort(int port){
        this.remotePort = port;
    }
    
    public int getLocalPort(){
        return localPort;
    }
        
    public void setLocalPort(int port){
        this.localPort = port;
    }

    // -------------------- encoding/type --------------------


    /**
     * Get the character encoding used for this request.
     */
    public String getCharacterEncoding() {

        if (charEncoding != null)
            return charEncoding;

        charEncoding = ContentType.getCharsetFromContentType(getContentType());
        return charEncoding;

    }


    public void setCharacterEncoding(String enc) {
        this.charEncoding = enc;
    }


    public void setContentLength(long len) {
        this.contentLength = len;
    }


    public int getContentLength() {
        long length = getContentLengthLong();

        if (length < Integer.MAX_VALUE) {
            return (int) length;
        }
        return -1;
    }

    public long getContentLengthLong() {
        if( contentLength > -1 ) return contentLength;

        MessageBytes clB = headers.getUniqueValue("content-length");
        contentLength = (clB == null || clB.isNull()) ? -1 : clB.getLong();

        return contentLength;
    }

    public String getContentType() {
        contentType();
        if ((contentTypeMB == null) || contentTypeMB.isNull()) 
            return null;
        return contentTypeMB.toString();
    }


    public void setContentType(String type) {
        contentTypeMB.setString(type);
    }


    public MessageBytes contentType() {
        if (contentTypeMB == null)
            contentTypeMB = headers.getValue("content-type");
        return contentTypeMB;
    }


    public void setContentType(MessageBytes mb) {
        contentTypeMB=mb;
    }


    public String getHeader(String name) {
        return headers.getHeader(name);
    }

    // -------------------- Associated response --------------------

    public Response getResponse() {
        return response;
    }

    public void setResponse( Response response ) {
        this.response=response;
        response.setRequest( this );
    }
    
    public void action(ActionCode actionCode, Object param) {
        if( hook==null && response!=null )
            hook=response.getHook();
        
        if (hook != null) {
            if( param==null ) 
                hook.action(actionCode, this);
            else
                hook.action(actionCode, param);
        }
    }


    // -------------------- Cookies --------------------


    public Cookies getCookies() {
        return cookies;
    }


    // -------------------- Parameters --------------------


    public Parameters getParameters() {
        return parameters;
    }


    // -------------------- Other attributes --------------------
    // We can use notes for most - need to discuss what is of general interest
    
    public void setAttribute( String name, Object o ) {
        attributes.put( name, o );
    }

    public HashMap<String,Object> getAttributes() {
        return attributes;
    }

    public Object getAttribute(String name ) {
        return attributes.get(name);
    }
    
    public MessageBytes getRemoteUser() {
        return remoteUser;
    }

    public boolean getRemoteUserNeedsAuthorization() {
        return remoteUserNeedsAuthorization;
    }

    public void setRemoteUserNeedsAuthorization(boolean remoteUserNeedsAuthorization) {
        this.remoteUserNeedsAuthorization = remoteUserNeedsAuthorization;
    }

    public MessageBytes getAuthType() {
        return authType;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public boolean getSupportsRelativeRedirects() {
        if (protocol().equals("") || protocol().equals("HTTP/1.0")) {
            return false;
        }
        return true;
    }


    // -------------------- Input Buffer --------------------

    public InputBuffer getInputBuffer() {
        return inputBuffer;
    }


    public void setInputBuffer(InputBuffer inputBuffer) {
        this.inputBuffer = inputBuffer;
    }


    /**
     * Read data from the input buffer and put it into a byte chunk.
     *
     * The buffer is owned by the protocol implementation - it will be reused on the next read.
     * The Adapter must either process the data in place or copy it to a separate buffer if it needs
     * to hold it. In most cases this is done during byte->char conversions or via InputStream. Unlike
     * InputStream, this interface allows the app to process data in place, without copy.
     *
     */
    public int doRead(ByteChunk chunk) 
        throws IOException {
        int n = inputBuffer.doRead(chunk, this);
        if (n > 0) {
            bytesRead+=n;
        }
        return n;
    }


    // -------------------- debug --------------------

    @Override
    public String toString() {
        return "R( " + requestURI().toString() + ")";
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    // -------------------- Per-Request "notes" --------------------


    /** 
     * Used to store private data. Thread data could be used instead - but 
     * if you have the req, getting/setting a note is just a array access, may
     * be faster than ThreadLocal for very frequent operations.
     * 
     *  Example use: 
     *   Jk:
     *     HandlerRequest.HOSTBUFFER = 10 CharChunk, buffer for Host decoding
     *     WorkerEnv: SSL_CERT_NOTE=16 - MessageBytes containing the cert
     *                
     *   Catalina CoyoteAdapter:
     *      ADAPTER_NOTES = 1 - stores the HttpServletRequest object ( req/res)             
     *      
     *   To avoid conflicts, note in the range 0 - 8 are reserved for the 
     *   servlet container ( catalina connector, etc ), and values in 9 - 16 
     *   for connector use. 
     *   
     *   17-31 range is not allocated or used.
     */
    public final void setNote(int pos, Object value) {
        notes[pos] = value;
    }


    public final Object getNote(int pos) {
        return notes[pos];
    }


    // -------------------- Recycling -------------------- 


    public void recycle() {
        bytesRead=0;

        contentLength = -1;
        contentTypeMB = null;
        charEncoding = null;
        headers.recycle();
        serverNameMB.recycle();
        serverPort=-1;
        localNameMB.recycle();
        localPort = -1;
        remotePort = -1;
        available = 0;

        cookies.recycle();
        parameters.recycle();

        unparsedURIMB.recycle();
        uriMB.recycle(); 
        decodedUriMB.recycle();
        queryMB.recycle();
        methodMB.recycle();
        protoMB.recycle();

        schemeMB.recycle();

        instanceId.recycle();
        remoteUser.recycle();
        remoteUserNeedsAuthorization = false;
        authType.recycle();
        attributes.clear();

        startTime = -1;
    }

    // -------------------- Info  --------------------
    public void updateCounters() {
        reqProcessorMX.updateCounters();
    }

    public RequestInfo getRequestProcessor() {
        return reqProcessorMX;
    }

    public long getBytesRead() {
        return bytesRead;
    }

    public boolean isProcessing() {
        return reqProcessorMX.getStage()==org.apache.coyote.Constants.STAGE_SERVICE;
    }
}
