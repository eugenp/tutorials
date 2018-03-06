package com.spaceprogram.bigcache;

/**
 * User: treeder
 * Date: Sep 30, 2008
 * Time: 6:33:31 PM
 */
public class BigCacheException extends Exception {
    public BigCacheException(Exception e) {
        super(e);
    }

    public BigCacheException(String s) {
        super(s);
    }
}
