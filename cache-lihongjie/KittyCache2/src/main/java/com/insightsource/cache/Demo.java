package com.insightsource.cache;

public class Demo {

    public static void main(String[] args) {
        Object value = new Object();

        // Create a new cache
        KittyCache<String, Object> cache = new KittyCache<String, Object>(5000); // 5000 is max number of objects
        // Put an object into the cache
        cache.put("mykey", value, 500); // 500 is time to live in seconds
        // Get an object from the cache
        value = cache.get("mykey");
    }

}
