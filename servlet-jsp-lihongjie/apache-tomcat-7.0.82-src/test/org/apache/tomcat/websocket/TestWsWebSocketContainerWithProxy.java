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
package org.apache.tomcat.websocket;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;

@Ignore // Additional infrastructure is required to run this test
public class TestWsWebSocketContainerWithProxy extends TestWsWebSocketContainer {

    @BeforeClass
    public static void init() {
        // Set the system properties for a HTTP proxy on 192.168.0.100:80
        // I used an httpd instance configured as an open forward proxy for this
        // Update the IP/hostname as required
        System.setProperty("http.proxyHost", "192.168.0.100");
        System.setProperty("http.proxyPort", "80");
        System.setProperty("http.nonProxyHosts", "");
    }

    @Before
    public void setPort() {
        // With httpd 2.2, AllowCONNECT requires fixed ports. From 2.4, a range
        // can be used.
        getTomcatInstance().getConnector().setPort(8080);
        getTomcatInstance().getConnector().setProperty("address","0.0.0.0");
    }

    @Override
    protected String getHostName() {
        // The IP/hostname where the tests are running. The proxy will connect
        // back to this expecting to find the Tomcat instance created by the
        // unit test.
        return "192.168.0.200";
    }
}
