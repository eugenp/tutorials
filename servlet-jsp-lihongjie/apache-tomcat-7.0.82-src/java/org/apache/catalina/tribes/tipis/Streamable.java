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
package org.apache.catalina.tribes.tipis;

import java.io.IOException;

/**
 * Example usage:
 * <code><pre>
 * byte[] data = new byte[1024];
 * Streamable st = ....;
 * while ( !st.eof() ) {
 * &nbsp;&nbsp;int length = st.read(data,0,data.length);
 * &nbsp;&nbsp;String s = new String(data,0,length);
 * &nbsp;&nbsp;System.out.println(s);
 * }
 * </pre></code>
 * @author Filip Hanik
 * @version 1.0
 *
 * @deprecated  Unused - will be removed in Tomcat 8.0.x
 */
@Deprecated
public interface Streamable {
    
    /**
     * returns true if the stream has reached its end
     * @return boolean
     */
    public boolean eof();
   
    /**
     * write data into the byte array starting at offset, maximum bytes read are (data.length-offset)
     * @param data byte[] - the array to read data into
     * @param offset int - start position for writing data
     * @return int - the number of bytes written into the data buffer
     */
    public int write(byte[] data, int offset, int length) throws IOException;
    
    /**
     * read data into the byte array starting at offset
     * @param data byte[] - the array to read data into
     * @param offset int - start position for writing data
     * @param length - the desired read length
     * @return int - the number of bytes read from the data buffer
     */
    public int read(byte[] data, int offset, int length) throws IOException;

   
}