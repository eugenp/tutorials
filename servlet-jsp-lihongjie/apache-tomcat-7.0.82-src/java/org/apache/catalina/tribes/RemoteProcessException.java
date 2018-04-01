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
package org.apache.catalina.tribes;

/**
 * <p>Title: RemoteProcessException</p>
 *
 * <p>Description: Message thrown by a sender when USE_SYNC_ACK receives a FAIL_ACK_COMMAND.<br>
 * This means that the message was received on the remote node but the processing of the message failed.
 * This message will be embedded in a ChannelException.FaultyMember
 * </p>
 * @see ChannelException
 * @author Filip Hanik
 * @version 1.0
 */
public class RemoteProcessException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RemoteProcessException() {
        super();
    }

    public RemoteProcessException(String message) {
        super(message);
    }

    public RemoteProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public RemoteProcessException(Throwable cause) {
        super(cause);
    }

}