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

public enum SendfileKeepAliveState {

    /**
     * Keep-alive is not in use. The socket can be closed when the response has
     * been written.
     */
    NONE,

    /**
     * Keep-alive is in use and there is pipelined data in the input buffer to
     * be read as soon as the current response has been written.
     */
    PIPELINED,

    /**
     * Keep-alive is in use. The socket should be added to the poller (or
     * equivalent) to await more data as soon as the current response has been
     * written.
     */
    OPEN
}
