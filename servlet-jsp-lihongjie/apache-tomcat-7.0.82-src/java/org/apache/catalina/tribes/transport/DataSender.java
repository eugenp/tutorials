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
package org.apache.catalina.tribes.transport;

import java.io.IOException;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public interface DataSender {
    public void connect() throws IOException;
    public void disconnect();
    public boolean isConnected();
    public void setRxBufSize(int size);
    public void setTxBufSize(int size);
    public boolean keepalive();
    public void setTimeout(long timeout);
    public void setKeepAliveCount(int maxRequests);
    public void setKeepAliveTime(long keepAliveTimeInMs);
    public int getRequestCount();
    public long getConnectTime();
    
    
}