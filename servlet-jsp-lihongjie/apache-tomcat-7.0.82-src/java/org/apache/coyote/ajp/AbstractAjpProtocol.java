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
package org.apache.coyote.ajp;

import org.apache.coyote.AbstractProtocol;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.upgrade.servlet31.HttpUpgradeHandler;
import org.apache.tomcat.util.net.SocketWrapper;
import org.apache.tomcat.util.res.StringManager;

public abstract class AbstractAjpProtocol<S> extends AbstractProtocol<S> {
    
    /**
     * The string manager for this package.
     */
    protected static final StringManager sm =
        StringManager.getManager(Constants.Package);


    @Override
    protected String getProtocolName() {
        return "Ajp";
    }



    // ------------------------------------------------- AJP specific properties
    // ------------------------------------------ managed in the ProtocolHandler
    
    /**
     * Send AJP flush packet when flushing.
     * An flush packet is a zero byte AJP13 SEND_BODY_CHUNK
     * packet. mod_jk and mod_proxy_ajp interprete this as
     * a request to flush data to the client.
     * AJP always does flush at the and of the response, so if
     * it is not important, that the packets get streamed up to
     * the client, do not use extra flush packets.
     * For compatibility and to stay on the safe side, flush
     * packets are enabled by default.
     */
    protected boolean ajpFlush = true;
    public boolean getAjpFlush() { return ajpFlush; }
    public void setAjpFlush(boolean ajpFlush) {
        this.ajpFlush = ajpFlush;
    }


    /**
     * Should authentication be done in the native web server layer,
     * or in the Servlet container ?
     */
    protected boolean tomcatAuthentication = true;
    public boolean getTomcatAuthentication() { return tomcatAuthentication; }
    public void setTomcatAuthentication(boolean tomcatAuthentication) {
        this.tomcatAuthentication = tomcatAuthentication;
    }


    /**
     * Should authentication be done in the native web server layer and
     * authorization in the Servlet container?
     */
    private boolean tomcatAuthorization = false;
    public boolean getTomcatAuthorization() { return tomcatAuthorization; }
    public void setTomcatAuthorization(boolean tomcatAuthorization) {
        this.tomcatAuthorization = tomcatAuthorization;
    }


    /**
     * Required secret.
     */
    protected String requiredSecret = null;
    public void setRequiredSecret(String requiredSecret) {
        this.requiredSecret = requiredSecret;
    }


    /**
     * AJP packet size.
     */
    protected int packetSize = Constants.MAX_PACKET_SIZE;
    public int getPacketSize() { return packetSize; }
    public void setPacketSize(int packetSize) {
        if(packetSize < Constants.MAX_PACKET_SIZE) {
            this.packetSize = Constants.MAX_PACKET_SIZE;
        } else {
            this.packetSize = packetSize;
        }
    }

    protected abstract static class AbstractAjpConnectionHandler<S,P extends AbstractAjpProcessor<S>>
            extends AbstractConnectionHandler<S, P> {

        @Override
        protected void initSsl(SocketWrapper<S> socket, Processor<S> processor) {
            // NOOP for AJP
        }

        @Override
        protected void longPoll(SocketWrapper<S> socket,
                Processor<S> processor) {
            // Same requirements for all AJP connectors
            socket.setAsync(true);
        }

        /**
         * @deprecated  Will be removed in Tomcat 8.0.x.
         */
        @Deprecated
        @Override
        protected P createUpgradeProcessor(SocketWrapper<S> socket,
                org.apache.coyote.http11.upgrade.UpgradeInbound inbound) {
            // TODO should fail - throw IOE
            return null;
        }
        
        @Override
        protected P createUpgradeProcessor(SocketWrapper<S> socket,
                HttpUpgradeHandler httpUpgradeHandler) {
            // TODO should fail - throw IOE
            return null;
        }
    }
}
