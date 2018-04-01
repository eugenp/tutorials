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
package org.apache.catalina.tribes.group.interceptors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.catalina.tribes.ChannelMessage;
import org.apache.catalina.tribes.Member;
import org.apache.catalina.tribes.group.GroupChannel;
import org.apache.catalina.tribes.group.InterceptorPayload;
import org.apache.catalina.tribes.transport.bio.util.LinkObject;
import org.apache.catalina.tribes.util.ExecutorFactory;
import org.apache.catalina.tribes.util.TcclThreadFactory;

/**
 * 
 * Same implementation as the MessageDispatchInterceptor
 * except it uses an atomic long for the currentSize calculation
 * and uses a thread pool for message sending.
 * 
 * @author Filip Hanik
 * @version 1.0
 */

public class MessageDispatch15Interceptor extends MessageDispatchInterceptor {

    protected AtomicLong currentSize = new AtomicLong(0);
    protected ExecutorService executor = null;
    protected int maxThreads = 10;
    protected int maxSpareThreads = 2;
    protected long keepAliveTime = 5000;

    @Override
    public long getCurrentSize() {
        return currentSize.get();
    }

    @Override
    public long addAndGetCurrentSize(long inc) {
        return currentSize.addAndGet(inc);
    }

    @Override
    public long setAndGetCurrentSize(long value) {
        currentSize.set(value);
        return value;
    }
    
    @Override
    public boolean addToQueue(ChannelMessage msg, Member[] destination, InterceptorPayload payload) {
        final LinkObject obj = new LinkObject(msg,destination,payload);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                sendAsyncData(obj);
            }
        };
        executor.execute(r);
        return true;
    }

    @Override
    public LinkObject removeFromQueue() {
        return null; //not used, thread pool contains its own queue.
    }

    @Override
    public void startQueue() {
        if ( run ) return;
        String channelName = "";
        if (getChannel() instanceof GroupChannel
                && ((GroupChannel)getChannel()).getName() != null) {
            channelName = "[" + ((GroupChannel)getChannel()).getName() + "]";
        }
        executor = ExecutorFactory.newThreadPool(maxSpareThreads, maxThreads,
                keepAliveTime, TimeUnit.MILLISECONDS,
                new TcclThreadFactory("MessageDispatch15Interceptor.MessageDispatchThread" + channelName));
        run = true;
    }

    @Override
    public void stopQueue() {
        run = false;
        executor.shutdownNow();
        setAndGetCurrentSize(0);
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public int getMaxSpareThreads() {
        return maxSpareThreads;
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public void setMaxSpareThreads(int maxSpareThreads) {
        this.maxSpareThreads = maxSpareThreads;
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

}