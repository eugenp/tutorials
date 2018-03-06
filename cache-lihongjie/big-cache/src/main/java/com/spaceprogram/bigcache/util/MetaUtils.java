package com.spaceprogram.bigcache.util;

/**
 * User: treeder
 * Date: Jan 13, 2009
 * Time: 8:47:48 PM
 */
public class MetaUtils {
    public static String expiryAsString(int expiresInSeconds) {
        if (expiresInSeconds <= 0) {
            return "0"; // no expiry
        }
        return Long.toString(System.currentTimeMillis() + ((long) expiresInSeconds * 1000L));
    }
}
