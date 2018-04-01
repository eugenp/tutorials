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


package org.apache.catalina.ha.backend;

import org.apache.catalina.ContainerEvent;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleEvent;
import org.apache.catalina.LifecycleListener;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/*
 * Listener to provider informations to mod_heartbeat.c
 * *msg_format = "v=%u&ready=%u&busy=%u"; (message to send).
 * send the multicast message using the format...
 * what about the bind(IP. port) only IP makes sense (for the moment).
 * BTW:v  = version :-)
 */
public class HeartbeatListener
    implements LifecycleListener, ContainerListener {

    private static final Log log = LogFactory.getLog(HeartbeatListener.class);

    /* To allow to select the connector */
    int port = 0;
    String host = null;
    public void setHost(String host) { this.host = host; }
    public void setPort(int port) { this.port = port; }

    /* for multicasting stuff */
    String ip = "224.0.1.105"; /* Multicast IP */
    int multiport = 23364;     /* Multicast Port */
    int ttl = 16;

    public void setGroup(String ip) { this.ip = ip; }
    public String getGroup() { return ip; }
    public void setMultiport(int multiport) { this.multiport = multiport; }
    public int getMultiport() { return multiport; }
    public void setTtl(int ttl) { this.ttl = ttl; }
    public int getTtl() { return ttl; }

    /**
     * Proxy list, format "address:port,address:port".
     */
    protected String proxyList = null;
    public String getProxyList() { return proxyList; }
    public void setProxyList(String proxyList) { this.proxyList = proxyList; }

    /**
     * URL prefix.
     */
    protected String proxyURL = "/HeartbeatListener";
    public String getProxyURL() { return proxyURL; }
    public void setProxyURL(String proxyURL) { this.proxyURL = proxyURL; }

    private CollectedInfo coll = null;

    private Sender sender = null;

    @Override
    public void containerEvent(ContainerEvent event) {
    }

    @Override
    public void lifecycleEvent(LifecycleEvent event) {

        if (Lifecycle.PERIODIC_EVENT.equals(event.getType())) {
            if (sender == null) {
                if (proxyList == null)
                    sender = new MultiCastSender();
                else
                    sender = new TcpSender();
            }

            /* Read busy and ready */
            if (coll == null) {
                try {
                    coll = new CollectedInfo(host, port);
                    this.port = coll.port;
                    this.host = coll.host;
                } catch (Exception ex) {
                    log.error("Unable to initialize info collection: " + ex);
                    coll = null;
                    return;
                } 
            }

            /* Start or restart sender */
            try {
                sender.init(this);
            } catch (Exception ex) {
                log.error("Unable to initialize Sender: " + ex);
                sender = null;
                return;
            }

            /* refresh the connector information and send it */
            try {
                coll.refresh();
            } catch (Exception ex) {
                log.error("Unable to collect load information: " + ex);
                coll = null;
                return;
            }
            String output = "v=1&ready=" + coll.ready + "&busy=" + coll.busy +
                    "&port=" + port;
            try {
                sender.send(output);
            } catch (Exception ex) {
                log.error("Unable to send collected load information: " + ex);
            }
        }
    }

}
