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
package org.apache.tomcat.jdbc.pool;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * A simple implementation of a blocking queue with fairness waiting.
 * invocations to method poll(...) will get handed out in the order they were received.
 * Locking is fine grained, a shared lock is only used during the first level of contention, waiting is done in a
 * lock per thread basis so that order is guaranteed once the thread goes into a suspended monitor state.
 * <br>
 * Not all of the methods of the {@link java.util.concurrent.BlockingQueue} are implemented.
 * @author Filip Hanik
 *
 */

public class FairBlockingQueue<E> implements BlockingQueue<E> {

    /**
     * This little sucker is used to reorder the way to do
     * {@link java.util.concurrent.locks.Lock#lock()},
     * {@link java.util.concurrent.locks.Lock#unlock()}
     * and
     * {@link java.util.concurrent.CountDownLatch#countDown()}
     * during the {@link #poll(long, TimeUnit)} operation.
     * On Linux, it performs much better if we count down while we hold the global
     * lock, on Solaris its the other way around.
     * Until we have tested other platforms we only check for Linux.
     */
    static final boolean isLinux = "Linux".equals(System.getProperty("os.name")) &&
                                   (!Boolean.getBoolean(FairBlockingQueue.class.getName()+".ignoreOS"));

    /**
     * Phase one entry lock in order to give out
     * per-thread-locks for the waiting phase we have
     * a phase one lock during the contention period.
     */
    final ReentrantLock lock = new ReentrantLock(false);

    /**
     * All the objects in the pool are stored in a simple linked list
     */
    final LinkedList<E> items;

    /**
     * All threads waiting for an object are stored in a linked list
     */
    final LinkedList<ExchangeCountDownLatch<E>> waiters;

    /**
     * Creates a new fair blocking queue.
     */
    public FairBlockingQueue() {
        items = new LinkedList<E>();
        waiters = new LinkedList<ExchangeCountDownLatch<E>>();
    }

    //------------------------------------------------------------------
    // USED BY CONPOOL IMPLEMENTATION
    //------------------------------------------------------------------
    /**
     * Will always return true, queue is unbounded.
     * {@inheritDoc}
     */
    @Override
    public boolean offer(E e) {
        //during the offer, we will grab the main lock
        final ReentrantLock lock = this.lock;
        lock.lock();
        ExchangeCountDownLatch<E> c = null;
        try {
            //check to see if threads are waiting for an object
            if (waiters.size() > 0) {
                //if threads are waiting grab the latch for that thread
                c = waiters.poll();
                //give the object to the thread instead of adding it to the pool
                c.setItem(e);
                if (isLinux) c.countDown();
            } else {
                //we always add first, so that the most recently used object will be given out
                items.addFirst(e);
            }
        } finally {
            lock.unlock();
        }
        //if we exchanged an object with another thread, wake it up.
        if (!isLinux && c!=null) c.countDown();
        //we have an unbounded queue, so always return true
        return true;
    }

    /**
     * Will never timeout, as it invokes the {@link #offer(Object)} method.
     * Once a lock has been acquired, the
     * {@inheritDoc}
     */
    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        return offer(e);
    }

    /**
     * Fair retrieval of an object in the queue.
     * Objects are returned in the order the threads requested them.
     * {@inheritDoc}
     */
    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        E result = null;
        final ReentrantLock lock = this.lock;
        try {
            //acquire the global lock until we know what to do
            lock.lock();
            //check to see if we have objects
            result = items.poll();
            if (result==null && timeout>0) {
                //the queue is empty we will wait for an object
                ExchangeCountDownLatch<E> c = new ExchangeCountDownLatch<E>(1);
                //add to the bottom of the wait list
                waiters.addLast(c);
                //unlock the global lock
                lock.unlock();
                //wait for the specified timeout
                boolean didtimeout = true;
                InterruptedException interruptedException = null;
                try {
                    //wait for the specified timeout
                    didtimeout = !c.await(timeout, unit);
                } catch (InterruptedException ix) {
                    interruptedException = ix;
                }
                if (didtimeout) {
                    //if we timed out, or got interrupted
                    // remove ourselves from the waitlist
                    lock.lock();
                    try {
                        waiters.remove(c);
                    } finally {
                        lock.unlock();
                    }
                }
                //return the item we received, can be null if we timed out
                result = c.getItem();
                if (null!=interruptedException) {
                    //we got interrupted
                    if (null!=result) {
                        //we got a result - clear the interrupt status
                        //don't propagate cause we have removed a connection from pool
                        Thread.interrupted();
                    } else {
                        throw interruptedException;
                    }
                }
            } else {
                //we have an object, release
                lock.unlock();
            }
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
        return result;
    }

    /**
     * Request an item from the queue asynchronously
     * @return - a future pending the result from the queue poll request
     */
    public Future<E> pollAsync() {
        Future<E> result = null;
        final ReentrantLock lock = this.lock;
        try {
            //grab the global lock
            lock.lock();
            //check to see if we have objects in the queue
            E item = items.poll();
            if (item==null) {
                //queue is empty, add ourselves as waiters
                ExchangeCountDownLatch<E> c = new ExchangeCountDownLatch<E>(1);
                waiters.addLast(c);
                //return a future that will wait for the object
                result = new ItemFuture<E>(c);
            } else {
                //return a future with the item
                result = new ItemFuture<E>(item);
            }
        } finally {
            lock.unlock();
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean remove(Object e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return items.remove(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return items.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<E> iterator() {
        return new FairIterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E poll() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return items.poll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(Object e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return items.contains(e);
        } finally {
            lock.unlock();
        }
    }


    //------------------------------------------------------------------
    // NOT USED BY CONPOOL IMPLEMENTATION
    //------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean add(E e) {
        return offer(e);
    }

    /**
     * {@inheritDoc}
     * @throws UnsupportedOperationException - this operation is not supported
     */
    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        throw new UnsupportedOperationException("int drainTo(Collection<? super E> c, int maxElements)");
    }

    /**
     * {@inheritDoc}
     * @throws UnsupportedOperationException - this operation is not supported
     */

    @Override
    public int drainTo(Collection<? super E> c) {
        return drainTo(c,Integer.MAX_VALUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void put(E e) throws InterruptedException {
        offer(e);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int remainingCapacity() {
        return Integer.MAX_VALUE - size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E take() throws InterruptedException {
        return this.poll(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        Iterator<? extends E> i = c.iterator();
        while (i.hasNext()) {
            E e = i.next();
            offer(e);
        }
        return true;
    }

    /**
     * {@inheritDoc}
     * @throws UnsupportedOperationException - this operation is not supported
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException("void clear()");

    }

    /**
     * {@inheritDoc}
     * @throws UnsupportedOperationException - this operation is not supported
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("boolean containsAll(Collection<?> c)");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * {@inheritDoc}
     * @throws UnsupportedOperationException - this operation is not supported
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("boolean removeAll(Collection<?> c)");
    }

    /**
     * {@inheritDoc}
     * @throws UnsupportedOperationException - this operation is not supported
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("boolean retainAll(Collection<?> c)");
    }

    /**
     * {@inheritDoc}
     * @throws UnsupportedOperationException - this operation is not supported
     */
    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("Object[] toArray()");
    }

    /**
     * {@inheritDoc}
     * @throws UnsupportedOperationException - this operation is not supported
     */
    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("<T> T[] toArray(T[] a)");
    }

    /**
     * {@inheritDoc}
     * @throws UnsupportedOperationException - this operation is not supported
     */
    @Override
    public E element() {
        throw new UnsupportedOperationException("E element()");
    }

    /**
     * {@inheritDoc}
     * @throws UnsupportedOperationException - this operation is not supported
     */
    @Override
    public E peek() {
        throw new UnsupportedOperationException("E peek()");
    }

    /**
     * {@inheritDoc}
     * @throws UnsupportedOperationException - this operation is not supported
     */
    @Override
    public E remove() {
        throw new UnsupportedOperationException("E remove()");
    }



    //------------------------------------------------------------------
    // Non cancellable Future used to check and see if a connection has been made available
    //------------------------------------------------------------------
    protected class ItemFuture<T> implements Future<T> {
        protected volatile T item = null;
        protected volatile ExchangeCountDownLatch<T> latch = null;
        protected volatile boolean canceled = false;

        public ItemFuture(T item) {
            this.item = item;
        }

        public ItemFuture(ExchangeCountDownLatch<T> latch) {
            this.latch = latch;
        }

        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false; //don't allow cancel for now
        }

        @Override
        public T get() throws InterruptedException, ExecutionException {
            if (item!=null) {
                return item;
            } else if (latch!=null) {
                latch.await();
                return latch.getItem();
            } else {
                throw new ExecutionException("ItemFuture incorrectly instantiated. Bug in the code?", new Exception());
            }
        }

        @Override
        public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            if (item!=null) {
                return item;
            } else if (latch!=null) {
                boolean timedout = !latch.await(timeout, unit);
                if (timedout) throw new TimeoutException();
                else return latch.getItem();
            } else {
                throw new ExecutionException("ItemFuture incorrectly instantiated. Bug in the code?", new Exception());
            }
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return (item!=null || latch.getItem()!=null);
        }

    }

    //------------------------------------------------------------------
    // Count down latch that can be used to exchange information
    //------------------------------------------------------------------
    protected class ExchangeCountDownLatch<T> extends CountDownLatch {
        protected volatile T item;
        public ExchangeCountDownLatch(int i) {
            super(i);
        }
        public T getItem() {
            return item;
        }
        public void setItem(T item) {
            this.item = item;
        }
    }

    //------------------------------------------------------------------
    // Iterator safe from concurrent modification exceptions
    //------------------------------------------------------------------
    protected class FairIterator implements Iterator<E> {
        E[] elements = null;
        int index;
        E element = null;

        @SuppressWarnings("unchecked") // Can't create arrays of generic types
        public FairIterator() {
            final ReentrantLock lock = FairBlockingQueue.this.lock;
            lock.lock();
            try {
                elements = (E[]) new Object[FairBlockingQueue.this.items.size()];
                FairBlockingQueue.this.items.toArray(elements);
                index = 0;
            } finally {
                lock.unlock();
            }
        }
        @Override
        public boolean hasNext() {
            return index<elements.length;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            element = elements[index++];
            return element;
        }

        @Override
        public void remove() {
            final ReentrantLock lock = FairBlockingQueue.this.lock;
            lock.lock();
            try {
                if (element!=null) {
                    FairBlockingQueue.this.items.remove(element);
                }
            } finally {
                lock.unlock();
            }
        }

    }
}
