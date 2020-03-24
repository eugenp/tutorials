package com.baeldung.typechecker;

import org.checkerframework.checker.nullness.qual.KeyFor;

import java.util.HashMap;
import java.util.Map;

public class KeyForExample {

    private final Map<String, String> config = new HashMap<>();

    KeyForExample() {

        // Here we initialize a map to store
        // some config data.
        config.put("url", "http://1.2.3.4");
        config.put("name", "foobaz");
    }

    public void dumpPort() {

        // Here, we want to dump the port value stored in the
        // config, so we declare that this key has to be
        // present in the config map.
        // Obviously that will fail because such key is not present
        // in the map.
        @KeyFor("config") String key = "port";
        System.out.println( config.get(key) );
    }

}
