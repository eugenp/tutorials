package com.spaceprogram.bigcache;

import com.spaceprogram.bigcache.marshallers.JAXBMarshaller;
import com.spaceprogram.bigcache.marshallers.Marshaller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Base class that cache implementations can subclass to get some base functionality out of the box.
 * <p>
 * User: treeder
 * Date: Jan 13, 2009
 * Time: 7:42:54 PM
 */
public abstract class AbstractCache implements BigCache {

    protected AtomicStatistics statistics = new AtomicStatistics();

    protected Marshaller marshaller = new JAXBMarshaller();

    protected ExecutorService executorService;

    /**
     * Puts an object to the cache in the background. Returns immediately.
     *
     * @param key
     * @param object
     * @param expiresInSeconds
     * @return
     */
    public Future<Object> putAsync(String key, Object object, int expiresInSeconds) {
        checkExecutor();
        Put put = new Put(this, key, object, expiresInSeconds);
        return executorService.submit(put);
    }

    /**
     * Gets an object from the cache in the background. Useful in various scenarios, but one in
     * particular is for getting objects in a webapp before rendering the view. When rendering the view
     * you call get() on the future that is returned from this method.
     *
     * @param key
     * @return
     */
    public Future<Object> getAsync(String key) {
        checkExecutor();
        Get get = new Get(this, key);
        return executorService.submit(get);
    }

    public Future<Object> removeAsync(String key) {
        checkExecutor();
        Remove remove = new Remove(this, key);
        return executorService.submit(remove);
    }

    private void checkExecutor() {
        if (executorService == null) {
            throw new BigCacheRuntimeException("ExecutorService is null. Can not perform operation.");
        }
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setMarshaller(JAXBMarshaller marshaller) {
        this.marshaller = marshaller;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
