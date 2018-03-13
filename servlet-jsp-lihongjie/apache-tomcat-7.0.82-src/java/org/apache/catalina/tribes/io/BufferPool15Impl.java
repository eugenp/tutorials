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

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Filip Hanik
 * @version 1.0
 */
class BufferPool15Impl implements BufferPool.BufferPoolAPI {
    protected int maxSize;
    protected AtomicInteger size = new AtomicInteger(0);
    protected ConcurrentLinkedQueue<XByteBuffer> queue = new ConcurrentLinkedQueue<XByteBuffer>();

    @Override
    public void setMaxSize(int bytes) {
        this.maxSize = bytes;
    }


    @Override
    public XByteBuffer getBuffer(int minSize, boolean discard) {
        XByteBuffer buffer = queue.poll();
        if ( buffer != null ) size.addAndGet(-buffer.getCapacity());
        if ( buffer == null ) buffer = new XByteBuffer(minSize,discard);
        else if ( buffer.getCapacity() <= minSize ) buffer.expand(minSize);
        buffer.setDiscard(discard);
        buffer.reset();
        return buffer;
    }

    @Override
    public void returnBuffer(XByteBuffer buffer) {
        if ( (size.get() + buffer.getCapacity()) <= maxSize ) {
            size.addAndGet(buffer.getCapacity());
            queue.offer(buffer);
        }
    }

    @Override
    public void clear() {
        queue.clear();
        size.set(0);
    }

    public int getMaxSize() {
        return maxSize;
    }

}
