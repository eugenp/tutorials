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


package org.apache.catalina.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.tomcat.util.ExceptionUtils;


/**
 * Simple utility module to make it easy to plug in the server identifier
 * when integrating Tomcat.
 *
 * @author Craig R. McClanahan
 */
public class ServerInfo {


    // ------------------------------------------------------- Static Variables


    /**
     * The server information String with which we identify ourselves.
     */
    private static String serverInfo = null;

    /**
     * The server built String.
     */
    private static String serverBuilt = null;

    /**
     * The server's version number String.
     */
    private static String serverNumber = null;

    static {

        Properties props = new Properties();
        InputStream is = null;
        try {
            is = ServerInfo.class.getResourceAsStream
                    ("/org/apache/catalina/util/ServerInfo.properties");
            props.load(is);
            serverInfo = props.getProperty("server.info");
            serverBuilt = props.getProperty("server.built");
            serverNumber = props.getProperty("server.number");
        } catch (Throwable t) {
            ExceptionUtils.handleThrowable(t);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
        }
        if (serverInfo == null)
            serverInfo = "Apache Tomcat 7.0.x-dev";
        if (serverBuilt == null)
            serverBuilt = "unknown";
        if (serverNumber == null)
            serverNumber = "7.0.x";
        
    }


    // --------------------------------------------------------- Public Methods


    /**
     * Return the server identification for this version of Tomcat.
     */
    public static String getServerInfo() {

        return (serverInfo);

    }

    /**
     * Return the server built time for this version of Tomcat.
     */
    public static String getServerBuilt() {

        return (serverBuilt);

    }

    /**
     * Return the server's version number.
     */
    public static String getServerNumber() {

        return (serverNumber);

    }

    public static void main(String args[]) {
        System.out.println("Server version: " + getServerInfo());
        System.out.println("Server built:   " + getServerBuilt());
        System.out.println("Server number:  " + getServerNumber());
        System.out.println("OS Name:        " +
                           System.getProperty("os.name"));
        System.out.println("OS Version:     " +
                           System.getProperty("os.version"));
        System.out.println("Architecture:   " +
                           System.getProperty("os.arch"));
        System.out.println("JVM Version:    " +
                           System.getProperty("java.runtime.version"));
        System.out.println("JVM Vendor:     " +
                           System.getProperty("java.vm.vendor"));                        
    }

}
