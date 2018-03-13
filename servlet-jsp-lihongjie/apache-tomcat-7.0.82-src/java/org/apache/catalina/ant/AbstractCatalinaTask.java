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

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;

/**
 * Abstract base class for Ant tasks that interact with the <em>Manager</em> web
 * application for dynamically deploying and undeploying applications. These
 * tasks require Ant 1.4 or later.
 *
 * @author Craig R. McClanahan
 * @since 4.1
 */
public abstract class AbstractCatalinaTask extends BaseRedirectorHelperTask {

    /**
     * manager webapp's encoding.
     */
    private static String CHARSET = "utf-8";


    /**
     * The charset used during URL encoding.
     */
    protected String charset = "ISO-8859-1";

    public String getCharset() {
        return this.charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }


    /**
     * The login password for the <code>Manager</code> application.
     */
    protected String password = null;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    /**
     * The URL of the <code>Manager</code> application to be used.
     */
    protected String url = "http://localhost:8080/manager/text";

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    /**
     * The login username for the <code>Manager</code> application.
     */
    protected String username = null;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    /**
     * Execute the specified command. This logic only performs the common
     * attribute validation required by all subclasses; it does not perform any
     * functional logic directly.
     *
     * @exception BuildException if a validation error occurs
     */
    @Override
    public void execute() throws BuildException {
        if ((username == null) || (password == null) || (url == null)) {
            throw new BuildException("Must specify all of 'username', 'password', and 'url'");
        }
    }


    /**
     * Execute the specified command, based on the configured properties.
     *
     * @param command Command to be executed
     *
     * @exception BuildException if an error occurs
     */
    public void execute(String command) throws BuildException {
        execute(command, null, null, -1);
    }


    /**
     * Execute the specified command, based on the configured properties. The
     * input stream will be closed upon completion of this task, whether it was
     * executed successfully or not.
     *
     * @param command Command to be executed
     * @param istream InputStream to include in an HTTP PUT, if any
     * @param contentType Content type to specify for the input, if any
     * @param contentLength Content length to specify for the input, if any
     *
     * @exception BuildException if an error occurs
     */
    public void execute(String command, InputStream istream, String contentType, int contentLength)
                    throws BuildException {
        URLConnection conn = null;
        InputStreamReader reader = null;
        try {
            // Create a connection for this command
            conn = (new URL(url + command)).openConnection();
            HttpURLConnection hconn = (HttpURLConnection) conn;
            // Set up standard connection characteristics
            hconn.setAllowUserInteraction(false);
            hconn.setDoInput(true);
            hconn.setUseCaches(false);
            if (istream != null) {
                hconn.setDoOutput(true);
                hconn.setRequestMethod("PUT");
                if (contentType != null) {
                    hconn.setRequestProperty("Content-Type", contentType);
                }
                if (contentLength >= 0) {
                    hconn.setRequestProperty("Content-Length", "" + contentLength);
                    hconn.setFixedLengthStreamingMode(contentLength);
                }
            } else {
                hconn.setDoOutput(false);
                hconn.setRequestMethod("GET");
            }
            hconn.setRequestProperty("User-Agent", "Catalina-Ant-Task/1.0");

            // Set up authorization with our credentials
            Authenticator.setDefault(new TaskAuthenticator(username, password));

            // Establish the connection with the server
            hconn.connect();
            // Send the request data (if any)
            if (istream != null) {
                BufferedOutputStream ostream = null;
                try {
                    ostream = new BufferedOutputStream(hconn.getOutputStream(), 1024);
                    byte buffer[] = new byte[1024];
                    while (true) {
                        int n = istream.read(buffer);
                        if (n < 0) {
                            break;
                        }
                        ostream.write(buffer, 0, n);
                    }
                    ostream.flush();
                } finally {
                    if (ostream != null) {
                        try {
                            ostream.close();
                        } catch (IOException e) {
                        }
                    }
                    try {
                        istream.close();
                    } catch (IOException e) {
                    }
                }
            }
            // Process the response message
            reader = new InputStreamReader(hconn.getInputStream(), CHARSET);
            StringBuilder buff = new StringBuilder();
            String error = null;
            int msgPriority = Project.MSG_INFO;
            boolean first = true;
            while (true) {
                int ch = reader.read();
                if (ch < 0) {
                    break;
                } else if ((ch == '\r') || (ch == '\n')) {
                    // in Win \r\n would cause handleOutput() to be called
                    // twice, the second time with an empty string,
                    // producing blank lines
                    if (buff.length() > 0) {
                        String line = buff.toString();
                        buff.setLength(0);
                        if (first) {
                            if (!line.startsWith("OK -")) {
                                error = line;
                                msgPriority = Project.MSG_ERR;
                            }
                            first = false;
                        }
                        handleOutput(line, msgPriority);
                    }
                } else {
                    buff.append((char) ch);
                }
            }
            if (buff.length() > 0) {
                handleOutput(buff.toString(), msgPriority);
            }
            if (error != null && isFailOnError()) {
                // exception should be thrown only if failOnError == true
                // or error line will be logged twice
                throw new BuildException(error);
            }
        } catch (Exception e) {
            if (isFailOnError()) {
                throw new BuildException(e);
            } else {
                handleErrorOutput(e.getMessage());
            }
        } finally {
            closeRedirector();
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioe) {
                    // Ignore
                }
                reader = null;
            }
            if (istream != null) {
                try {
                    istream.close();
                } catch (IOException ioe) {
                    // Ignore
                }
            }
        }
    }


    private static class TaskAuthenticator extends Authenticator {

        private final String user;
        private final String password;

        private TaskAuthenticator(String user, String password) {
            this.user = user;
            this.password = password;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, password.toCharArray());
        }
    }
}
