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
package org.apache.tomcat.util.net;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.TrustManager;

public interface SSLUtil {

    public SSLContext createSSLContext() throws Exception;

    public KeyManager[] getKeyManagers() throws Exception;

    public TrustManager[] getTrustManagers() throws Exception;

    public void configureSessionContext(SSLSessionContext sslSessionContext);

    /**
     * Determines the SSL cipher suites that can be enabled, based on the
     * configuration of the endpoint and the ciphers supported by the SSL
     * implementation.
     *
     * @param context An initialized context to obtain the supported ciphers from.
     *
     * @return Array of SSL cipher suites that may be enabled (which may be
     *         empty if none of the specified ciphers are supported), or
     *         the defaults for the underlying SSL implementation if 
     *         the endpoint configuration does not specify any ciphers.
     */
    public String[] getEnableableCiphers(SSLContext context);

    /**
     * Determines the SSL protocol variants that can be enabled, based on the
     * configuration of the endpoint and the ciphers supported by the SSL
     * implementation.
     *
     * @param context An initialized context to obtain the supported protocols from.
     *
     * @return Array of SSL protocol variants that may be enabled (which may be
     *         empty if none of the specified protocols are supported), or
     *         the defaults for the underlying SSL implementation if 
     *         the endpoint configuration does not specify any protocols.
     */
    public String[] getEnableableProtocols(SSLContext context);
}
