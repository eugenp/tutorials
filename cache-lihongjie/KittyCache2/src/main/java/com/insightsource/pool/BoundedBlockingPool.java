package com.insightsource.pool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BoundedBlockingPool<T> extends AbstractPool<T> implements
        BlockingPool<T> {
    private int size;
    private Validator<T> validator;
    private ObjectFactory<T> objectFactory;

    private BlockingQueue<T> objects;

    private volatile boolean shutdownCalled;

    private ExecutorService executor = Executors.newCachedThreadPool();

    public BoundedBlockingPool(int size, Validator<T> validator, ObjectFactory<T> objectFactory) {
        super();

        this.size = size;
        this.validator = validator;
        this.objectFactory = objectFactory;

        objects = new LinkedBlockingQueue<T>(size);

        shutdownCalled = false;

        initializeObject();
    }

    private void initializeObject() {
        for (int i = 0; i < size; i++) {
            objects.add(objectFactory.createNew());
        }
    }

    @Override
    public T get(long timeOut, TimeUnit unit) {
        if (!shutdownCalled) {
            T t = null;
            try {
                t = objects.poll(timeOut, unit);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            return t;
        }

        throw new IllegalStateException("Object pool is already shutdown");
    }

    @Override
    public T get() {
        if (!shutdownCalled) {
            T t = null;
            try {
                t = objects.take();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            return t;
        }

        throw new IllegalStateException("Object pool is already shutdown");
    }

    @Override
    public void shutdown() {
        shutdownCalled = true;
        executor.shutdownNow();
        clearResources();
    }

    private void clearResources() {
        for (T t : objects) {
            validator.invalidate(t);
        }
    }

    @Override
    protected boolean isValid(T t) {
        return validator.isValid(t);
    }

    @Override
    protected void returnToPool(T t) {
        if (validator.isValid(t)) {
            executor.submit(new ObjectReturner<T>(objects, t));
        }
    }

    @Override
    protected void handleInvalidReturn(T t) {

    }

    private class ObjectReturner<E> implements Callable<Void> {
        private BlockingQueue<E> queue;
        private E t;

        public ObjectReturner(BlockingQueue<E> queue, E t) {
            this.queue = queue;
            this.t = t;
        }

        @Override
        public Void call() {
            while (true) {
                try {
                    queue.put(t);
                    break;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            return null;
        }
    }
}
