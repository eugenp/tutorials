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
package org.apache.jasper.util;

/**
 *
 * The FastRemovalDequeue is a Dequeue that supports constant time removal of
 * entries. This is achieved by using a doubly linked list and wrapping any object
 * added to the collection with an Entry type, that is returned to the consumer.
 * When removing an object from the list, the consumer provides this Entry object.
 *
 * The Entry type is nearly opaque to the consumer of the queue. The only public
 * member is the getter for any object displaced when adding a new object to the
 * queue. This can be used to destroy that object.
 *
 * The Entry object contains the links pointing to the neighbours in the doubly
 * linked list, so that removal of an Entry does not need to search for it but
 * instead can be done in constant time.
 *
 * The implementation is fully thread-safe.
 *
 * Invalidation of Entry objects during removal from the list is done
 * by setting their "valid" field to false. All public methods which take Entry
 * objects as arguments are NOP if the entry is no longer valid.
 *
 * A typical use of the FastRemovalDequeue is a list of entries in sorted order,
 * where the sort position of an object will only switch to first or last.
 *
 * Whenever the sort position needs to change, the consumer can remove the object
 * and reinsert it in front or at the end in constant time.
 * So keeping the list sorted is very cheap.
 *
 * @param <T> The type of elements in the queue
 */
public class FastRemovalDequeue<T> {

    /** Maximum size of the queue */
    private final int maxSize;
    /** First element of the queue. */
    protected Entry first;
    /** Last element of the queue. */
    protected Entry last;
    /** Size of the queue */
    private int size;

    /**
     * Initialize empty queue.
     *
     * @param maxSize The maximum size to which the queue will be allowed to
     *                grow
     */
    public FastRemovalDequeue(int maxSize) {
        if (maxSize <=1 ) {
            maxSize = 2;
        }
        this.maxSize = maxSize;
        first = null;
        last = null;
        size = 0;
    }

    /**
     * Retrieve the size of the list.
     * This method also needs to be externally synchronized to
     * ensure correct publication of changes.
     *
     * @return the size of the list.
     * */
    public synchronized int getSize() {
        return size;
    }

    /**
     * Adds an object to the start of the list and returns the entry created for
     * said object. The entry can later be reused for moving the entry.
     *
     * @param object the object to prepend to the start of the list.
     * @return an entry for use when the object should be moved.
     * */
    public synchronized Entry push(final T object) {
        Entry entry = new Entry(object);
        if (size >= maxSize) {
            entry.setReplaced(pop());
        }
        if (first == null) {
            first = last = entry;
        } else {
            first.setPrevious(entry);
            entry.setNext(first);
            first = entry;
        }
        size++;

        return entry;
    }

    /**
     * Adds an object to the end of the list and returns the entry created for
     * said object. The entry can later be reused for moving the entry.
     *
     * @param object the object to append to the end of the list.
     * @return an entry for use when the object should be moved.
     * */
    public synchronized Entry unpop(final T object) {
        Entry entry = new Entry(object);
        if (size >= maxSize) {
            entry.setReplaced(unpush());
        }
        if (first == null) {
            first = last = entry;
        } else {
            last.setNext(entry);
            entry.setPrevious(last);
            last = entry;
        }
        size++;

        return entry;
    }

    /**
     * Removes the first element of the list and returns its content.
     *
     * @return the content of the first element of the list.
     **/
    public synchronized T unpush() {
        T content = null;
        if (first != null) {
            Entry element = first;
            first = first.getNext();
            content = element.getContent();
            if (first == null) {
                last =null;
            } else {
                first.setPrevious(null);
            }
            size--;
            element.invalidate();
        }
        return content;
    }

    /**
     * Removes the last element of the list and returns its content.
     *
     * @return the content of the last element of the list.
     **/
    public synchronized T pop() {
        T content = null;
        if (last != null) {
            Entry element = last;
            last = last.getPrevious();
            content = element.getContent();
            if (last == null) {
                first = null;
            } else {
                last.setNext(null);
            }
            size--;
            element.invalidate();
        }
        return content;
    }

    /**
     * Removes any element of the list and returns its content.
     *
     * @param element The element to remove
     */
    public synchronized void remove(final Entry element) {
        if (element == null || !element.getValid()) {
            return;
        }
        Entry next = element.getNext();
        Entry prev = element.getPrevious();
        if (next != null) {
            next.setPrevious(prev);
        } else {
            last = prev;
        }
        if (prev != null) {
            prev.setNext(next);
        } else {
            first = next;
        }
        size--;
        element.invalidate();
    }

    /**
     * Moves the element in front.
     *
     * Could also be implemented as remove() and
     * push(), but explicitly coding might be a bit faster.
     *
     * @param element the entry to move in front.
     * */
    public synchronized void moveFirst(final Entry element) {
        if (element.getValid() &&
            element.getPrevious() != null) {
            Entry prev = element.getPrevious();
            Entry next = element.getNext();
            prev.setNext(next);
            if (next != null) {
                next.setPrevious(prev);
            } else {
                last = prev;
            }
            first.setPrevious(element);
            element.setNext(first);
            element.setPrevious(null);
            first = element;
        }
    }

    /**
     * Moves the element to the back.
     *
     * Could also be implemented as remove() and
     * unpop(), but explicitely coding might be a bit faster.
     *
     * @param element the entry to move to the back.
     * */
    public synchronized void moveLast(final Entry element) {
        if (element.getValid() &&
            element.getNext() != null) {
            Entry next = element.getNext();
            Entry prev = element.getPrevious();
            next.setPrevious(prev);
            if (prev != null) {
                prev.setNext(next);
            } else {
                first = next;
            }
            last.setNext(element);
            element.setPrevious(last);
            element.setNext(null);
            last = element;
        }
    }

    /**
     * Implementation of a doubly linked list entry.
     * All implementation details are private.
     * For the consumer of the above collection, this
     * is simply garbage in, garbage out.
     */
    public class Entry {

        /** Is this entry still valid? */
        private boolean valid = true;
        /** The content this entry is valid for. */
        private final T content;
        /** Optional content that was displaced by this entry */
        private T replaced = null;
        /** Pointer to next element in queue. */
        private Entry next = null;
        /** Pointer to previous element in queue. */
        private Entry previous = null;

        private Entry(T object) {
            content = object;
        }

        private final boolean getValid() {
            return valid;
        }

        private final void invalidate() {
            this.valid = false;
            this.previous = null;
            this.next = null;
        }

        public final T getContent() {
            return content;
        }

        public final T getReplaced() {
            return replaced;
        }

        private final void setReplaced(final T replaced) {
            this.replaced = replaced;
        }

        public final void clearReplaced() {
            this.replaced = null;
        }

        private final Entry getNext() {
            return next;
        }

        private final void setNext(final Entry next) {
            this.next = next;
        }

        private final Entry getPrevious() {
            return previous;
        }

        private final void setPrevious(final Entry previous) {
            this.previous = previous;
        }

        @Override
        public String toString() {
            return "Entry-" + content.toString();
        }
    }

}
