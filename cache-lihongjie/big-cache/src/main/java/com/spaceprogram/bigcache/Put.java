package com.spaceprogram.bigcache;

import org.jets3t.service.model.S3Object;

import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User: treeder
 * Date: Nov 14, 2008
 * Time: 10:21:01 AM
 */
public class Put implements Callable {

    private static Logger logger = Logger.getLogger(Put.class.getName());

    private BigCache cache;

    private String key;

    private Object object;

    private int expiresInSeconds;

    public Put(BigCache cache, String key, Object object, int expiresInSeconds) {
        this.cache = cache;
        this.key = key;
        this.object = object;
        this.expiresInSeconds = expiresInSeconds;
    }

    public Object call() throws Exception {
        logger.fine("DelayedPut running [key=" + key + "], putting " + object);
        try {
            // put below removes the object from the delayMap
            cache.put(key, object, expiresInSeconds);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error on Put operation: " + e.getMessage(), e);
            throw e;
        }
        return object;
    }
}
