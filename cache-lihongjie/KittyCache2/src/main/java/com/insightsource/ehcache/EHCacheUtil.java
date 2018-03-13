package com.insightsource.ehcache;

import java.io.Serializable;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EHCacheUtil {
    static CacheManager manager = null;
    static String configfile = "ehcache.xml";

    static {
        try {
            manager = CacheManager.create(EHCacheUtil.class.getClassLoader().getResourceAsStream(configfile));
//            manager = CacheManager.create(getClass().getResource(configfile));
//            manager = CacheManager.create(configfile);
        } catch (CacheException e) {
            e.printStackTrace();
        }
    }

    public static void put(String cachename, Serializable key, Serializable value) {
        manager.getCache(cachename).put(new Element(key, value));
    }

    public static Object get(String cachename, Serializable key) {
        try {
            Element e = manager.getCache(cachename).get(key);
            if (e == null)
                return null;
            return e.getObjectValue();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (CacheException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void clearCache(String cachename) {
        try {
            manager.getCache(cachename).removeAll();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public static void remove(String cachename, Serializable key) {
        manager.getCache(cachename).remove(key);
    }
}
