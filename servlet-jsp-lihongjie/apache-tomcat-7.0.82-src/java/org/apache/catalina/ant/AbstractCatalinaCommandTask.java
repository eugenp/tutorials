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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.tools.ant.BuildException;

public abstract class AbstractCatalinaCommandTask extends AbstractCatalinaTask {

    /**
     * The context path of the web application we are managing.
     */
    protected String path = null;


    public String getPath() {
        return (this.path);
    }


    public void setPath(String path) {
        this.path = path;
    }

    /**
     * The context version of the web application we are managing.
     */
    protected String version = null;


    public String getVersion() {
        return (this.version);
    }


    public void setVersion(String version) {
        this.version = version;
    }


    // --------------------------------------------------------- Public Methods
    /**
     * Create query string for the specified command.
     *
     * @param command Command to be executed
     *
     * @return The generated query string
     *
     * @exception BuildException if an error occurs
     */
    public StringBuilder createQueryString(String command) throws BuildException {
        StringBuilder buffer = new StringBuilder();
        try {
            buffer.append(command);
            if (path == null) {
                throw new BuildException("Must specify 'path' attribute");
            } else {
                buffer.append("?path=");
                buffer.append(URLEncoder.encode(this.path, getCharset()));
                if (this.version != null) {
                    buffer.append("&version=");
                    buffer.append(URLEncoder.encode(this.version, getCharset()));
                }
            }
        } catch (UnsupportedEncodingException e) {
            throw new BuildException("Invalid 'charset' attribute: " + getCharset());
        }
        return buffer;
    }
}