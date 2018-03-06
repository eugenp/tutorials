package com.insightsource.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A very simple cache using java.util.concurrent.
 * <p/>
 * User: treeder, innovativeravi Date: Mar 15, 2009 Time: 8:42:01 PM
 */
public class KittyCache<K, V> implements KCache<K, V> {

    private Map<K, CacheEntry<V>> cache;
    /**
     * Used to restrict the size of the cache map.
     */
    private Queue<K> queue;
    private int maxSize;
    /**
     * Using this integer because ConcurrentLinkedQueue.size is not constant
     * time.
     */
    private AtomicInteger cacheSize = new AtomicInteger();

    public KittyCache(int maxSize) {
        this.maxSize = maxSize;
        cache = new ConcurrentHashMap<K, CacheEntry<V>>(maxSize);
        queue = new ConcurrentLinkedQueue<K>();
    }

    /**
     * {@inheritDoc}
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Invalid Key.");
        }

        CacheEntry<V> entry = cache.get(key);

        if (entry == null) {
            return null;
        }

        long timestamp = entry.getExpireBy();
        if (timestamp != -1 && System.currentTimeMillis() > timestamp) {
            remove(key);
            return null;
        }
        return entry.getEntry();
    }

    /**
     * @param key
     */
    public V removeAndGet(K key) {

        if (key == null) {
            return null;
        }

        CacheEntry<V> entry = cache.get(key);
        if (entry != null) {
            cacheSize.decrementAndGet();
            return cache.remove(key).getEntry();
        }

        return null;
    }

    /**
     * {@inheritDoc}
     */
    public void put(K key, V value, int secondsToLive) {
        if (key == null) {
            throw new IllegalArgumentException("Invalid Key.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Invalid Value.");
        }

        long expireBy = secondsToLive != -1 ? System.currentTimeMillis()
                + (secondsToLive * 1000) : secondsToLive;
        boolean exists = cache.containsKey(key);
        if (!exists) {
            cacheSize.incrementAndGet();
            while (cacheSize.get() > maxSize) {
                remove(queue.poll());
            }
        }
        cache.put(key, new CacheEntry<V>(expireBy, value));
        queue.add(key);
    }

    /**
     * Returns boolean to stay compatible with ehcache and memcached.
     *
     * @see #removeAndGet for alternate version.
     */
    public boolean remove(K key) {
        return removeAndGet(key) != null;
    }

    public int size() {
        return cacheSize.get();
    }

    /**
     * @param collection
     * @return
     */
    public Map<K, V> getAll(Collection<K> collection) {
        Map<K, V> ret = new HashMap<K, V>();
        for (K o : collection) {
            ret.put(o, get(o));
        }
        return ret;
    }

    public void clear() {
        cache.clear();
    }

    public int mapSize() {
        return cache.size();
    }

    public int queueSize() {
        return queue.size();
    }

    private class CacheEntry<T> {
        private long expireBy;
        private T entry;

        public CacheEntry(long expireBy, T entry) {
            super();
            this.expireBy = expireBy;
            this.entry = entry;
        }

        /**
         * @return the expireBy
         */
        public long getExpireBy() {
            return expireBy;
        }

        /**
         * @return the entry
         */
        public T getEntry() {
            return entry;
        }

    }

}
