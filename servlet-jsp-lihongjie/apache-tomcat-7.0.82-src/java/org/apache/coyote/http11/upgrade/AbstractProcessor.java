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
import org.apache.coyote.http11.upgrade.servlet31.WebConnection;
import org.apache.juli.logging.Log;
import org.apache.tomcat.util.net.AbstractEndpoint.Handler.SocketState;
import org.apache.tomcat.util.net.SSLSupport;
import org.apache.tomcat.util.net.SocketStatus;
import org.apache.tomcat.util.net.SocketWrapper;
import org.apache.tomcat.util.res.StringManager;

public abstract class AbstractProcessor<S>
        implements Processor<S>, WebConnection {

    protected static final StringManager sm =
            StringManager.getManager(Constants.Package);
    protected abstract Log getLog();

    private final HttpUpgradeHandler httpUpgradeHandler;
    private final AbstractServletInputStream upgradeServletInputStream;
    private final AbstractServletOutputStream upgradeServletOutputStream;

    protected AbstractProcessor (HttpUpgradeHandler httpUpgradeHandler,
            AbstractServletInputStream upgradeServletInputStream,
            AbstractServletOutputStream upgradeServletOutputStream) {
        this.httpUpgradeHandler = httpUpgradeHandler;
        this.upgradeServletInputStream = upgradeServletInputStream;
        this.upgradeServletOutputStream = upgradeServletOutputStream;
    }


    // --------------------------------------------------- AutoCloseable methods

    @Override
    public void close() throws Exception {
        upgradeServletInputStream.close();
        upgradeServletOutputStream.close();
    }


    // --------------------------------------------------- WebConnection methods

    @Override
    public AbstractServletInputStream getInputStream() throws IOException {
        return upgradeServletInputStream;
    }

    @Override
    public AbstractServletOutputStream getOutputStream() throws IOException {
        return upgradeServletOutputStream;
    }


    // ------------------------------------------- Implemented Processor methods

    @Override
    public final boolean isUpgrade() {
        return true;
    }

    @Override
    public HttpUpgradeHandler getHttpUpgradeHandler() {
        return httpUpgradeHandler;
    }

    @Override
    public final SocketState upgradeDispatch(SocketStatus status)
            throws IOException {

        if (status == SocketStatus.OPEN_READ) {
            try {
                upgradeServletInputStream.onDataAvailable();
            } catch (IOException ioe) {
                // The error handling within the ServletInputStream should have
                // marked the stream for closure which will get picked up below,
                // triggering the clean-up of this processor.
                getLog().debug(sm.getString("abstractProcessor.onDataAvailableFail"), ioe);
            }
        } else if (status == SocketStatus.OPEN_WRITE) {
            try {
                upgradeServletOutputStream.onWritePossible();
            } catch (IOException ioe) {
                // The error handling within the ServletOutputStream should have
                // marked the stream for closure which will get picked up below,
                // triggering the clean-up of this processor.
                getLog().debug(sm.getString("abstractProcessor.onWritePossibleFail"), ioe);
            }
        } else if (status == SocketStatus.STOP) {
            try {
                upgradeServletInputStream.close();
            } catch (IOException ioe) {
                getLog().debug(sm.getString(
                        "abstractProcessor.isCloseFail", ioe));
            }
            try {
                upgradeServletOutputStream.close();
            } catch (IOException ioe) {
                getLog().debug(sm.getString(
                        "abstractProcessor.osCloseFail", ioe));
            }
            return SocketState.CLOSED;
        } else {
            // Unexpected state
            return SocketState.CLOSED;
        }
        if (upgradeServletInputStream.isCloseRequired() ||
                upgradeServletOutputStream.isCloseRequired()) {
            return SocketState.CLOSED;
        }
        return SocketState.UPGRADED;
    }

    @Override
    public final void recycle(boolean socketClosing) {
        // Currently a NO-OP as upgrade processors are not recycled.
    }

    
    // ------------------ Processor methods for Inbound/Outbound based mechanism
    
    @Override
    @Deprecated
    public UpgradeInbound getUpgradeInbound() {
        return null;
    }

    @Override
    public SocketState upgradeDispatch() throws IOException {
        return null;
    }


    // ---------------------------- Processor methods that are NO-OP for upgrade

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
