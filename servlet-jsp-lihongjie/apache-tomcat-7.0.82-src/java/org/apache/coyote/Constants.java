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


/**
 * Constants.
 *
 * @author Remy Maucherat
 */
public final class Constants {


    // -------------------------------------------------------------- Constants

    public static final String Package = "org.apache.coyote";
    
    public static final String DEFAULT_CHARACTER_ENCODING="ISO-8859-1";

    public static final int MAX_NOTES = 32;


    // Request states
    public static final int STAGE_NEW = 0;
    public static final int STAGE_PARSE = 1;
    public static final int STAGE_PREPARE = 2;
    public static final int STAGE_SERVICE = 3;
    public static final int STAGE_ENDINPUT = 4;
    public static final int STAGE_ENDOUTPUT = 5;
    public static final int STAGE_KEEPALIVE = 6;
    public static final int STAGE_ENDED = 7;


    /**
     * Has security been turned on?
     */
    public static final boolean IS_SECURITY_ENABLED =
        (System.getSecurityManager() != null);


    /**
     * If true, custom HTTP status messages will be used in headers.
     */
    public static final boolean USE_CUSTOM_STATUS_MSG_IN_HEADER =
            Boolean.getBoolean("org.apache.coyote.USE_CUSTOM_STATUS_MSG_IN_HEADER");

    /**
     * The request attribute that is set to the value of {@code Boolean.TRUE}
     * if connector processing this request supports Comet API.
     */
    public static final String COMET_SUPPORTED_ATTR =
        "org.apache.tomcat.comet.support";


    /**
     * The request attribute that is set to the value of {@code Boolean.TRUE}
     * if connector processing this request supports setting
     * per-connection request timeout through Comet API.
     *
     * @see org.apache.catalina.comet.CometEvent#setTimeout(int)
     */
    public static final String COMET_TIMEOUT_SUPPORTED_ATTR =
        "org.apache.tomcat.comet.timeout.support";


    /**
     * The request attribute that can be set to a value of type
     * {@code java.lang.Integer} to specify per-connection request
     * timeout for Comet API. The value is in milliseconds.
     *
     * @see org.apache.catalina.comet.CometEvent#setTimeout(int)
     */
    public static final String COMET_TIMEOUT_ATTR =
        "org.apache.tomcat.comet.timeout";


    /**
     * The request attribute that is set to the value of {@code Boolean.TRUE}
     * if connector processing this request supports use of sendfile.
     */
    public static final String SENDFILE_SUPPORTED_ATTR =
        "org.apache.tomcat.sendfile.support";


    /**
     * The request attribute that can be used by a servlet to pass
     * to the connector the name of the file that is to be served
     * by sendfile. The value should be {@code java.lang.String}
     * that is {@code File.getCanonicalPath()} of the file to be served.
     */
    public static final String SENDFILE_FILENAME_ATTR =
        "org.apache.tomcat.sendfile.filename";


    /**
     * The request attribute that can be used by a servlet to pass
     * to the connector the start offset of the part of a file
     * that is to be served by sendfile. The value should be
     * {@code java.lang.Long}. To serve complete file
     * the value should be {@code Long.valueOf(0)}.
     */
    public static final String SENDFILE_FILE_START_ATTR =
        "org.apache.tomcat.sendfile.start";


    /**
     * The request attribute that can be used by a servlet to pass
     * to the connector the end offset (not including) of the part
     * of a file that is to be served by sendfile. The value should be
     * {@code java.lang.Long}. To serve complete file
     * the value should be equal to the length of the file.
     */
    public static final String SENDFILE_FILE_END_ATTR =
        "org.apache.tomcat.sendfile.end";


    /**
     * The request attribute set by the RemoteIpFilter, RemoteIpValve (and may
     * be set by other similar components) that identifies for the connector the
     * remote IP address claimed to be associated with this request when a
     * request is received via one or more proxies. It is typically provided via
     * the X-Forwarded-For HTTP header.
     */
    public static final String REMOTE_ADDR_ATTRIBUTE =
            "org.apache.tomcat.remoteAddr";
}
