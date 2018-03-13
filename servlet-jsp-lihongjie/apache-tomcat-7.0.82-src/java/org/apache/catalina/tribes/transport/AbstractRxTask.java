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

import org.apache.catalina.tribes.io.ListenCallback;


/**
 * @author Filip Hanik
 */
public abstract class AbstractRxTask implements Runnable
{ 
    
    public static final int OPTION_DIRECT_BUFFER = ReceiverBase.OPTION_DIRECT_BUFFER;
    
    private ListenCallback callback;
    private RxTaskPool pool;
    private boolean doRun = true;
    private int options;
    protected boolean useBufferPool = true;

    public AbstractRxTask(ListenCallback callback) {
        this.callback = callback;
    }

    public void setTaskPool(RxTaskPool pool) {
        this.pool = pool;
    }

    public void setOptions(int options) {
        this.options = options;
    }

    public void setCallback(ListenCallback callback) {
        this.callback = callback;
    }

    public void setDoRun(boolean doRun) {
        this.doRun = doRun;
    }

    public RxTaskPool getTaskPool() {
        return pool;
    }

    public int getOptions() {
        return options;
    }

    public ListenCallback getCallback() {
        return callback;
    }

    public boolean isDoRun() {
        return doRun;
    }

    public void close()
    {
        doRun = false;
        notify();
    }
    
    public void setUseBufferPool(boolean usebufpool) {
        useBufferPool = usebufpool;
    }
    
    public boolean getUseBufferPool() {
        return useBufferPool;
    }
}
