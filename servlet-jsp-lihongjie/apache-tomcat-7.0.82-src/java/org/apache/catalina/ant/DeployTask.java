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


package org.apache.catalina.ant;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Pattern;

import org.apache.tools.ant.BuildException;


/**
 * Ant task that implements the <code>/deploy</code> command, supported by
 * the Tomcat manager application.
 *
 * @author Craig R. McClanahan
 * @since 4.1
 */
public class DeployTask extends AbstractCatalinaCommandTask {
    private static final Pattern PROTOCOL_PATTERN = Pattern.compile("\\w{3,5}\\:");


    // ------------------------------------------------------------- Properties


    /**
     * URL of the context configuration file for this application, if any.
     */
    protected String config = null;

    public String getConfig() {
        return (this.config);
    }

    public void setConfig(String config) {
        this.config = config;
    }


    /**
     * URL of the server local web application archive (WAR) file 
     * to be deployed.
     */
    protected String localWar = null;

    public String getLocalWar() {
        return (this.localWar);
    }

    public void setLocalWar(String localWar) {
        this.localWar = localWar;
    }


    /**
     * Tag to associate with this to be deployed webapp.
     */
    protected String tag = null;

    public String getTag() {
        return (this.tag);
    }

    public void setTag(String tag) {
        this.tag = tag;
    }


    /**
     * Update existing webapps.
     */
    protected boolean update = false;

    public boolean getUpdate() {
        return (this.update);
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }


    /**
     * URL of the web application archive (WAR) file to be deployed.
     */
    protected String war = null;

    public String getWar() {
        return (this.war);
    }

    public void setWar(String war) {
        this.war = war;
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Execute the requested operation.
     *
     * @exception BuildException if an error occurs
     */
    @Override
    public void execute() throws BuildException {

        super.execute();
        if (path == null) {
            throw new BuildException
                ("Must specify 'path' attribute");
        }
        if ((war == null) && (localWar == null) && (config == null) && (tag == null)) {
            throw new BuildException
                ("Must specify either 'war', 'localWar', 'config', or 'tag' attribute");
        }

        // Building an input stream on the WAR to upload, if any
        BufferedInputStream stream = null;
        String contentType = null;
        int contentLength = -1;
        if (war != null) {
            if (PROTOCOL_PATTERN.matcher(war).lookingAt()) {
                try {
                    URL url = new URL(war);
                    URLConnection conn = url.openConnection();
                    contentLength = conn.getContentLength();
                    stream = new BufferedInputStream
                        (conn.getInputStream(), 1024);
                } catch (IOException e) {
                    throw new BuildException(e);
                }
            } else {
                FileInputStream fsInput = null;
                try {
                    fsInput = new FileInputStream(war);
                    long size = fsInput.getChannel().size();

                    if (size > Integer.MAX_VALUE)
                        throw new UnsupportedOperationException(
                                "DeployTask does not support WAR files " +
                                "greater than 2 Gb");
                    contentLength = (int) size;

                    stream = new BufferedInputStream(fsInput, 1024);

                } catch (IOException e) {
                    if (fsInput != null) {
                        try {
                            fsInput.close();
                        } catch (IOException ioe) {
                            // Ignore
                        }
                    }
                    throw new BuildException(e);
                }
            }
            contentType = "application/octet-stream";
        }

        // Building URL
        StringBuilder sb = createQueryString("/deploy");
        try {
            if ((war == null) && (config != null)) {
                sb.append("&config=");
                sb.append(URLEncoder.encode(config, getCharset()));
            }
            if ((war == null) && (localWar != null)) {
                sb.append("&war=");
                sb.append(URLEncoder.encode(localWar, getCharset()));
            }
            if (update) {
                sb.append("&update=true");
            }
            if (tag != null) {
                sb.append("&tag=");
                sb.append(URLEncoder.encode(tag, getCharset()));
            }

            execute(sb.toString(), stream, contentType, contentLength);
        } catch (UnsupportedEncodingException e) {
            throw new BuildException("Invalid 'charset' attribute: " + getCharset());
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException ioe) {
                    // Ignore
                }
            }
        }

    }


}
