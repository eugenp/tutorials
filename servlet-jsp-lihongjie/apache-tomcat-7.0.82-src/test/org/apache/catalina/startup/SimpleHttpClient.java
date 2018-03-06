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

package org.apache.catalina.startup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Simple client for unit testing. It isn't robust, it isn't secure and
 * should not be used as the basis for production code. Its only purpose
 * is to do the bare minimum for the unit tests.
 */
public abstract class SimpleHttpClient {
    public static final String TEMP_DIR =
            System.getProperty("java.io.tmpdir");

    public static final String CR = "\r";
    public static final String LF = "\n";
    public static final String CRLF = CR + LF;

    public static final String INFO_100 = "HTTP/1.1 100 ";
    public static final String OK_200 = "HTTP/1.1 200 ";
    public static final String REDIRECT_302 = "HTTP/1.1 302 ";
    public static final String FAIL_400 = "HTTP/1.1 400 ";
    public static final String FORBIDDEN_403 = "HTTP/1.1 403 ";
    public static final String FAIL_404 = "HTTP/1.1 404 ";
    public static final String TIMEOUT_408 = "HTTP/1.1 408 ";
    public static final String FAIL_413 = "HTTP/1.1 413 ";
    public static final String FAIL_417 = "HTTP/1.1 417 ";
    public static final String FAIL_50X = "HTTP/1.1 50";
    public static final String FAIL_500 = "HTTP/1.1 500 ";
    public static final String FAIL_501 = "HTTP/1.1 501 ";

    private static final String CONTENT_LENGTH_HEADER_PREFIX =
            "Content-Length: ";

    protected static final String SESSION_COOKIE_NAME = "JSESSIONID";
    protected static final String SESSION_PARAMETER_NAME =
            SESSION_COOKIE_NAME.toLowerCase(Locale.ENGLISH);

    private static final String COOKIE_HEADER_PREFIX = "Set-Cookie: ";
    private static final String SESSION_COOKIE_HEADER_PREFIX =
            COOKIE_HEADER_PREFIX + SESSION_COOKIE_NAME + "=";

    private static final String REDIRECT_HEADER_PREFIX = "Location: ";
    protected static final String SESSION_PATH_PARAMETER_PREFIX =
            SESSION_PARAMETER_NAME + "=";
    protected static final String SESSION_PATH_PARAMETER_TAILS = CRLF + ";?";

    private static final String ELEMENT_HEAD = "<";
    private static final String ELEMENT_TAIL = ">";
    private static final String RESOURCE_TAG = "a";
    private static final String LOGIN_TAG = "form";

    private Socket socket;
    private Writer writer;
    private BufferedReader reader;
    private int port = 8080;

    private String[] request;
    private boolean useContinue = false;
    private boolean useCookies = true;
    private int requestPause = 1000;

    private String responseLine;
    private List<String> responseHeaders = new ArrayList<String>();
    private String sessionId;
    private boolean useContentLength;
    private int contentLength;
    private String redirectUri;

    private String responseBody;
    private List<String> bodyUriElements = null;

    public void setPort(int thePort) {
        port = thePort;
    }

    public void setRequest(String[] theRequest) {
        request = theRequest;
    }

    /*
     * Expect the server to reply with 100 Continue interim response
     */
    public void setUseContinue(boolean theUseContinueFlag) {
        useContinue = theUseContinueFlag;
    }

    public boolean getUseContinue() {
        return useContinue;
    }

    public void setUseCookies(boolean theUseCookiesFlag) {
        useCookies = theUseCookiesFlag;
    }

    public boolean getUseCookies() {
        return useCookies;
    }

    public void setRequestPause(int theRequestPause) {
        requestPause = theRequestPause;
    }

    public String getResponseLine() {
        return responseLine;
    }

    public List<String> getResponseHeaders() {
        return responseHeaders;
    }

    public String getResponseBody() {
        return responseBody;
    }

    /**
     * Return opening tags of HTML elements that were extracted by the
     * {@link #extractUriElements()} method.
     *
     * <p>
     * Note, that {@link #extractUriElements()} method has to be called
     * explicitly.
     *
     * @return List of HTML tags, accumulated by {@link #extractUriElements()}
     *         method, or {@code null} if the method has not been called yet.
     */
    public List<String> getResponseBodyUriElements() {
        return bodyUriElements;
    }

    public void setUseContentLength(boolean b) {
        useContentLength = b;
    }

    public void setSessionId(String theSessionId) {
        sessionId = theSessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void connect(int connectTimeout, int soTimeout)
           throws UnknownHostException, IOException {
        final String encoding = "ISO-8859-1";
        SocketAddress addr = new InetSocketAddress("localhost", port);
        socket = new Socket();
        socket.setSoTimeout(soTimeout);
        socket.connect(addr,connectTimeout);
        OutputStream os = socket.getOutputStream();
        writer = new OutputStreamWriter(os, encoding);
        InputStream is = socket.getInputStream();
        Reader r = new InputStreamReader(is, encoding);
        reader = new BufferedReader(r);
    }
    public void connect() throws UnknownHostException, IOException {
        connect(0,0);
    }

    public void processRequest() throws IOException, InterruptedException {
        processRequest(true);
    }

    public void processRequest(boolean wantBody)
            throws IOException, InterruptedException {
        sendRequest();

        readResponse(wantBody);

    }

    /*
     * Send the component parts of the request
     * (be tolerant and simply skip null entries)
     */
    public void sendRequest() throws InterruptedException, IOException {
        boolean first = true;
        for (String requestPart : request) {
            if (requestPart != null) {
                if (first) {
                    first = false;
                }
                else {
                    Thread.sleep(requestPause);
                }
                writer.write(requestPart);
                writer.flush();
            }
        }
    }

    public void readResponse(boolean wantBody) throws IOException {
        // clear any residual data before starting on this response
        responseHeaders.clear();
        responseBody = null;
        if (bodyUriElements != null) {
            bodyUriElements.clear();
        }

        // Read the response status line
        responseLine = readLine();

        // Is a 100 continue response expected?
        if (useContinue) {
            if (isResponse100()) {
                // Skip the blank after the 100 Continue response
                readLine();
                // Now get the final response
                responseLine = readLine();
            } else {
                throw new IOException("No 100 Continue response");
            }
        }

        // Put the headers into a map, and process interesting ones
        processHeaders();

        // Read the body, if requested and if one exists
        processBody(wantBody);

        if (isResponse408()) {
            // the session has timed out
            sessionId = null;
        }
    }

    /*
     * Accumulate the response headers into a map, and extract
     * interesting details at the same time
     */
    private void processHeaders() throws IOException {
        // Reset response fields
        redirectUri = null;
        contentLength = -1;

        String line = readLine();
        while ((line != null) && (line.length() > 0)) {
            responseHeaders.add(line);
            if (line.startsWith(CONTENT_LENGTH_HEADER_PREFIX)) {
                contentLength = Integer.parseInt(line.substring(16));
            }
            else if (line.startsWith(COOKIE_HEADER_PREFIX)) {
                if (useCookies) {
                    String temp = line.substring(
                            SESSION_COOKIE_HEADER_PREFIX.length());
                    temp = temp.substring(0, temp.indexOf(';'));
                    setSessionId(temp);
                }
            }
            else if (line.startsWith(REDIRECT_HEADER_PREFIX)) {
                redirectUri = line.substring(REDIRECT_HEADER_PREFIX.length());
            }
            line = readLine();
        }
    }

    /*
     * Read the body of the server response. Save its contents and
     * search it for uri-elements only if requested
     */
    private void processBody(boolean wantBody) throws IOException {
        // Read the body, if one exists
        StringBuilder builder = new StringBuilder();
        if (wantBody) {
            if (useContentLength && (contentLength > -1)) {
                char[] body = new char[contentLength];
                reader.read(body);
                builder.append(body);
            }
            else {
                // not using content length, so just read it line by line
                String line = null;
                while ((line = readLine()) != null) {
                    builder.append(line);
                }
            }
        }
        responseBody = builder.toString();
    }

    /**
     * Scan the response body for opening tags of certain HTML elements
     * (&lt;a&gt;, &lt;form&gt;). If any are found, then accumulate them.
     *
     * <p>
     * Note: This method has the following limitations: a) It assumes that the
     * response is HTML. b) It searches for lowercase tags only.
     *
     * @see #getResponseBodyUriElements()
     */
    public void extractUriElements() {
        bodyUriElements = new ArrayList<String>();
        if (responseBody.length() > 0) {
            int ix = 0;
            while ((ix = extractUriElement(responseBody, ix, RESOURCE_TAG)) > 0){
                // loop
            }
            ix = 0;
            while ((ix = extractUriElement(responseBody, ix, LOGIN_TAG)) > 0){
                // loop
            }
        }
    }

    /*
     * Scan an html body for a given html uri element, starting from the
     * given index into the source string. If any are found, simply
     * accumulate them as literal strings, including angle brackets.
     * note: nested elements will not be collected.
     *
     * @param body HTTP body of the response
     * @param startIx offset into the body to resume the scan (for iteration)
     * @param elementName to scan for (only one at a time)
     * @returns the index into the body to continue the scan (for iteration)
     */
    private int extractUriElement(String body, int startIx, String elementName) {
        String detector = ELEMENT_HEAD + elementName + " ";
        int iStart = body.indexOf(detector, startIx);
        if (iStart > -1) {
            int iEnd = body.indexOf(ELEMENT_TAIL, iStart);
            if (iEnd < 0) {
                throw new IllegalArgumentException(
                        "Response body check failure.\n"
                        + "element [" + detector + "] is not terminated with ["
                        + ELEMENT_TAIL + "]\nActual: [" + body + "]");
            }
            String element = body.substring(iStart, iEnd);
            bodyUriElements.add(element);
            iStart += element.length();
        }
        return iStart;
    }

    public String readLine() throws IOException {
        return reader.readLine();
    }

    public void disconnect() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }

    public void reset() {
        socket = null;
        writer = null;
        reader = null;

        request = null;
        requestPause = 1000;

        useContinue = false;

        resetResponse();
    }

    public void resetResponse() {
        responseLine = null;
        responseHeaders = new ArrayList<String>();
        responseBody = null;
    }

    public boolean isResponse100() {
        return getResponseLine().startsWith(INFO_100);
    }

    public boolean isResponse200() {
        return getResponseLine().startsWith(OK_200);
    }

    public boolean isResponse302() {
        return getResponseLine().startsWith(REDIRECT_302);
    }

    public boolean isResponse400() {
        return getResponseLine().startsWith(FAIL_400);
    }

    public boolean isResponse403() {
        return getResponseLine().startsWith(FORBIDDEN_403);
    }

    public boolean isResponse404() {
        return getResponseLine().startsWith(FAIL_404);
    }

    public boolean isResponse408() {
        return getResponseLine().startsWith(TIMEOUT_408);
    }

    public boolean isResponse413() {
        return getResponseLine().startsWith(FAIL_413);
    }

    public boolean isResponse417() {
        return getResponseLine().startsWith(FAIL_417);
    }

    public boolean isResponse50x() {
        return getResponseLine().startsWith(FAIL_50X);
    }

    public boolean isResponse500() {
        return getResponseLine().startsWith(FAIL_500);
    }

    public boolean isResponse501() {
        return getResponseLine().startsWith(FAIL_501);
    }

    public Socket getSocket() {
        return socket;
    }

    public abstract boolean isResponseBodyOK();
}