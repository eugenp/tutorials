package com.baeldung.jcache;

import java.io.Serializable;

import javax.cache.event.CacheEntryCreatedListener;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryListenerException;
import javax.cache.event.CacheEntryUpdatedListener;

public class SimpleCacheEntryListener implements CacheEntryCreatedListener<String, String>, CacheEntryUpdatedListener<String, String>, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -712657810462878763L;
    private boolean updated;
    private boolean created;

    public boolean getUpdated() {
        return this.updated;
    }

    public boolean getCreated() {
        return this.created;
    }

    public void onUpdated(Iterable<CacheEntryEvent<? extends String, ? extends String>> events) throws CacheEntryListenerException {
        this.updated = true;
    }

    public void onCreated(Iterable<CacheEntryEvent<? extends String, ? extends String>> events) throws CacheEntryListenerException {
        this.created = true;
    }
}