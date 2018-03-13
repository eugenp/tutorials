/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.apache.jasper.tagplugins.jstl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

import org.apache.jasper.Constants;

/**
 * Util contains some often used consts, static methods and embedded class
 * to support the JSTL tag plugin.
 */

public class Util {
    
    public static final String VALID_SCHEME_CHAR = 
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+.-";
    
    public static final String DEFAULT_ENCODING = 
        "ISO-8859-1";
    
    public static final int HIGHEST_SPECIAL = '>';
    
    private static char[][] specialCharactersRepresentation = new char[HIGHEST_SPECIAL + 1][];
    
    static {
        specialCharactersRepresentation['&'] = "&amp;".toCharArray();
        specialCharactersRepresentation['<'] = "&lt;".toCharArray();
        specialCharactersRepresentation['>'] = "&gt;".toCharArray();
        specialCharactersRepresentation['"'] = "&#034;".toCharArray();
        specialCharactersRepresentation['\''] = "&#039;".toCharArray();
    }
    
    /**
     * Converts the given string description of a scope to the corresponding
     * PageContext constant.
     *
     * The validity of the given scope has already been checked by the
     * appropriate TLV.
     *
     * @param scope String description of scope
     *
     * @return PageContext constant corresponding to given scope description
     * 
     * taken from org.apache.taglibs.standard.tag.common.core.Util  
     */
    public static int getScope(String scope){
        int ret = PageContext.PAGE_SCOPE;
        
        if("request".equalsIgnoreCase(scope)){
            ret = PageContext.REQUEST_SCOPE;
        }else if("session".equalsIgnoreCase(scope)){
            ret = PageContext.SESSION_SCOPE;
        }else if("application".equalsIgnoreCase(scope)){
            ret = PageContext.APPLICATION_SCOPE;
        }
        
        return ret;
    }
    
    /**
     * Returns <tt>true</tt> if our current URL is absolute,
     * <tt>false</tt> otherwise.
     * taken from org.apache.taglibs.standard.tag.common.core.ImportSupport
     */
    public static boolean isAbsoluteUrl(String url){
        if(url == null){
            return false;
        }
        
        int colonPos = url.indexOf(':');
        if(colonPos == -1){
            return false;
        }
        
        for(int i=0;i<colonPos;i++){
            if(VALID_SCHEME_CHAR.indexOf(url.charAt(i)) == -1){
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Get the value associated with a content-type attribute.
     * Syntax defined in RFC 2045, section 5.1.
     * taken from org.apache.taglibs.standard.tag.common.core.Util
     */
    public static String getContentTypeAttribute(String input, String name) {
        int begin;
        int end;
        int index = input.toUpperCase(Locale.ENGLISH).indexOf(name.toUpperCase(Locale.ENGLISH));
        if (index == -1) return null;
        index = index + name.length(); // positioned after the attribute name
        index = input.indexOf('=', index); // positioned at the '='
        if (index == -1) return null;
        index += 1; // positioned after the '='
        input = input.substring(index).trim();
        
        if (input.charAt(0) == '"') {
            // attribute value is a quoted string
            begin = 1;
            end = input.indexOf('"', begin);
            if (end == -1) return null;
        } else {
            begin = 0;
            end = input.indexOf(';');
            if (end == -1) end = input.indexOf(' ');
            if (end == -1) end = input.length();
        }
        return input.substring(begin, end).trim();
    }
    
    /**
     * Strips a servlet session ID from <tt>url</tt>.  The session ID
     * is encoded as a URL "path parameter" beginning with "jsessionid=".
     * We thus remove anything we find between ";jsessionid=" (inclusive)
     * and either EOS or a subsequent ';' (exclusive).
     * 
     * taken from org.apache.taglibs.standard.tag.common.core.ImportSupport
     */
    public static String stripSession(String url) {
        StringBuilder u = new StringBuilder(url);
        int sessionStart;
        while ((sessionStart = u.toString().indexOf(";" + Constants.SESSION_PARAMETER_NAME + "=")) != -1) {
            int sessionEnd = u.toString().indexOf(';', sessionStart + 1);
            if (sessionEnd == -1)
                sessionEnd = u.toString().indexOf('?', sessionStart + 1);
            if (sessionEnd == -1) // still
                sessionEnd = u.length();
            u.delete(sessionStart, sessionEnd);
        }
        return u.toString();
    }
    
    
    /**
     * Performs the following substring replacements
     * (to facilitate output to XML/HTML pages):
     *
     *    & -> &amp;
     *    < -> &lt;
     *    > -> &gt;
     *    " -> &#034;
     *    ' -> &#039;
     *
     * See also OutSupport.writeEscapedXml().
     * 
     * taken from org.apache.taglibs.standard.tag.common.core.Util
     */
    public static String escapeXml(String buffer) {
        String result = escapeXml(buffer.toCharArray(), buffer.length());
        if (result == null) {
            return buffer;
        } else {
            return result;
        }
    }

    @SuppressWarnings("null") // escapedBuffer cannot be null
    public static String escapeXml(char[] arrayBuffer, int length) {
        int start = 0;
        StringBuilder escapedBuffer = null;
        
        for (int i = 0; i < length; i++) {
            char c = arrayBuffer[i];
            if (c <= HIGHEST_SPECIAL) {
                char[] escaped = specialCharactersRepresentation[c];
                if (escaped != null) {
                    // create StringBuilder to hold escaped xml string
                    if (start == 0) {
                        escapedBuffer = new StringBuilder(length + 5);
                    }
                    // add unescaped portion
                    if (start < i) {
                        escapedBuffer.append(arrayBuffer,start,i-start);
                    }
                    start = i + 1;
                    // add escaped xml
                    escapedBuffer.append(escaped);
                }
            }
        }
        // no xml escaping was necessary
        if (start == 0) {
            return null;
        }
        // add rest of unescaped portion
        if (start < length) {
            escapedBuffer.append(arrayBuffer,start,length-start);
        }
        return escapedBuffer.toString();
    }
    
    /** Utility methods
     * taken from org.apache.taglibs.standard.tag.common.core.UrlSupport
     */
    public static String resolveUrl(
            String url, String context, PageContext pageContext)
    throws JspException {
        // don't touch absolute URLs
        if (isAbsoluteUrl(url))
            return url;
        
        // normalize relative URLs against a context root
        HttpServletRequest request =
            (HttpServletRequest) pageContext.getRequest();
        if (context == null) {
            if (url.startsWith("/"))
                return (request.getContextPath() + url);
            else
                return url;
        } else {
            if (!context.startsWith("/") || !url.startsWith("/")) {
                throw new JspTagException(
                "In URL tags, when the \"context\" attribute is specified, values of both \"context\" and \"url\" must start with \"/\".");
            }
            if (context.equals("/")) {
                // Don't produce string starting with '//', many
                // browsers interpret this as host name, not as
                // path on same host.
                return url;
            } else {
                return (context + url);
            }
        }
    }
    
    /** Wraps responses to allow us to retrieve results as Strings. 
     * mainly taken from org.apache.taglibs.standard.tag.common.core.importSupport 
     */
    public static class ImportResponseWrapper extends HttpServletResponseWrapper{
        
        private StringWriter sw = new StringWriter();
        private ByteArrayOutputStream bos = new ByteArrayOutputStream();
        private ServletOutputStream sos = new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                bos.write(b);
            }
        };
        private boolean isWriterUsed;
        private boolean isStreamUsed;
        private int status = 200;
        private String charEncoding;
        
        public ImportResponseWrapper(HttpServletResponse arg0) {
            super(arg0);
            // TODO Auto-generated constructor stub
        }
        
        @Override
        public PrintWriter getWriter() {
            if (isStreamUsed)
                throw new IllegalStateException("Unexpected internal error during &lt;import&gt: " +
                "Target servlet called getWriter(), then getOutputStream()");
            isWriterUsed = true;
            return new PrintWriter(sw);
        }
        
        @Override
        public ServletOutputStream getOutputStream() {
            if (isWriterUsed)
                throw new IllegalStateException("Unexpected internal error during &lt;import&gt: " +
                "Target servlet called getOutputStream(), then getWriter()");
            isStreamUsed = true;
            return sos;
        }
        
        /** Has no effect. */
        @Override
        public void setContentType(String x) {
            // ignore
        }
        
        /** Has no effect. */
        @Override
        public void setLocale(Locale x) {
            // ignore
        }
        
        @Override
        public void setStatus(int status) {
            this.status = status;
        }
        
        @Override
        public int getStatus() {
            return status;
        }
        
        public String getCharEncoding(){
            return this.charEncoding;
        }
        
        public void setCharEncoding(String ce){
            this.charEncoding = ce;
        }
        
        public String getString() throws UnsupportedEncodingException {
            if (isWriterUsed)
                return sw.toString();
            else if (isStreamUsed) {
                if (this.charEncoding != null && !this.charEncoding.equals(""))
                    return bos.toString(charEncoding);
                else
                    return bos.toString("ISO-8859-1");
            } else
                return ""; // target didn't write anything
        }
    }
    
}
