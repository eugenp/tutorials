package com.spaceprogram.bigcache;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Extended interface for additional methods.
 * <p>
 * User: treeder
 * Date: Sep 4, 2008
 * Time: 7:21:28 PM
 */
public interface BigCacheExt extends BigCache {

    /**
     * Will delay a put for delay milliseconds, and only put the LAST instance of a particular key.  So if the delay is
     * 5000 and you put for key X once per second, only the 5th put for key X will actually go through.
     *
     * @param scheduledExecutorService
     * @param delay                    delay until executed
     * @param key
     * @param object
     * @param expiresInSeconds
     */
    void putDelayed(ScheduledExecutorService scheduledExecutorService, long delay, String key, Serializable object, int expiresInSeconds);

    /**
     * Puts an object into the cache asynchronously using the executorService
     *
     * @param executorService
     * @param key
     * @param object
     * @param expiresInSeconds
     */
    void putAsync(ExecutorService executorService, String key, Serializable object, int expiresInSeconds);

    /**
     * Get statistics for the cache providing put counts, get counts, hit/miss counts, etc.
     *
     * @return
     */
    Statistics getStatistics();
}
