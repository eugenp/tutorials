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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.tomcat.util.net.SocketWrapper;

/**
 * @deprecated  Will be removed in Tomcat 8.0.x.
 */
@Deprecated
public class UpgradeBioProcessor extends UpgradeProcessor<Socket> {

    private final InputStream inputStream;
    private final OutputStream outputStream;

    public UpgradeBioProcessor(SocketWrapper<Socket> wrapper,
            UpgradeInbound upgradeInbound) throws IOException {
        super(upgradeInbound);

        int timeout = upgradeInbound.getReadTimeout();
        if (timeout < 0) {
            timeout = 0;
        }
        wrapper.getSocket().setSoTimeout(timeout);

        this.inputStream = wrapper.getSocket().getInputStream();
        this.outputStream = wrapper.getSocket().getOutputStream();
    }


    /*
     * Output methods
     */
    @Override
    public void flush() throws IOException {
        outputStream.flush();
    }


    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
    }


    @Override
    public void write(byte[]b, int off, int len) throws IOException {
        outputStream.write(b, off, len);
    }


    /*
     * Input methods
     */
    @Override
    public int read() throws IOException {
        return inputStream.read();
    }


    @Override
    public int read(boolean block, byte[] bytes, int off, int len)
            throws IOException {
        // The BIO endpoint always uses blocking IO so the block parameter is
        // ignored and a blocking read is performed.
        return inputStream.read(bytes, off, len);
    }
}
