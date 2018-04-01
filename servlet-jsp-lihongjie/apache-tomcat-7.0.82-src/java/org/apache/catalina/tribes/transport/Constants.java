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

import org.apache.catalina.tribes.io.XByteBuffer;

/**
 * Manifest constants for the <code>org.apache.catalina.tribes.transport</code>
 * package.
 * @author Filip Hanik
 * @author Peter Rossbach
 */
public class Constants {

    public static final String Package = "org.apache.catalina.tribes.transport";
    
    /*
     * Do not change any of these values!
     */
    public static final byte[] ACK_DATA = new byte[] {6, 2, 3};
    public static final byte[] FAIL_ACK_DATA = new byte[] {11, 0, 5};
    public static final byte[] ACK_COMMAND = XByteBuffer.createDataPackage(ACK_DATA);
    public static final byte[] FAIL_ACK_COMMAND = XByteBuffer.createDataPackage(FAIL_ACK_DATA);

}
