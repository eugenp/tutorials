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
package org.apache.tomcat.websocket;

public enum TransformationResult {
    /**
     * The end of the available data was reached before the WebSocket frame was
     * completely read.
     */
    UNDERFLOW,

    /**
     * The provided destination buffer was filled before all of the available
     * data from the WebSocket frame could be processed.
     */
    OVERFLOW,

    /**
     * The end of the WebSocket frame was reached and all the data from that
     * frame processed into the provided destination buffer.
     */
    END_OF_FRAME
}
