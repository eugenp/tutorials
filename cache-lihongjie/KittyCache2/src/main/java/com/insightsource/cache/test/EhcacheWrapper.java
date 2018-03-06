package com.insightsource.cache.test;

import java.util.Collection;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.insightsource.cache.KCache;

public class EhcacheWrapper<K, V> implements KCache<K, V> {
    private Cache ehcache;

    public EhcacheWrapper(Cache ehcache) {
        this.ehcache = ehcache;
    }

    public void put(Object key, Object value, Integer secondsToLive) {
        ehcache.put(new Element(key, value));
    }

    @Override
    public void put(K key, V value, int secondsToLive) {
        // TODO Auto-generated method stub

    }

    @Override
    public V get(K key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<K, V> getAll(Collection<K> collection) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean remove(K key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public V removeAndGet(K key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

}
