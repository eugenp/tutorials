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

package org.apache.catalina.tribes.transport;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.catalina.tribes.Member;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class AbstractSender implements DataSender {

    private boolean connected = false;
    private int rxBufSize = 25188;
    private int txBufSize = 43800;
    private int udpRxBufSize = 25188;
    private int udpTxBufSize = 43800;
    private boolean directBuffer = false;
    private int keepAliveCount = -1;
    private int requestCount = 0;
    private long connectTime;
    private long keepAliveTime = -1;
    private long timeout = 3000;
    private Member destination;
    private InetAddress address;
    private int port;
    private int maxRetryAttempts = 1;//1 resends
    private int attempt;
    private boolean tcpNoDelay = true;
    private boolean soKeepAlive = false;
    private boolean ooBInline = true;
    private boolean soReuseAddress = true;
    private boolean soLingerOn = false;
    private int soLingerTime = 3;
    private int soTrafficClass = 0x04 | 0x08 | 0x010;
    private boolean throwOnFailedAck = true;
    private boolean udpBased = false;
    private int udpPort = -1;

    /**
     * transfers sender properties from one sender to another
     * @param from AbstractSender
     * @param to AbstractSender
     */
    public static void transferProperties(AbstractSender from, AbstractSender to) {
        to.rxBufSize = from.rxBufSize;
        to.txBufSize = from.txBufSize;
        to.directBuffer = from.directBuffer;
        to.keepAliveCount = from.keepAliveCount;
        to.keepAliveTime = from.keepAliveTime;
        to.timeout = from.timeout;
        to.destination = from.destination;
        to.address = from.address;
        to.port = from.port;
        to.maxRetryAttempts = from.maxRetryAttempts;
        to.tcpNoDelay = from.tcpNoDelay;
        to.soKeepAlive = from.soKeepAlive;
        to.ooBInline = from.ooBInline;
        to.soReuseAddress = from.soReuseAddress;
        to.soLingerOn = from.soLingerOn;
        to.soLingerTime = from.soLingerTime;
        to.soTrafficClass = from.soTrafficClass;
        to.throwOnFailedAck = from.throwOnFailedAck;
        to.udpBased = from.udpBased;
        to.udpPort = from.udpPort;
    }


    public AbstractSender() {

    }

    /**
     * connect
     *
     * @throws IOException
     * TODO Implement this org.apache.catalina.tribes.transport.DataSender method
     */
    @Override
    public abstract void connect() throws IOException;

    /**
     * disconnect
     *
     * TODO Implement this org.apache.catalina.tribes.transport.DataSender method
     */
    @Override
    public abstract void disconnect();

    /**
     * keepalive
     *
     * @return boolean
     * TODO Implement this org.apache.catalina.tribes.transport.DataSender method
     */
    @Override
    public boolean keepalive() {
        boolean disconnect = false;
        if (isUdpBased()) disconnect = true; //always disconnect UDP, TODO optimize the keepalive handling
        else if ( keepAliveCount >= 0 && requestCount>keepAliveCount ) disconnect = true;
        else if ( keepAliveTime >= 0 && (System.currentTimeMillis()-connectTime)>keepAliveTime ) disconnect = true;
        if ( disconnect ) disconnect();
        return disconnect;
    }

    protected void setConnected(boolean connected){
        this.connected = connected;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public long getConnectTime() {
        return connectTime;
    }

    public Member getDestination() {
        return destination;
    }


    public int getKeepAliveCount() {
        return keepAliveCount;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    @Override
    public int getRequestCount() {
        return requestCount;
    }

    public int getRxBufSize() {
        return rxBufSize;
    }

    public long getTimeout() {
        return timeout;
    }

    public int getTxBufSize() {
        return txBufSize;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getMaxRetryAttempts() {
        return maxRetryAttempts;
    }

    public void setDirect(boolean direct) {
        setDirectBuffer(direct);
    }

    public void setDirectBuffer(boolean directBuffer) {
        this.directBuffer = directBuffer;
    }

    public boolean getDirect() {
        return getDirectBuffer();
    }

    public boolean getDirectBuffer() {
        return this.directBuffer;
    }

    public int getAttempt() {
        return attempt;
    }

    public boolean getTcpNoDelay() {
        return tcpNoDelay;
    }

    public boolean getSoKeepAlive() {
        return soKeepAlive;
    }

    public boolean getOoBInline() {
        return ooBInline;
    }

    public boolean getSoReuseAddress() {
        return soReuseAddress;
    }

    public boolean getSoLingerOn() {
        return soLingerOn;
    }

    public int getSoLingerTime() {
        return soLingerTime;
    }

    public int getSoTrafficClass() {
        return soTrafficClass;
    }

    public boolean getThrowOnFailedAck() {
        return throwOnFailedAck;
    }

    @Override
    public void setKeepAliveCount(int keepAliveCount) {
        this.keepAliveCount = keepAliveCount;
    }

    @Override
    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public void setRequestCount(int requestCount) {
        this.requestCount = requestCount;
    }

    @Override
    public void setRxBufSize(int rxBufSize) {
        this.rxBufSize = rxBufSize;
    }

    @Override
    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    @Override
    public void setTxBufSize(int txBufSize) {
        this.txBufSize = txBufSize;
    }

    public void setConnectTime(long connectTime) {
        this.connectTime = connectTime;
    }

    public void setMaxRetryAttempts(int maxRetryAttempts) {
        this.maxRetryAttempts = maxRetryAttempts;
    }

    public void setAttempt(int attempt) {
        this.attempt = attempt;
    }

    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    public void setSoKeepAlive(boolean soKeepAlive) {
        this.soKeepAlive = soKeepAlive;
    }

    public void setOoBInline(boolean ooBInline) {
        this.ooBInline = ooBInline;
    }

    public void setSoReuseAddress(boolean soReuseAddress) {
        this.soReuseAddress = soReuseAddress;
    }

    public void setSoLingerOn(boolean soLingerOn) {
        this.soLingerOn = soLingerOn;
    }

    public void setSoLingerTime(int soLingerTime) {
        this.soLingerTime = soLingerTime;
    }

    public void setSoTrafficClass(int soTrafficClass) {
        this.soTrafficClass = soTrafficClass;
    }

    public void setThrowOnFailedAck(boolean throwOnFailedAck) {
        this.throwOnFailedAck = throwOnFailedAck;
    }

    public void setDestination(Member destination) throws UnknownHostException {
        this.destination = destination;
        this.address = InetAddress.getByAddress(destination.getHost());
        this.port = destination.getPort();
        this.udpPort = destination.getUdpPort();

    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }


    public boolean isUdpBased() {
        return udpBased;
    }


    public void setUdpBased(boolean udpBased) {
        this.udpBased = udpBased;
    }


    public int getUdpPort() {
        return udpPort;
    }


    public void setUdpPort(int udpPort) {
        this.udpPort = udpPort;
    }


    public int getUdpRxBufSize() {
        return udpRxBufSize;
    }


    public void setUdpRxBufSize(int udpRxBufSize) {
        this.udpRxBufSize = udpRxBufSize;
    }


    public int getUdpTxBufSize() {
        return udpTxBufSize;
    }


    public void setUdpTxBufSize(int udpTxBufSize) {
        this.udpTxBufSize = udpTxBufSize;
    }

}