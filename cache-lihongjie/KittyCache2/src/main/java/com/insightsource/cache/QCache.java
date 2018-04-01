package com.insightsource.cache;

import java.util.Iterator;
import java.util.Map;

/**
 * http://www.blogjava.net/DLevin/archive/2013/10/15/404770.html
 *
 * @param <K>
 * @param <V>
 * @author Mac
 */
public interface QCache<K, V> {
    public V get(K key);

    public void put(K key, V value);

    public void remove(K key) throws Exception;

    public void clear();

    public int size();

    public Map<? extends K, ? extends V> getAll(Iterator<? extends K> keys) throws Exception;

    public void putAll(Map<? extends K, ? extends V> entries);

    public void removeAll(Iterator<? extends K> keys);
}
