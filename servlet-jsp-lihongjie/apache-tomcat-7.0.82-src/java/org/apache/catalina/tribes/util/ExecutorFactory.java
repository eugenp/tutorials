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

import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorFactory {

    public static ExecutorService newThreadPool(int minThreads, int maxThreads, long maxIdleTime, TimeUnit unit) {
        TaskQueue taskqueue = new TaskQueue();
        ThreadPoolExecutor service = new TribesThreadPoolExecutor(minThreads, maxThreads, maxIdleTime, unit,taskqueue);
        taskqueue.setParent(service);
        return service;
    }

    public static ExecutorService newThreadPool(int minThreads, int maxThreads, long maxIdleTime, TimeUnit unit, ThreadFactory threadFactory) {
        TaskQueue taskqueue = new TaskQueue();
        ThreadPoolExecutor service = new TribesThreadPoolExecutor(minThreads, maxThreads, maxIdleTime, unit,taskqueue, threadFactory);
        taskqueue.setParent(service);
        return service;
    }

    // ---------------------------------------------- TribesThreadPoolExecutor Inner Class
    private static class TribesThreadPoolExecutor extends ThreadPoolExecutor {
        public TribesThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
        }

        public TribesThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
                RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        public TribesThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
        }

        public TribesThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        @Override
        public void execute(Runnable command) {
            try {
                super.execute(command);
            } catch (RejectedExecutionException rx) {
                if (super.getQueue() instanceof TaskQueue) {
                    TaskQueue queue = (TaskQueue)super.getQueue();
                    if (!queue.force(command)) {
                        throw new RejectedExecutionException("Queue capacity is full.");
                    }
                }
            }
        }
    }

     // ---------------------------------------------- TaskQueue Inner Class
    private static class TaskQueue extends LinkedBlockingQueue<Runnable> {
        private static final long serialVersionUID = 1L;

        ThreadPoolExecutor parent = null;

        public TaskQueue() {
            super();
        }

        public TaskQueue(int initialCapacity) {
            super(initialCapacity);
        }

        public TaskQueue(Collection<? extends Runnable> c) {
            super(c);
        }

        public void setParent(ThreadPoolExecutor tp) {
            parent = tp;
        }
        
        public boolean force(Runnable o) {
            if ( parent.isShutdown() ) throw new RejectedExecutionException("Executor not running, can't force a command into the queue");
            return super.offer(o); //forces the item onto the queue, to be used if the task is rejected
        }

        @Override
        public boolean offer(Runnable o) {
            //we can't do any checks
            if (parent==null) return super.offer(o);
            //we are maxed out on threads, simply queue the object
            if (parent.getPoolSize() == parent.getMaximumPoolSize()) return super.offer(o);
            //we have idle threads, just add it to the queue
            //this is an approximation, so it could use some tuning
            if (parent.getActiveCount()<(parent.getPoolSize())) return super.offer(o);
            //if we have less threads than maximum force creation of a new thread
            if (parent.getPoolSize()<parent.getMaximumPoolSize()) return false;
            //if we reached here, we need to add it to the queue
            return super.offer(o);
        }
    }
}
