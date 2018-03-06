package com.spaceprogram.bigcache;

import java.io.Serializable;

/**
 * User: treeder
 * Date: Aug 28, 2008
 * Time: 10:36:50 AM
 */
public class CacheItem implements Serializable {

    private Serializable object;

    private long expirationDate;

    public CacheItem() {
    }

    public CacheItem(Serializable object, int expiresInSeconds) {
        this.object = object;
        this.expirationDate = System.currentTimeMillis() + expiresInSeconds;
    }

    public long getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(long expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Serializable getObject() {
        return object;
    }

    public void setObject(Serializable object) {
        this.object = object;
    }
}
