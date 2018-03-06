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

package org.apache.jasper.compiler;

import java.util.Iterator;
import java.util.Vector;

import javax.servlet.ServletContext;

import org.apache.jasper.Constants;
import org.apache.jasper.JasperException;
import org.apache.jasper.xmlparser.ParserUtils;
import org.apache.jasper.xmlparser.TreeNode;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 * Handles the jsp-config element in WEB_INF/web.xml.  This is used
 * for specifying the JSP configuration information on a JSP page
 *
 * @author Kin-man Chung
 * @author Remy Maucherat
 */

public class JspConfig {

    // Logger
    private final Log log = LogFactory.getLog(JspConfig.class);

    private Vector<JspPropertyGroup> jspProperties = null;
    private ServletContext ctxt;
    private volatile boolean initialized = false;

    private static final String defaultIsXml = null;    // unspecified
    private String defaultIsELIgnored = null;           // unspecified
    private static final String defaultIsScriptingInvalid = null;
    private String defaultDeferedSyntaxAllowedAsLiteral = null;
    private static final String defaultTrimDirectiveWhitespaces = null;
    private static final String defaultDefaultContentType = null;
    private static final String defaultBuffer = null;
    private static final String defaultErrorOnUndeclaredNamespace = "false";
    private JspProperty defaultJspProperty;

    public JspConfig(ServletContext ctxt) {
        this.ctxt = ctxt;
    }

    private double getVersion(TreeNode webApp) {
        String v = webApp.findAttribute("version");
        if (v != null) {
            try {
                return Double.parseDouble(v);
            } catch (NumberFormatException e) {
            }
        }
        return 2.3;
    }

    private void processWebDotXml() throws JasperException {

        WebXml webXml = null;

        try {
            webXml = new WebXml(ctxt);
            
            boolean validate = Boolean.parseBoolean(
                    ctxt.getInitParameter(Constants.XML_VALIDATION_INIT_PARAM));
            String blockExternalString =
                    ctxt.getInitParameter(Constants.XML_BLOCK_EXTERNAL_INIT_PARAM);
            boolean blockExternal;
            if (blockExternalString == null) {
                blockExternal = true;
            } else {
                blockExternal = Boolean.parseBoolean(blockExternalString);
            }

            TreeNode webApp = null;
            if (webXml.getInputSource() != null) {
                ParserUtils pu = new ParserUtils(validate, blockExternal);
                webApp = pu.parseXMLDocument(webXml.getSystemId(),
                        webXml.getInputSource());
            }

            if (webApp == null
                    || getVersion(webApp) < 2.4) {
                defaultIsELIgnored = "true";
                defaultDeferedSyntaxAllowedAsLiteral = "true";
                return;
            }
            if (getVersion(webApp) < 2.5) {
                defaultDeferedSyntaxAllowedAsLiteral = "true";
            }
            TreeNode jspConfig = webApp.findChild("jsp-config");
            if (jspConfig == null) {
                return;
            }

            jspProperties = new Vector<JspPropertyGroup>();
            Iterator<TreeNode> jspPropertyList =
                jspConfig.findChildren("jsp-property-group");
            while (jspPropertyList.hasNext()) {

                TreeNode element = jspPropertyList.next();
                Iterator<TreeNode> list = element.findChildren();

                Vector<String> urlPatterns = new Vector<String>();
                String pageEncoding = null;
                String scriptingInvalid = null;
                String elIgnored = null;
                String isXml = null;
                Vector<String> includePrelude = new Vector<String>();
                Vector<String> includeCoda = new Vector<String>();
                String deferredSyntaxAllowedAsLiteral = null;
                String trimDirectiveWhitespaces = null;
                String defaultContentType = null;
                String buffer = null;
                String errorOnUndeclaredNamespace = null;

                while (list.hasNext()) {

                    element = list.next();
                    String tname = element.getName();

                    if ("url-pattern".equals(tname))
                        urlPatterns.addElement( element.getBody() );
                    else if ("page-encoding".equals(tname))
                        pageEncoding = element.getBody();
                    else if ("is-xml".equals(tname))
                        isXml = element.getBody();
                    else if ("el-ignored".equals(tname))
                        elIgnored = element.getBody();
                    else if ("scripting-invalid".equals(tname))
                        scriptingInvalid = element.getBody();
                    else if ("include-prelude".equals(tname))
                        includePrelude.addElement(element.getBody());
                    else if ("include-coda".equals(tname))
                        includeCoda.addElement(element.getBody());
                    else if ("deferred-syntax-allowed-as-literal".equals(tname))
                        deferredSyntaxAllowedAsLiteral = element.getBody();
                    else if ("trim-directive-whitespaces".equals(tname))
                        trimDirectiveWhitespaces = element.getBody();
                    else if ("default-content-type".equals(tname))
                        defaultContentType = element.getBody();
                    else if ("buffer".equals(tname))
                        buffer = element.getBody();
                    else if ("error-on-undeclared-namespace".equals(tname))
                        errorOnUndeclaredNamespace = element.getBody();
                }

                if (urlPatterns.size() == 0) {
                    continue;
                }

                // Add one JspPropertyGroup for each URL Pattern.  This makes
                // the matching logic easier.
                for( int p = 0; p < urlPatterns.size(); p++ ) {
                    String urlPattern = urlPatterns.elementAt( p );
                    String path = null;
                    String extension = null;

                    if (urlPattern.indexOf('*') < 0) {
                        // Exact match
                        path = urlPattern;
                    } else {
                        int i = urlPattern.lastIndexOf('/');
                        String file;
                        if (i >= 0) {
                            path = urlPattern.substring(0,i+1);
                            file = urlPattern.substring(i+1);
                        } else {
                            file = urlPattern;
                        }

                        // pattern must be "*", or of the form "*.jsp"
                        if (file.equals("*")) {
                            extension = "*";
                        } else if (file.startsWith("*.")) {
                            extension = file.substring(file.indexOf('.')+1);
                        }

                        // The url patterns are reconstructed as the following:
                        // path != null, extension == null:  / or /foo/bar.ext
                        // path == null, extension != null:  *.ext
                        // path != null, extension == "*":   /foo/*
                        boolean isStar = "*".equals(extension);
                        if ((path == null && (extension == null || isStar))
                                || (path != null && !isStar)) {
                            if (log.isWarnEnabled()) {
                                log.warn(Localizer.getMessage(
                                        "jsp.warning.bad.urlpattern.propertygroup",
                                        urlPattern));
                            }
                            continue;
                        }
                    }

                    JspProperty property = new JspProperty(isXml,
                            elIgnored,
                            scriptingInvalid,
                            pageEncoding,
                            includePrelude,
                            includeCoda,
                            deferredSyntaxAllowedAsLiteral,
                            trimDirectiveWhitespaces,
                            defaultContentType,
                            buffer,
                            errorOnUndeclaredNamespace);
                    JspPropertyGroup propertyGroup =
                        new JspPropertyGroup(path, extension, property);

                    jspProperties.addElement(propertyGroup);
                }
            }
        } catch (Exception ex) {
            throw new JasperException(ex);
        } finally {
            if (webXml != null) {
                webXml.close();
            }
        }
    }

    private void init() throws JasperException {

        if (!initialized) {
            synchronized (this) {
                if (!initialized) {
                    processWebDotXml();
                    defaultJspProperty = new JspProperty(defaultIsXml,
                            defaultIsELIgnored,
                            defaultIsScriptingInvalid,
                            null, null, null,
                            defaultDeferedSyntaxAllowedAsLiteral, 
                            defaultTrimDirectiveWhitespaces,
                            defaultDefaultContentType,
                            defaultBuffer,
                            defaultErrorOnUndeclaredNamespace);
                    initialized = true;
                }
            }
        }
    }

    /**
     * Select the property group that has more restrictive url-pattern.
     * In case of tie, select the first.
     */
    private JspPropertyGroup selectProperty(JspPropertyGroup prev,
            JspPropertyGroup curr) {
        if (prev == null) {
            return curr;
        }
        if (prev.getExtension() == null) {
            // exact match
            return prev;
        }
        if (curr.getExtension() == null) {
            // exact match
            return curr;
        }
        String prevPath = prev.getPath();
        String currPath = curr.getPath();
        if (prevPath == null && currPath == null) {
            // Both specifies a *.ext, keep the first one
            return prev;
        }
        if (prevPath == null && currPath != null) {
            return curr;
        }
        if (prevPath != null && currPath == null) {
            return prev;
        }
        if (prevPath.length() >= currPath.length()) {
            return prev;
        }
        return curr;
    }


    /**
     * Find a property that best matches the supplied resource.
     * @param uri the resource supplied.
     * @return a JspProperty indicating the best match, or some default.
     */
    public JspProperty findJspProperty(String uri) throws JasperException {

        init();

        // JSP Configuration settings do not apply to tag files
        if (jspProperties == null || uri.endsWith(".tag")
                || uri.endsWith(".tagx")) {
            return defaultJspProperty;
        }

        String uriPath = null;
        int index = uri.lastIndexOf('/');
        if (index >=0 ) {
            uriPath = uri.substring(0, index+1);
        }
        String uriExtension = null;
        index = uri.lastIndexOf('.');
        if (index >=0) {
            uriExtension = uri.substring(index+1);
        }

        Vector<String> includePreludes = new Vector<String>();
        Vector<String> includeCodas = new Vector<String>();

        JspPropertyGroup isXmlMatch = null;
        JspPropertyGroup elIgnoredMatch = null;
        JspPropertyGroup scriptingInvalidMatch = null;
        JspPropertyGroup pageEncodingMatch = null;
        JspPropertyGroup deferedSyntaxAllowedAsLiteralMatch = null;
        JspPropertyGroup trimDirectiveWhitespacesMatch = null;
        JspPropertyGroup defaultContentTypeMatch = null;
        JspPropertyGroup bufferMatch = null;
        JspPropertyGroup errorOnUndeclaredNamespaceMatch = null;

        Iterator<JspPropertyGroup> iter = jspProperties.iterator();
        while (iter.hasNext()) {

            JspPropertyGroup jpg = iter.next();
            JspProperty jp = jpg.getJspProperty();

            // (arrays will be the same length)
            String extension = jpg.getExtension();
            String path = jpg.getPath();

            if (extension == null) {
                // exact match pattern: /a/foo.jsp
                if (!uri.equals(path)) {
                    // not matched;
                    continue;
                }
            } else {
                // Matching patterns *.ext or /p/*
                if (path != null && uriPath != null &&
                        ! uriPath.startsWith(path)) {
                    // not matched
                    continue;
                }
                if (!extension.equals("*") &&
                        !extension.equals(uriExtension)) {
                    // not matched
                    continue;
                }
            }
            // We have a match
            // Add include-preludes and include-codas
            if (jp.getIncludePrelude() != null) {
                includePreludes.addAll(jp.getIncludePrelude());
            }
            if (jp.getIncludeCoda() != null) {
                includeCodas.addAll(jp.getIncludeCoda());
            }

            // If there is a previous match for the same property, remember
            // the one that is more restrictive.
            if (jp.isXml() != null) {
                isXmlMatch = selectProperty(isXmlMatch, jpg);
            }
            if (jp.isELIgnored() != null) {
                elIgnoredMatch = selectProperty(elIgnoredMatch, jpg);
            }
            if (jp.isScriptingInvalid() != null) {
                scriptingInvalidMatch =
                    selectProperty(scriptingInvalidMatch, jpg);
            }
            if (jp.getPageEncoding() != null) {
                pageEncodingMatch = selectProperty(pageEncodingMatch, jpg);
            }
            if (jp.isDeferedSyntaxAllowedAsLiteral() != null) {
                deferedSyntaxAllowedAsLiteralMatch =
                    selectProperty(deferedSyntaxAllowedAsLiteralMatch, jpg);
            }
            if (jp.isTrimDirectiveWhitespaces() != null) {
                trimDirectiveWhitespacesMatch =
                    selectProperty(trimDirectiveWhitespacesMatch, jpg);
            }
            if (jp.getDefaultContentType() != null) {
                defaultContentTypeMatch =
                    selectProperty(defaultContentTypeMatch, jpg);
            }
            if (jp.getBuffer() != null) {
                bufferMatch = selectProperty(bufferMatch, jpg);
            }
            if (jp.isErrorOnUndeclaredNamespace() != null) {
                errorOnUndeclaredNamespaceMatch =
                    selectProperty(errorOnUndeclaredNamespaceMatch, jpg);
            }
        }


        String isXml = defaultIsXml;
        String isELIgnored = defaultIsELIgnored;
        String isScriptingInvalid = defaultIsScriptingInvalid;
        String pageEncoding = null;
        String isDeferedSyntaxAllowedAsLiteral =
            defaultDeferedSyntaxAllowedAsLiteral;
        String isTrimDirectiveWhitespaces = defaultTrimDirectiveWhitespaces;
        String defaultContentType = defaultDefaultContentType;
        String buffer = defaultBuffer;
        String errorOnUndeclaredNamespace = defaultErrorOnUndeclaredNamespace;

        if (isXmlMatch != null) {
            isXml = isXmlMatch.getJspProperty().isXml();
        }
        if (elIgnoredMatch != null) {
            isELIgnored = elIgnoredMatch.getJspProperty().isELIgnored();
        }
        if (scriptingInvalidMatch != null) {
            isScriptingInvalid =
                scriptingInvalidMatch.getJspProperty().isScriptingInvalid();
        }
        if (pageEncodingMatch != null) {
            pageEncoding = pageEncodingMatch.getJspProperty().getPageEncoding();
        }
        if (deferedSyntaxAllowedAsLiteralMatch != null) {
            isDeferedSyntaxAllowedAsLiteral =
                deferedSyntaxAllowedAsLiteralMatch.getJspProperty().isDeferedSyntaxAllowedAsLiteral();
        }
        if (trimDirectiveWhitespacesMatch != null) {
            isTrimDirectiveWhitespaces =
                trimDirectiveWhitespacesMatch.getJspProperty().isTrimDirectiveWhitespaces();
        }
        if (defaultContentTypeMatch != null) {
            defaultContentType =
                defaultContentTypeMatch.getJspProperty().getDefaultContentType();
        }
        if (bufferMatch != null) {
            buffer = bufferMatch.getJspProperty().getBuffer();
        }
        if (errorOnUndeclaredNamespaceMatch != null) {
            errorOnUndeclaredNamespace =
                errorOnUndeclaredNamespaceMatch.getJspProperty().isErrorOnUndeclaredNamespace();
        }

        return new JspProperty(isXml, isELIgnored, isScriptingInvalid,
                pageEncoding, includePreludes, includeCodas, 
                isDeferedSyntaxAllowedAsLiteral, isTrimDirectiveWhitespaces,
                defaultContentType, buffer, errorOnUndeclaredNamespace);
    }

    /**
     * To find out if an uri matches an url pattern in jsp config.  If so,
     * then the uri is a JSP page.  This is used primarily for jspc.
     */
    public boolean isJspPage(String uri) throws JasperException {

        init();
        if (jspProperties == null) {
            return false;
        }

        String uriPath = null;
        int index = uri.lastIndexOf('/');
        if (index >=0 ) {
            uriPath = uri.substring(0, index+1);
        }
        String uriExtension = null;
        index = uri.lastIndexOf('.');
        if (index >=0) {
            uriExtension = uri.substring(index+1);
        }

        Iterator<JspPropertyGroup> iter = jspProperties.iterator();
        while (iter.hasNext()) {

            JspPropertyGroup jpg = iter.next();

            String extension = jpg.getExtension();
            String path = jpg.getPath();

            if (extension == null) {
                if (uri.equals(path)) {
                    // There is an exact match
                    return true;
                }
            } else {
                if ((path == null || path.equals(uriPath)) &&
                        (extension.equals("*") || extension.equals(uriExtension))) {
                    // Matches *, *.ext, /p/*, or /p/*.ext
                    return true;
                }
            }
        }
        return false;
    }

    public static class JspPropertyGroup {
        private String path;
        private String extension;
        private JspProperty jspProperty;

        JspPropertyGroup(String path, String extension,
                JspProperty jspProperty) {
            this.path = path;
            this.extension = extension;
            this.jspProperty = jspProperty;
        }

        public String getPath() {
            return path;
        }

        public String getExtension() {
            return extension;
        }

        public JspProperty getJspProperty() {
            return jspProperty;
        }
    }

    public static class JspProperty {

        private String isXml;
        private String elIgnored;
        private String scriptingInvalid;
        private String pageEncoding;
        private Vector<String> includePrelude;
        private Vector<String> includeCoda;
        private String deferedSyntaxAllowedAsLiteral;
        private String trimDirectiveWhitespaces;
        private String defaultContentType;
        private String buffer;
        private String errorOnUndeclaredNamespace;

        public JspProperty(String isXml, String elIgnored,
                String scriptingInvalid, String pageEncoding,
                Vector<String> includePrelude, Vector<String> includeCoda,
                String deferedSyntaxAllowedAsLiteral, 
                String trimDirectiveWhitespaces,
                String defaultContentType,
                String buffer,
                String errorOnUndeclaredNamespace) {

            this.isXml = isXml;
            this.elIgnored = elIgnored;
            this.scriptingInvalid = scriptingInvalid;
            this.pageEncoding = pageEncoding;
            this.includePrelude = includePrelude;
            this.includeCoda = includeCoda;
            this.deferedSyntaxAllowedAsLiteral = deferedSyntaxAllowedAsLiteral;
            this.trimDirectiveWhitespaces = trimDirectiveWhitespaces;
            this.defaultContentType = defaultContentType;
            this.buffer = buffer;
            this.errorOnUndeclaredNamespace = errorOnUndeclaredNamespace;
        }

        public String isXml() {
            return isXml;
        }

        public String isELIgnored() {
            return elIgnored;
        }

        public String isScriptingInvalid() {
            return scriptingInvalid;
        }

        public String getPageEncoding() {
            return pageEncoding;
        }

        public Vector<String> getIncludePrelude() {
            return includePrelude;
        }

        public Vector<String> getIncludeCoda() {
            return includeCoda;
        }
        
        public String isDeferedSyntaxAllowedAsLiteral() {
            return deferedSyntaxAllowedAsLiteral;
        }
        
        public String isTrimDirectiveWhitespaces() {
            return trimDirectiveWhitespaces;
        }
        
        public String getDefaultContentType() {
            return defaultContentType;
        }
        
        public String getBuffer() {
            return buffer;
        }
        
        public String isErrorOnUndeclaredNamespace() {
            return errorOnUndeclaredNamespace;
        }
    }
}
