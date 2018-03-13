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
package org.apache.coyote.http11.upgrade;

import java.io.IOException;

import org.apache.tomcat.util.net.AbstractEndpoint.Handler.SocketState;

/**
 * Receives notification that there is data to be read on the upgraded
 * connection and processes it.
 * 
 * @deprecated  Will be removed in Tomcat 8.0.x.
 */
@Deprecated
public interface UpgradeInbound {

    void setUpgradeProcessor(UpgradeProcessor<?> processor);

    void onUpgradeComplete();

    SocketState onData() throws IOException;

    void setUpgradeOutbound(UpgradeOutbound upgradeOutbound);

    /**
     * Allow the upgraded protocol to define the read timeout to be used with
     * the upgraded connection.
     *
     * @return  The read timeout in milliseconds or -1 for infinite
     */
    int getReadTimeout();
}
