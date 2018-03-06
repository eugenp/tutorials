package com.spaceprogram.bigcache;

import org.jets3t.service.S3ServiceException;
import org.jets3t.service.model.S3Object;

import java.io.IOException;
import java.util.concurrent.Future;

/**
 * Main cache interface.
 * <p>
 * User: treeder
 * Date: Aug 28, 2008
 * Time: 9:22:30 AM
 */
public interface BigCache {

    /**
     * Unconditional set.
     *
     * @param key
     * @param object
     * @param expiresInSeconds
     * @throws S3ServiceException
     * @throws IOException
     */
    void put(String key, S3Object object, int expiresInSeconds) throws Exception;

    /**
     * Adds to cache only if it doesn't exist.
     *
     * @param key
     * @param object
     * @param expiresInSeconds
     */
    void add(String key, S3Object object, int expiresInSeconds) throws Exception;

    /**
     * Sets in cache only if it does exist.
     *
     * @param key
     * @param object
     * @param expiresInSeconds
     */
    void replace(String key, S3Object object, int expiresInSeconds) throws Exception;

    /**
     * Gets the object from the cache.
     *
     * @param key
     */
    Object get(String key) throws Exception;

    /**
     * Removes the object from the cache.
     *
     * @param key
     * @throws Exception
     */
    void remove(String key) throws Exception;

    /**
     * Puts an object to the cache in the background. Returns immediately.
     *
     * @param key
     * @param object
     * @param expiresInSeconds
     * @return
     */
    Future<Object> putAsync(String key, S3Object object, int expiresInSeconds);

    /**
     * Gets an object from the cache in the background. Returns immediately.
     * <p>
     * Useful in various scenarios, but one in
     * particular is for getting objects in a webapp before rendering the view. When rendering the view
     * you call get() on the future that is returned from this method.
     *
     * @param key
     * @return
     */
    Future<Object> getAsync(String key);

    /**
     * Removes an object from the cache in the background. Returns immediately.
     *
     * @param key
     * @return Future.get always returns null, but you can still use it to check for exceptions.
     */
    Future<Object> removeAsync(String key);
}
