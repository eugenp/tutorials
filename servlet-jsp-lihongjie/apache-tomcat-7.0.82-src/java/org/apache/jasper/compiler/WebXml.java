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

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import org.apache.jasper.Constants;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.xml.sax.InputSource;

/**
 * Provides Jasper with a standard mechanism for gaining access to the web.xml
 * file associated with the current application. This isn't as simple as looking
 * in /WEB-INF/web.xml. In embedded scenarios, an alternative web.xml may be
 * provided and in Servlet 3.0 / JSP 2.2 environments an application's web.xml
 * may be the result of merging a number of web-fragment.xml files and/or
 * annotations with the main web.xml
 * 
 * Clients *must* ensure that they call {@link #close()} to clean up resources. 
 */
public class WebXml {
    private static final String FILE_PROTOCOL = "file:";
    private static final String WEB_XML = "/WEB-INF/web.xml";

    private final Log log = LogFactory.getLog(WebXml.class);
            
    private InputStream stream;
    private InputSource source;
    private String systemId;

    public WebXml(ServletContext ctxt) throws IOException {
        // Is a web.xml provided as context attribute?
        String webXml = (String) ctxt.getAttribute(
                org.apache.tomcat.util.scan.Constants.MERGED_WEB_XML);
        if (webXml != null) {
            source = new InputSource(new StringReader(webXml));
            systemId = org.apache.tomcat.util.scan.Constants.MERGED_WEB_XML;
        }
        
        // If not available as context attribute, look for an alternative
        // location
        if (source == null) {
            // Acquire input stream to web application deployment descriptor
            String altDDName = (String)ctxt.getAttribute(
                                                    Constants.ALT_DD_ATTR);
            if (altDDName != null) {
                try {
                    URL uri =
                        new URL(FILE_PROTOCOL+altDDName.replace('\\', '/'));
                    stream = uri.openStream();
                    source = new InputSource(stream);
                    systemId = uri.toExternalForm();
                } catch (MalformedURLException e) {
                    log.warn(Localizer.getMessage(
                            "jsp.error.internal.filenotfound",
                            altDDName));
                }
            }
        }
        
        // Finally, try the default /WEB-INF/web.xml
        if (source == null) {
            URL uri = ctxt.getResource(WEB_XML);
            if (uri == null) {
                log.warn(Localizer.getMessage(
                        "jsp.error.internal.filenotfound", WEB_XML));
            } else {
                stream = uri.openStream();
                source = new InputSource(stream);
                systemId = uri.toExternalForm();
            }
        }

        if (source == null) {
            systemId = null;
        } else {
            source.setSystemId(systemId);
        }
    }
    
    public String getSystemId() {
        return systemId;
    }

    public InputSource getInputSource() {
        return source;
    }

    public void close() {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                log.error(Localizer.getMessage(
                        "jsp.error.stream.close.failed"));
            }
        }
    }
}
