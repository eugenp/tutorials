/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.tomcat.util.net;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class SocketWrapper<E> {

    protected volatile E socket;

    // Volatile because I/O and setting the timeout values occurs on a different
    // thread to the thread checking the timeout.
    protected volatile long lastAccess = System.currentTimeMillis();
    protected volatile long timeout = -1;
    
    protected boolean error = false;
    protected long lastRegistered = 0;
    protected volatile int keepAliveLeft = 100;
    private boolean comet = false;
    protected boolean async = false;
    protected boolean keptAlive = false;
    private boolean upgraded = false;
    private boolean secure = false;

    /*
     * Used if block/non-blocking is set at the socket level. The client is
     * responsible for the thread-safe use of this field via the locks provided.
     */
    private volatile boolean blockingStatus = true;
    private final Lock blockingStatusReadLock;
    private final WriteLock blockingStatusWriteLock;

    /*
     * In normal servlet processing only one thread is allowed to access the
     * socket at a time. That is controlled by a lock on the socket for both
     * read and writes). When HTTP upgrade is used, one read thread and one
     * write thread are allowed to access the socket concurrently. In this case
     * the lock on the socket is used for reads and the lock below is used for
     * writes.
     */
    private final Object writeThreadLock = new Object();

    public SocketWrapper(E socket) {
        this.socket = socket;
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        this.blockingStatusReadLock = lock.readLock();
        this.blockingStatusWriteLock =lock.writeLock();
    }

    public E getSocket() {
        return socket;
    }

    public boolean isComet() { return comet; }
    public void setComet(boolean comet) { this.comet = comet; }
    public boolean isAsync() { return async; }
    public void setAsync(boolean async) { this.async = async; }
    public boolean isUpgraded() { return upgraded; }
    public void setUpgraded(boolean upgraded) { this.upgraded = upgraded; }
    public boolean isSecure() { return secure; }
    public void setSecure(boolean secure) { this.secure = secure; }
    public long getLastAccess() { return lastAccess; }
    public void access() {
        // Async timeouts are based on the time between the call to startAsync()
        // and complete() / dispatch() so don't update the last access time
        // (that drives the timeout) on every read and write when using async
        // processing.
        if (!isAsync()) {
            access(System.currentTimeMillis());
        }
    }
    public void access(long access) { lastAccess = access; }
    public void setTimeout(long timeout) {
        if (timeout > 0) {
            this.timeout = timeout;
        } else {
            this.timeout = -1;
        }
    }
    public long getTimeout() {return this.timeout;}
    public boolean getError() { return error; }
    public void setError(boolean error) { this.error = error; }
    public void setKeepAliveLeft(int keepAliveLeft) { this.keepAliveLeft = keepAliveLeft;}
    public int decrementKeepAlive() { return (--keepAliveLeft);}
    public boolean isKeptAlive() {return keptAlive;}
    public void setKeptAlive(boolean keptAlive) {this.keptAlive = keptAlive;}
    public boolean getBlockingStatus() { return blockingStatus; }
    public void setBlockingStatus(boolean blockingStatus) {
        this.blockingStatus = blockingStatus;
    }
    public Lock getBlockingStatusReadLock() { return blockingStatusReadLock; }
    public WriteLock getBlockingStatusWriteLock() {
        return blockingStatusWriteLock;
    }
    public Object getWriteThreadLock() { return writeThreadLock; }

    public void reset(E socket, long timeout) {
        async = false;
        blockingStatus = true;
        comet = false;
        error = false;
        keepAliveLeft = 100;
        lastAccess = System.currentTimeMillis();
        this.socket = socket;
        this.timeout = timeout;
        upgraded = false;
    }

    /**
     * Overridden for debug purposes. No guarantees are made about the format of
     * this message which may vary significantly between point releases.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString() + ":" + String.valueOf(socket);
    }
}
