package jmxbp.ex1;

import  java.util.ArrayList;
import  java.lang.reflect.*;


/**
 * A very simple queue.
 * Implemented as a dynamic MBean.
 */
public class Queue extends Basic {
    private static final int DEFAULT_QUEUE_SIZE = 10;
    private static final int WAIT_FOREVER = -1;
    // Queue guts
    private int _head;
    private int _tail;
    private Object[] _backingStore;
    private boolean _suspended;
    private boolean _queueFull;
    private boolean _queueEmpty;
    private ArrayList _suppliers = new ArrayList();
    private ArrayList _consumers = new ArrayList();
    private boolean _endOfInput;
    // Metrics
    private long _addWaitTime;
    private long _removeWaitTime;
    private long _numberOfItemsProcessed;

    public Queue () {
        this(DEFAULT_QUEUE_SIZE);
    }
    public Queue (int queueSize) {
        super();
        // Create the backing store. If the specified queue size is
        /// bogus, use the default queue size
        _backingStore = new Object[(queueSize > 0) ? queueSize : DEFAULT_QUEUE_SIZE];
        _queueEmpty = true;
    }

    public synchronized void addSupplier () {
        String supplierName = Thread.currentThread().getName();
        if (isTraceOn())
            System.out.println("Queue.addSupplier(): INFO: " + "Adding supplier \'"
                    + supplierName + "\'.");
        _suppliers.add(supplierName);
    }

    public synchronized void removeSupplier () {
        String supplier = Thread.currentThread().getName();
        if (isTraceOn())
            System.out.println("Queue.removeSupplier(): INFO: " + "Looking for supplier \'"
                    + supplier + "\' to remove it.");
        for (int aa = 0; aa < _suppliers.size(); aa++) {
            String s = (String)_suppliers.get(aa);
            if (supplier.equals(s)) {
                if (isTraceOn())
                    System.out.println("Queue.removeSupplier(): INFO: " + "Removing supplier \'"
                            + s + "\'.");
                _suppliers.remove(aa);
                notifyAll();
                break;
            }
        }
        if (_suppliers.size() == 0)
            _endOfInput = true;
    }

    public synchronized void addConsumer () {
        String consumerName = Thread.currentThread().getName();
        if (isTraceOn())
            System.out.println("Queue.addConsumer(): INFO: " + "Adding consumer \'"
                    + consumerName + "\'.");
        _consumers.add(consumerName);
    }

    public synchronized void removeConsumer () {
        String consumer = Thread.currentThread().getName();
        if (isTraceOn())
            System.out.println("Queue.removeConsumer(): INFO: " + "Looking for consumer \'"
                    + consumer + "\' to remove it.");
        for (int aa = 0; aa < _consumers.size(); aa++) {
            String s = (String)_consumers.get(aa);
            if (consumer.equals(s)) {
                if (isTraceOn())
                    System.out.println("Queue.removeConsumer(): INFO: " + "Removing consumer \'"
                            + s + "\'.");
                _consumers.remove(aa);
                notifyAll();
                break;
            }
        }
        if (_consumers.size() == 0)
            _endOfInput = true;
    }

    public synchronized void add (Object item) {
        long addWaitTime = 0;
        while (_suspended || _tail == -1) {
            long waitStart = System.currentTimeMillis();
            try {
                wait();
            } catch (InterruptedException e) {}
            addWaitTime += (System.currentTimeMillis() - waitStart);
        }
        _addWaitTime += addWaitTime;
        // add the item to the queue's backing store
        _backingStore[_tail] = item;
        _queueEmpty = false;
        _tail++;
        if (_tail >= _backingStore.length)
            _tail = 0;          // wrap
        if (_tail == _head) {
            _tail = -1;         // special case, we're full
        }
        _queueFull = (_tail == -1) ? true : false;
        notifyAll();
    }

    public synchronized Object remove () {
        return  (remove(WAIT_FOREVER));
    }

    public synchronized Object remove (long timeout) {
        boolean timedOut = false;
        Object ret = null;
        long removeWaitTime = 0;
        if (!_endOfInput) {
            while (_suspended || _tail == _head) {
                long waitStart = System.currentTimeMillis();
                try {
                    if (timeout == WAIT_FOREVER)
                        wait(); 
                    else 
                        wait(timeout/1000);
                } catch (InterruptedException e) {}
                if (_endOfInput)
                    break;
                if (timeout != -1 && (System.currentTimeMillis() - waitStart) > timeout) {
                    timedOut = true;
                    break;
                }
                removeWaitTime += System.currentTimeMillis() - waitStart;
            }
            if (!timedOut) {
                _removeWaitTime += removeWaitTime;
                ret = _backingStore[_head];
                _queueFull = false;
                _numberOfItemsProcessed++;
                _backingStore[_head] = null;
                if (_tail == -1)
                    _tail = _head;              // point tail to new free spot
                _head++;
                if (_head >= _backingStore.length)
                    _head = 0;                  // wrap head pointer
                _queueEmpty = (_head == _tail) ? true : false;
            }
        }
        notifyAll();
        return  ret;
    }
    public int getQueueSize () {
        return  _backingStore.length;
    }
    public synchronized void setQueueSize (int queueSize) {
        // Can't set queue size on a suspended queue
        if (!_suspended) {
            // Only allow the queue to grow, not shrink...
            if (queueSize > _backingStore.length) {
                // allocate new array
                Object[] newStore = new Object[queueSize];
                System.arraycopy(_backingStore, 0, newStore, 0, _backingStore.length);
            }
        }
        notifyAll();
    }

    public long getNumberOfItemsProcessed () {
        return  _numberOfItemsProcessed;
    }
    public void setNumberOfItemsProcessed (long value) {
        _numberOfItemsProcessed = value;
    }

    public long getAddWaitTime () {
        return  _addWaitTime;
    }
    public void setAddWaitTime (long value) {
        _addWaitTime = value;
    }

    public long getRemoveWaitTime () {
        return  _removeWaitTime;
    }
    public void setRemoveWaitTime (long value) {
        _removeWaitTime = value;
    }

    public Object[] getBackingStore () {
        return  _backingStore;
    }

    public boolean isQueueFull () {
        return  _queueFull;
    }

    public boolean isQueueEmpty () {
        return  _queueEmpty;
    }

    public boolean isSuspended () {
        return  _suspended;
    }

    public boolean isEndOfInput () {
        return  _endOfInput;
    }

    public int getNumberOfSuppliers () {
        return  _suppliers.size();
    }

    public int getNumberOfConsumers () {
        return  _consumers.size();
    }

    public synchronized void suspend () {
        _suspended = true;
    }

    public synchronized void resume () {
        _suspended = false;
        notifyAll();
    }

    public void reset () {
        setNumberOfItemsProcessed(0);
        setAddWaitTime(0);
        setRemoveWaitTime(0);
        setNumberOfResets(getNumberOfResets() + 1);
    }
    private String[] _stateAsStringArray = new String[10];


}



