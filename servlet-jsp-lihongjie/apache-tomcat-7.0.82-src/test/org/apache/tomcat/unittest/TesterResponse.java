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
package org.apache.tomcat.unittest;

import java.io.IOException;

import org.apache.catalina.connector.Response;

/**
 * Minimal changes to Response to enable tests that use this class to operate
 * correctly.
 */
public class TesterResponse extends Response {

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void sendError(int status, String message) throws IOException {
        // NO-OP by default.
        /*
        System.out.println("TesterResponse.sendError(" + status + ", \"" +
                message + "\")");
         */
    }

    @Override
    public void resetBuffer(boolean resetWriterStreamFlags) {
        // NO-OP by default.
        // There is no buffer created for this test object since no test depends
        // on one being present or on this method resetting it.
    }

    @Override
    public org.apache.coyote.Response getCoyoteResponse() {
        // Lazy init
        if (super.getCoyoteResponse() == null) {
            this.coyoteResponse = new org.apache.coyote.Response();
        }
        return super.getCoyoteResponse();
    }

    @Override
    public void setSuspended(boolean suspended) {
        // NO-OP by default.
        // There is no buffer created for this test object since no test depends
        // on one being present or on this method suspending it.
    }

    @Override
    public void reset() {
        // Minimal implementation for tests that avoids using OutputBuffer
        if (super.getCoyoteResponse() != null) {
            super.getCoyoteResponse().reset();
        }
    }
}
