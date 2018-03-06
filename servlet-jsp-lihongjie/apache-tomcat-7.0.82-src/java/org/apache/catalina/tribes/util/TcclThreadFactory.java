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
package org.apache.catalina.tribes.util;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ThreadFactory implementation that creates threads with the thread context
 * class loader set to the class loader that loaded this factory. It is intended
 * to be used when tasks may be passed to executors when the web application
 * class loader is set as the thread context class loader, such as in async
 * session replication.
 */
public class TcclThreadFactory implements ThreadFactory {
    
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private static final boolean IS_SECURITY_ENABLED =
        (System.getSecurityManager() != null);
    
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;
    
    public TcclThreadFactory() {
        this("pool-" + poolNumber.getAndIncrement() + "-thread-");
    }

    public TcclThreadFactory(String namePrefix) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        this.namePrefix = namePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        final Thread t = new Thread(group, r, namePrefix +
                threadNumber.getAndIncrement());
        
        if (IS_SECURITY_ENABLED) {
            AccessController.doPrivileged(new PrivilegedAction<Void>() {
                @Override
                public Void run() {
                    t.setContextClassLoader(this.getClass().getClassLoader());
                    return null;
                }
            });
        } else {
            t.setContextClassLoader(this.getClass().getClassLoader());
        }
        t.setDaemon(true);
        return t;
    }

}
