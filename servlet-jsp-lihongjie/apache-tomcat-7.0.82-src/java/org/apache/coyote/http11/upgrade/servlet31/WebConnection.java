/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.coyote.http11.upgrade.servlet31;

import java.io.IOException;

import org.apache.coyote.http11.upgrade.AbstractServletInputStream;
import org.apache.coyote.http11.upgrade.AbstractServletOutputStream;

/**
 * The interface used by a {@link HttpUpgradeHandler} to interact with an upgraded
 * HTTP connection.
 */
public interface WebConnection {

    /**
     * Provides access to the {@link AbstractServletInputStream} for reading
     * data from the client.
     */
    AbstractServletInputStream getInputStream() throws IOException;

    /**
     * Provides access to the {@link AbstractServletOutputStream} for writing
     * data to the client.
     */
    AbstractServletOutputStream getOutputStream() throws IOException;
    
    /**
     * The Servlet 3.1 interface extends AutoCloseable but that is not available
     * in Java 6 so this is the single method from that interface.
     */
    void close() throws Exception;
}