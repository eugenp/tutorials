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

package org.apache.catalina.tribes.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Byte array output stream that exposes the byte array directly
 *
 * @author not attributable
 * @version 1.0
 */
public class DirectByteArrayOutputStream extends OutputStream {
    
    private XByteBuffer buffer;
    
    public DirectByteArrayOutputStream(int size) {
        buffer = new XByteBuffer(size,false);
    }

    /**
     * Writes the specified byte to this output stream.
     *
     * @param b the <code>byte</code>.
     * @throws IOException if an I/O error occurs. In particular, an
     *   <code>IOException</code> may be thrown if the output stream has
     *   been closed.
     * TODO Implement this java.io.OutputStream method
     */
    @Override
    public void write(int b) throws IOException {
        buffer.append((byte)b);
    }
    
    public int size() {
        return buffer.getLength();
    }
    
    public byte[] getArrayDirect() {
        return buffer.getBytesDirect();
    }
    
    public byte[] getArray() {
        return buffer.getBytes();
    }


}