package com.insightsource.pool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class BoundedPool<T> extends AbstractPool<T> {
    private Queue<T> objects;
    private Validator<T> validator;
    private ObjectFactory<T> objectFactory;
    private int size;

    private Semaphore permits;
    private volatile boolean shutdownCalled;

    public BoundedPool(int size, Validator<T> validator, ObjectFactory<T> objectFactory) {
        super();

        this.size = size;
        this.validator = validator;
        this.objectFactory = objectFactory;

        objects = new LinkedList<T>();
        initializeObjects();
        shutdownCalled = false;
    }

    private void initializeObjects() {
        for (int i = 0; i < size; i++) {
            objects.add(objectFactory.createNew());
        }
    }

    @Override
    public T get() {
        T t = null;
        if (!shutdownCalled) {
            if (permits.tryAcquire()) {
                t = objects.poll();
            }
        } else {
            throw new IllegalStateException("Object pool already shutdown");
        }

        return t;
    }

    @Override
    public boolean isValid(T t) {
        return validator.isValid(t);
    }

    @Override
    public void returnToPool(T t) {
        boolean added = objects.add(t);

        if (added) {
            permits.release();
        }
    }

    @Override
    public void handleInvalidReturn(T t) {

    }

    @Override
    public void shutdown() {
        shutdownCalled = true;
        clearResources();
    }

    private void clearResources() {
        for (T t : objects) {
            validator.invalidate(t);
        }
    }
}
