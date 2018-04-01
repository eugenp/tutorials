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
package org.apache.tomcat.websocket;

import java.nio.channels.CompletionHandler;

import javax.websocket.SendHandler;
import javax.websocket.SendResult;

public class SendHandlerToCompletionHandler
        implements CompletionHandler<Long,Void> {

    private SendHandler handler;

    public SendHandlerToCompletionHandler(SendHandler handler) {
        this.handler = handler;
    }

    @Override
    public void completed(Long result, Void attachment) {
        handler.onResult(new SendResult());
    }

    @Override
    public void failed(Throwable exc, Void attachment) {
        handler.onResult(new SendResult(exc));
    }
}
