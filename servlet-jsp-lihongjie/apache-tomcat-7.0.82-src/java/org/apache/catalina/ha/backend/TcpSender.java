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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.StringTokenizer;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/*
 * Sender to proxies using multicast socket.
 */
public class TcpSender
    implements Sender {

    private static final Log log = LogFactory.getLog(HeartbeatListener.class);

    HeartbeatListener config = null;

    /**
     * Proxies.
     */
    protected Proxy[] proxies = null;


    /**
     * Active connections.
     */

    protected Socket[] connections = null;
    protected BufferedReader[] connectionReaders = null;
    protected BufferedWriter[] connectionWriters = null;


    @Override
    public void init(HeartbeatListener config) throws Exception {
        this.config = config;
        StringTokenizer tok = new StringTokenizer(config.getProxyList(), ",");
        proxies = new Proxy[tok.countTokens()];
        int i = 0;
        while (tok.hasMoreTokens()) {
            String token = tok.nextToken().trim();
            int pos = token.indexOf(':');
            if (pos <=0)
                throw new Exception("bad ProxyList");
            proxies[i] = new Proxy();
            proxies[i].port = Integer.parseInt(token.substring(pos + 1));
            try {
                 proxies[i].address = InetAddress.getByName(token.substring(0, pos));
            } catch (Exception e) {
                throw new Exception("bad ProxyList");
            }
            i++;
        }
        connections = new Socket[proxies.length];
        connectionReaders = new BufferedReader[proxies.length];
        connectionWriters = new BufferedWriter[proxies.length];

    }

    @Override
    public int send(String mess) throws Exception {
        if (connections == null) {
            log.error("Not initialized");
            return -1;
        }
        String requestLine = "POST " + config.getProxyURL() + " HTTP/1.0";

        for (int i = 0; i < connections.length; i++) {
            if (connections[i] == null) {
                try {
                    if (config.host != null) {
                        connections[i] = new Socket();
                        InetAddress addr =  InetAddress.getByName(config.host);
                        InetSocketAddress addrs = new InetSocketAddress(addr, 0);
                        connections[i].setReuseAddress(true);
                        connections[i].bind(addrs);
                        addrs = new InetSocketAddress(proxies[i].address, proxies[i].port);
                        connections[i].connect(addrs);
                    } else 
                        connections[i] = new Socket(proxies[i].address, proxies[i].port);
                    connectionReaders[i] = new BufferedReader(new InputStreamReader(connections[i].getInputStream()));
                    connectionWriters[i] = new BufferedWriter(new OutputStreamWriter(connections[i].getOutputStream()));
                } catch (Exception ex) {
                    log.error("Unable to connect to proxy: " + ex);
                    close(i);
                } 
            }
            if (connections[i] == null)
                continue; // try next proxy in the list
            BufferedWriter writer = connectionWriters[i];
            try {
                writer.write(requestLine); 
                writer.write("\r\n");
                writer.write("Content-Length: " + mess.length() + "\r\n");
                writer.write("User-Agent: HeartbeatListener/1.0\r\n");
                writer.write("Connection: Keep-Alive\r\n");
                writer.write("\r\n");
                writer.write(mess);
                writer.write("\r\n");
                writer.flush();
            } catch (Exception ex) {
                log.error("Unable to send collected load information to proxy: " + ex);
                close(i);
            } 
            if (connections[i] == null)
                continue; // try next proxy in the list
            
            /* Read httpd answer */
            String responseStatus = connectionReaders[i].readLine();
            if (responseStatus == null) {
                log.error("Unable to read response from proxy");
                close(i);
                continue;
            } else {
                responseStatus = responseStatus.substring(responseStatus.indexOf(' ') + 1, responseStatus.indexOf(' ', responseStatus.indexOf(' ') + 1));
                int status = Integer.parseInt(responseStatus);
                if (status != 200) {
                    log.error("Status is " + status);
                    close(i);
                    continue;
                }

                // read all the headers.
                String header = connectionReaders[i].readLine();
                int contentLength = 0;
                while (!"".equals(header)) {
                    int colon = header.indexOf(':');
                    String headerName = header.substring(0, colon).trim();
                    String headerValue = header.substring(colon + 1).trim();
                    if ("content-length".equalsIgnoreCase(headerName)) {
                        contentLength = Integer.parseInt(headerValue);
                    }
                    header = connectionReaders[i].readLine();
                }
                if (contentLength > 0) {
                    char[] buf = new char[512];
                    while (contentLength > 0) {
                        int thisTime = (contentLength > buf.length) ? buf.length : contentLength;
                        int n = connectionReaders[i].read(buf, 0, thisTime);
                        if (n <= 0) {
                            log.error("Read content failed");
                            close(i);
                            break;
                        } else {
                            contentLength -= n;
                        }
                   }
                }
            }
               
        }

        return 0;
    }

    /**
     * Close connection.
     */
    protected void close(int i) {
        try {
            if (connectionReaders[i] != null) {
                connectionReaders[i].close();
            }
        } catch (IOException e) {
        }
        connectionReaders[i] = null;
        try {
            if (connectionWriters[i] != null) {
                connectionWriters[i].close();
            }
        } catch (IOException e) {
        }
        connectionWriters[i] = null;
        try {
            if (connections[i] != null) {
                connections[i].close();
            }
        } catch (IOException e) {
        }
        connections[i] = null;
    }
}
