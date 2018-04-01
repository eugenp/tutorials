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
package org.apache.tomcat.util.net;

public enum SendfileState {

    /**
     * The sending of the file has started but has not completed. Sendfile is
     * still using the socket.
     */
    PENDING,

    /**
     * The file has been fully sent. Sendfile is no longer using the socket.
     */
    DONE,

    /**
     * Something went wrong. The file may or may not have been sent. The socket
     * is in an unknown state.
     */
    ERROR
}
