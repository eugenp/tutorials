package com.baeldung;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;

public class IgniteCacheExample {

    public static void main(String [] args) {

        Ignite ignite = Ignition.ignite();

        final IgniteCache<Integer, String> cache = ignite.cache("baeldungCache");

        cache.put(1, "baeldung cache");

        String message = cache.get(1);
    }

}
