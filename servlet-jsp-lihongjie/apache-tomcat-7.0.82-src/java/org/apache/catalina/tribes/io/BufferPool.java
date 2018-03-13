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


import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

/**
 *
 * @author Filip Hanik
 * 
 * @version 1.0
 */
public class BufferPool {
    private static final Log log = LogFactory.getLog(BufferPool.class);

    public static final int DEFAULT_POOL_SIZE = 100*1024*1024; //100MB



    protected static volatile BufferPool instance = null;
    protected BufferPoolAPI pool = null;

    private BufferPool(BufferPoolAPI pool) {
        this.pool = pool;
    }

    public XByteBuffer getBuffer(int minSize, boolean discard) {
        if ( pool != null ) return pool.getBuffer(minSize, discard);
        else return new XByteBuffer(minSize,discard);
    }

    public void returnBuffer(XByteBuffer buffer) {
        if ( pool != null ) pool.returnBuffer(buffer);
    }

    public void clear() {
        if ( pool != null ) pool.clear();
    }


    public static BufferPool getBufferPool() {
        if (  (instance == null) ) {
            synchronized (BufferPool.class) {
                if ( instance == null ) {
                   BufferPoolAPI pool = null;
                   Class<?> clazz = null;
                   try {
                       // TODO Is this approach still required?
                       clazz = Class.forName("org.apache.catalina.tribes.io.BufferPool15Impl");
                       pool = (BufferPoolAPI)clazz.newInstance();
                   } catch ( Throwable x ) {
                       log.warn("Unable to initilize BufferPool, not pooling XByteBuffer objects:"+x.getMessage());
                       if ( log.isDebugEnabled() ) log.debug("Unable to initilize BufferPool, not pooling XByteBuffer objects:",x);
                   }
                   if (pool != null) {
                       pool.setMaxSize(DEFAULT_POOL_SIZE);
                       log.info("Created a buffer pool with max size:"+DEFAULT_POOL_SIZE+" bytes of type:"+(clazz!=null?clazz.getName():"null"));
                       instance = new BufferPool(pool);
                   }
                }//end if
            }//sync
        }//end if
        return instance;
    }


    public static interface BufferPoolAPI {
        public void setMaxSize(int bytes);

        public XByteBuffer getBuffer(int minSize, boolean discard);

        public void returnBuffer(XByteBuffer buffer);

        public void clear();
    }    
}
