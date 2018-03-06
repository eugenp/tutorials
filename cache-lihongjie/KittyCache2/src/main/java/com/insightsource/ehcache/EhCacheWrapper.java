package com.insightsource.ehcache;

import java.util.Iterator;
import java.util.Map;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import com.insightsource.cache.QCache;

public class EhCacheWrapper<K, V> implements QCache<K, V> {
    private final String cacheName;
    private final CacheManager cacheManager;

    public EhCacheWrapper(final String cacheName, final CacheManager cacheManager) {
        this.cacheName = cacheName;
        this.cacheManager = cacheManager;
    }

    public void put(final K key, final V value) {
        getCache().put(new Element(key, value));
    }

    public V get(final K key) {
        Element element = getCache().get(key);
        if (element != null) {
            return (V) element.getObjectValue();
        }
        return null;
    }

    public Ehcache getCache() {
        return cacheManager.getEhcache(cacheName);
    }

    @Override
    public void remove(K key) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Map<? extends K, ? extends V> getAll(Iterator<? extends K> keys)
            throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> entries) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeAll(Iterator<? extends K> keys) {
        // TODO Auto-generated method stub

    }

    public void shutdown() {
        cacheManager.shutdown();
    }
}
