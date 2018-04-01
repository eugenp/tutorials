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

package org.apache.catalina.tribes.transport.bio.util;

/**
 * The class <b>SingleRemoveSynchronizedAddLock</b> implement locking for
 * accessing the queue by a single remove thread and multiple add threads.
 *
 * A thread is only allowed to be either the remove or
 * an add thread.
 *
 * The lock can either be owned by the remove thread
 * or by a single add thread.
 *
 * If the remove thread tries to get the lock,
 * but the queue is empty, it will block (poll)
 * until an add threads adds an entry to the queue and
 * releases the lock.
 * 
 * If the remove thread and add threads compete for
 * the lock and an add thread releases the lock, then
 * the remove thread will get the lock first.
 *
 * The remove thread removes all entries in the queue
 * at once and processes them without further
 * polling the queue.
 *
 * The lock is not reentrant, in the sense, that all
 * threads must release an owned lock before competing
 * for the lock again!
 *
 * @author Rainer Jung
 * @author Peter Rossbach
 * @version 1.1
 */
 
public class SingleRemoveSynchronizedAddLock {
    
    public SingleRemoveSynchronizedAddLock() {
        // NO-OP
    }
    
    public SingleRemoveSynchronizedAddLock(boolean dataAvailable) {
        this.dataAvailable=dataAvailable;
    }
    
    /**
     * Time in milliseconds after which threads
     * waiting for an add lock are woken up.
     * This is used as a safety measure in case
     * thread notification via the unlock methods
     * has a bug.
     */
    private long addWaitTimeout = 10000L;

    /**
     * Time in milliseconds after which threads
     * waiting for a remove lock are woken up.
     * This is used as a safety measure in case
     * thread notification via the unlock methods
     * has a bug.
     */
    private long removeWaitTimeout = 30000L;

    /**
     * The current remove thread.
     * It is set to the remove thread polling for entries.
     * It is reset to null when the remove thread
     * releases the lock and proceeds processing
     * the removed entries.
     */
    private Thread remover = null;

    /**
     * A flag indicating, if an add thread owns the lock.
     */
    private boolean addLocked = false;

    /**
     * A flag indicating, if the remove thread owns the lock.
     */
    private boolean removeLocked = false;

    /**
     * A flag indicating, if the remove thread is allowed
     * to wait for the lock. The flag is set to false, when aborting.
     */
    private boolean removeEnabled = true;

    /**
     * A flag indicating, if the remover needs polling.
     * It indicates, if the locked object has data available
     * to be removed.
     */
    private boolean dataAvailable = false;

    /**
     * @return Value of addWaitTimeout
     */
    public synchronized long getAddWaitTimeout() {
        return addWaitTimeout;
    }

    /**
     * Set value of addWaitTimeout
     */
    public synchronized void setAddWaitTimeout(long timeout) {
        addWaitTimeout = timeout;
    }

    /**
     * @return Value of removeWaitTimeout
     */
    public synchronized long getRemoveWaitTimeout() {
        return removeWaitTimeout;
    }

    /**
     * Set value of removeWaitTimeout
     */
    public synchronized void setRemoveWaitTimeout(long timeout) {
        removeWaitTimeout = timeout;
    }

    /**
     * Check if the locked object has data available
     * i.e. the remover can stop poling and get the lock.
     * @return True iff the lock Object has data available.
     */
    public synchronized boolean isDataAvailable() {
        return dataAvailable;
    }

    /**
     * Check if an add thread owns the lock.
     * @return True iff an add thread owns the lock.
     */
    public synchronized boolean isAddLocked() {
        return addLocked;
    }

    /**
     * Check if the remove thread owns the lock.
     * @return True iff the remove thread owns the lock.
     */
    public synchronized boolean isRemoveLocked() {
        return removeLocked;
    }

    /**
     * Check if the remove thread is polling.
     * @return True iff the remove thread is polling.
     */
    public synchronized boolean isRemovePolling() {
        if ( remover != null ) {
            return true;
        }
        return false;
    }

    /**
     * Acquires the lock by an add thread and sets the add flag.
     * If any add thread or the remove thread already acquired the lock
     * this add thread will block until the lock is released.
     */
    public synchronized void lockAdd() {
        if ( addLocked || removeLocked ) {
            do {
                try {
                    wait(addWaitTimeout);
                } catch ( InterruptedException e ) {
                    Thread.currentThread().interrupt();
                }
            } while ( addLocked || removeLocked );
        }
        addLocked=true;
    }

    /**
     * Acquires the lock by the remove thread and sets the remove flag.
     * If any add thread already acquired the lock or the queue is
     * empty, the remove thread will block until the lock is released
     * and the queue is not empty.
     */
    public synchronized boolean lockRemove() {
        removeLocked=false;
        removeEnabled=true;
        if ( ( addLocked || ! dataAvailable ) && removeEnabled ) {
            remover=Thread.currentThread();
            do {
                try {
                    wait(removeWaitTimeout);
                } catch ( InterruptedException e ) {
                    Thread.currentThread().interrupt();
                }
            } while ( ( addLocked || ! dataAvailable ) && removeEnabled );
            remover=null;
        }
        if ( removeEnabled ) {
            removeLocked=true;
        } 
        return removeLocked;
    }

    /**
     * Releases the lock by an add thread and reset the remove flag.
     * If the reader thread is polling, notify it.
     */
    public synchronized void unlockAdd(boolean dataAvailable) {
        addLocked=false;
        this.dataAvailable=dataAvailable;
        if ( ( remover != null ) && ( dataAvailable || ! removeEnabled ) ) {
            remover.interrupt();
        } else {
            notifyAll();
        }
    }

    /**
     * Releases the lock by the remove thread and reset the add flag.
     * Notify all waiting add threads,
     * that the lock has been released by the remove thread.
     */
    public synchronized void unlockRemove() {
        removeLocked=false;
        dataAvailable=false;
        notifyAll();
    }

    /**
     * Abort any polling remover thread
     */
    public synchronized void abortRemove() {
        removeEnabled=false;
        if ( remover != null ) {
            remover.interrupt();
        }
    }

}
