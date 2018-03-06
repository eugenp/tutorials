package com.insightsource.pool;

/**
 * Represent a cached pool of objects.
 *
 * @param <T> the type of object to pool.
 * @author Mac
 */
public interface Pool<T> {
    /**
     * Return an instance from pool.
     * <p>
     * The call may be a blocking one or a non-blocking one and this is determined by the internal implementation.
     * If the call is blocking call, the call returns immediately with a valid object if available,
     * else the thread is made to wait until an object become available.
     * In the case of blocking call, it is advised that clients react to {@link InterruptedException}
     * which might be thrown when the thread waits for an object to become available.
     * <p>
     * If the call is non-blocking one, the call returns immediately
     * irrespective of whether an object is available or not.
     * If an object is available the call return it,
     * else the call return <code> null </code>.
     * <p>
     * The validity of the objects are determined using the {@link Validator} interface,
     * such that an object <code> o </code> is valid if <code> Validator.isValid(o) == true </code>.
     *
     * @return T one of the pooled object.
     */
    T get();

    /**
     * Release the object and put it back to the pool.
     * <p>
     * The mechanism of putting the object back to pool is generally asynchronous.
     * However, future implementations might differ.
     *
     * @param t the object to return to the pool.
     */
    void release(T t);

    /**
     * Shut down the pool.
     * In essence this call will not accept any more request and will release all resources.
     * Releasing resources are done via the <code>invalidate()</code> method of {@link Validator} interface.
     */
    void shutdown();


    /**
     * Represents the functionality to validate an object of the pool
     * and to subsequently perform cleanup activities.
     *
     * @param <T> the type of objects to validate and cleanup.
     * @author Mac
     */
    public static interface Validator<T> {
        /**
         * Checks whether the object is valid.
         *
         * @param t the object to check.
         * @return <code>true</code> if the object is valid else <code>false</code>.
         */
        public boolean isValid(T t);

        /**
         * Performs any cleanup activities before discarding the object.
         * For example before discarding database connection objects,
         * the pool will want to close the connections.
         * This is done via the <code>invalidate()</code> method.
         *
         * @param t the object to cleanup
         */
        public void invalidate(T t);
    }
}
