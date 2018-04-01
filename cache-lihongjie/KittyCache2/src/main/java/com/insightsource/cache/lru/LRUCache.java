package com.insightsource.cache.lru;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
 * Reference URL: http://www.ajkp.ca/2014/02/06/how-to-implement-a-lru-cache/
 */
public class LRUCache {
    /*
     * Multi-threads:
     * Map<Key, Value> example = Collections.synchronizedMap(new LRUCache<Key, Value>(CACHE_SIZE));
     */
    private class SimpleLRUCache<Key, Value> extends LinkedHashMap<Key, Value> {
        private final int size;

        public SimpleLRUCache(final int size) {
            super(size + 1, 1.0f, true);
            this.size = size;
        }

        protected boolean removeEldestEntry(final Map.Entry<Key, Value> oldest) {
            return super.size() > size;
        }
    }

    private final int size;
    private ConcurrentLinkedQueue<Key> linkedQueue;
    private ConcurrentHashMap<Key, Value> hashMap;

    public LRUCache(final int size) {
        this.size = size;
        this.linkedQueue = new ConcurrentLinkedQueue<Key>();
        this.hashMap = new ConcurrentHashMap<Key, Value>(size);
    }

    public Value get(Key key) {
        return hashMap.get(key);
    }

    public synchronized void put(Key key, Value value) {
        if (hashMap.containsKey(key)) {
            linkedQueue.remove(key);
        }

        while (linkedQueue.size() > size) {
            Key oldestKey = linkedQueue.poll();
            if (oldestKey != null) {
                hashMap.remove(oldestKey);
            }
        }

        linkedQueue.add(key);
        hashMap.put(key, value);
    }
}

