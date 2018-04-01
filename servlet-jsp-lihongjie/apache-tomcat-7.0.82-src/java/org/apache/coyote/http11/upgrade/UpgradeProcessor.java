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
package org.apache.coyote.http11.upgrade;

import java.io.IOException;
import java.util.concurrent.Executor;

import org.apache.coyote.Processor;
import org.apache.coyote.Request;
import org.apache.coyote.http11.upgrade.servlet31.HttpUpgradeHandler;
import org.apache.tomcat.util.net.AbstractEndpoint.Handler.SocketState;
import org.apache.tomcat.util.net.SSLSupport;
import org.apache.tomcat.util.net.SocketStatus;
import org.apache.tomcat.util.net.SocketWrapper;
import org.apache.tomcat.util.res.StringManager;

/**
 * @deprecated  Will be removed in Tomcat 8.0.x.
 */
@Deprecated
public abstract class UpgradeProcessor<S> implements Processor<S> {

    protected static final StringManager sm =
            StringManager.getManager(Constants.Package);

    private final UpgradeInbound upgradeInbound;

    protected UpgradeProcessor (UpgradeInbound upgradeInbound) {
        this.upgradeInbound = upgradeInbound;
        upgradeInbound.setUpgradeProcessor(this);
        upgradeInbound.setUpgradeOutbound(new UpgradeOutbound(this));
    }

    // Output methods
    public abstract void flush() throws IOException;
    public abstract void write(int b) throws IOException;
    public abstract void write(byte[] b, int off, int len) throws IOException;

    // Input methods
    /**
     * This is always a blocking read of a single byte.
     *
     * @return  The next byte or -1 if the end of the input is reached.
     *
     * @throws IOException  If a problem occurs trying to read from the input
     */
    public abstract int read() throws IOException;

    /**
     * Read up to len bytes from the input in either blocking or non-blocking
     * mode (where non-blocking is supported). If the input does not support
     * non-blocking reads, a blcoking read will be performed.
     *
     * @param block
     * @param bytes
     * @param off
     * @param len
     * @return  The number of bytes read or -1 if the end of the input is
     *          reached. Non-blocking reads may return zero if no data is
     *          available. Blocking reads never return zero.
     *
     * @throws IOException  If a problem occurs trying to read from the input
     */
    public abstract int read(boolean block, byte[] bytes, int off, int len)
            throws IOException;

    @Override
    public final UpgradeInbound getUpgradeInbound() {
        return upgradeInbound;
    }

    @Override
    public final SocketState upgradeDispatch() throws IOException {
        return upgradeInbound.onData();
    }

    @Override
    public final void recycle(boolean socketClosing) {
        // Currently a NO-OP as upgrade processors are not recycled.
    }

    
    // Servlet 3.1 based HTTP upgrade mechanism. NO-OPs for the proprietary
    // Tomcat upgrade mechanism.
    @Override
    public HttpUpgradeHandler getHttpUpgradeHandler() {
        return null;
    }

    @Override
    public SocketState upgradeDispatch(SocketStatus status) throws IOException {
        return null;
    }

    @Override
    public boolean isUpgrade() {
        return false;
    }
    
    
    // NO-OP methods for upgrade
    @Override
    public final Executor getExecutor() {
        return null;
    }

    @Override
    public final SocketState process(SocketWrapper<S> socketWrapper)
            throws IOException {
        return null;
    }

    @Override
    public final SocketState event(SocketStatus status) throws IOException {
        return null;
    }

    @Override
    public final SocketState asyncDispatch(SocketStatus status) {
        return null;
    }

    @Override
    public void errorDispatch() {
        // NO-OP
    }

    @Override
    public final SocketState asyncPostProcess() {
        return null;
    }

    @Override
    public final boolean isComet() {
        return false;
    }

    @Override
    public final boolean isAsync() {
        return false;
    }

    @Override
    public final Request getRequest() {
        return null;
    }

    @Override
    public final void setSslSupport(SSLSupport sslSupport) {
        // NOOP
    }
}
