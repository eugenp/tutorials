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
package org.apache.tomcat.websocket.server;

import java.io.EOFException;
import java.io.IOException;

import org.apache.coyote.http11.upgrade.AbstractServletInputStream;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.res.StringManager;
import org.apache.tomcat.websocket.Transformation;
import org.apache.tomcat.websocket.WsFrameBase;
import org.apache.tomcat.websocket.WsSession;

public class WsFrameServer extends WsFrameBase {

    private static final Log log = LogFactory.getLog(WsFrameServer.class);
    private static final StringManager sm = StringManager.getManager(Constants.PACKAGE_NAME);

    private final AbstractServletInputStream sis;
    private final Object connectionReadLock = new Object();


    public WsFrameServer(AbstractServletInputStream sis, WsSession wsSession,
            Transformation transformation) {
        super(wsSession, transformation);
        this.sis = sis;
    }


    /**
     * Called when there is data in the ServletInputStream to process.
     */
    public void onDataAvailable() throws IOException {
        if (log.isDebugEnabled()) {
            log.debug("wsFrameServer.onDataAvailable");
        }
        synchronized (connectionReadLock) {
            while (isOpen() && sis.isReady()) {
                // Fill up the input buffer with as much data as we can
                int read = sis.read(inputBuffer, writePos, inputBuffer.length - writePos);
                if (log.isDebugEnabled()) {
                    log.debug(sm.getString("wsFrameServer.bytesRead", Integer.toString(read)));
                }
                if (read == 0) {
                    return;
                }
                if (read == -1) {
                    throw new EOFException();
                }
                writePos += read;
                processInputBuffer();
            }
        }
    }


    @Override
    protected boolean isMasked() {
        // Data is from the client so it should be masked
        return true;
    }


    @Override
    protected Transformation getTransformation() {
        // Overridden to make it visible to other classes in this package
        return super.getTransformation();
    }


    @Override
    protected Log getLog() {
        return log;
    }
}
