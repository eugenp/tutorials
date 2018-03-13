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

import org.apache.coyote.http11.upgrade.servlet31.HttpUpgradeHandler;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.jni.Socket;
import org.apache.tomcat.util.net.AprEndpoint;
import org.apache.tomcat.util.net.SocketWrapper;

public class AprProcessor extends AbstractProcessor<Long> {

    private static final Log log = LogFactory.getLog(AprProcessor.class);
    @Override
    protected Log getLog() {return log;}

    private static final int INFINITE_TIMEOUT = -1;

    public AprProcessor(SocketWrapper<Long> wrapper,
            HttpUpgradeHandler httpUpgradeProcessor, AprEndpoint endpoint,
            int asyncWriteBufferSize) {
        super(httpUpgradeProcessor,
                new AprServletInputStream(wrapper),
                new AprServletOutputStream(wrapper, asyncWriteBufferSize, endpoint));

        Socket.timeoutSet(wrapper.getSocket().longValue(), INFINITE_TIMEOUT);
    }
}
