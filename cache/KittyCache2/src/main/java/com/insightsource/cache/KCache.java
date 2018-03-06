package com.insightsource.cache;

import java.util.Collection;
import java.util.Map;

public interface KCache<K, V> {
    /**
     * Inserts an entry into cache that will expire after the number of seconds
     * specified.
     *
     * @param key           key of the entry that is used as handle, to retrieve at a
     *                      later point of time or to delete the entry before its expiry time.
     * @param value         The actual object that is to be cached.
     * @param secondsToLive expiry time in seconds. so if the cache entry needs to be
     *                      expired in 5 minutes, expiryTime needs to be set as 300. If
     *                      the cache entry doesn't need an expiry time, set it as -1.
     * @throws IllegalArgumentException Throws illegal argument exception, if the key or value is
     *                                  null.
     */
    void put(K key, V value, int secondsToLive);

    /**
     * Retrieves an entry from the cache that has been cached before using
     * {@link #put(Object, Object, int)}
     *
     * @param key handle of the entry with which the object has been cached.
     * @return Returns the cached entry. Returns null, if there is no entry with
     * the key K or if the entry has expired.
     */
    V get(K key);

    /**
     * Retrieves all the entries from the cache using a collection of keys.
     *
     * @param collection Collection of keys for which the cache entries need to be
     *                   returned.
     * @return Returns a map of cache entries keyed in by the key.
     */
    Map<K, V> getAll(Collection<K> collection);

    /**
     * Clears all the entries in the cache regardless of any conditions.
     */
    void clear();

    /**
     * Removes an entry from the cache using the key K. Returns true, if the
     * remove operation is successful. Else, returns false.
     *
     * @param key The handle of the entry that has to be removed.
     * @return Returns true, if the removal of the entry from the cache is
     * successful. Else returns false.
     */
    boolean remove(K key);

    /**
     * Removes an entry from the cache using the key K. Returns object that has
     * been removed. If the operation is not successful, returns <code>null</code>.
     *
     * @param key The handle of the entry that has to be removed.
     * @return Returns object that has
     * been removed. If the operation is not successful, returns <code>null</code>
     */
    V removeAndGet(K key);

    /**
     * @return Returns the size of the cache.
     */
    int size();
}
