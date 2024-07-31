package com.baeldung.mapandhashmap.printer;

import java.util.HashMap;
import java.util.Map.Entry;

public class HashMapPrinter {

    public void printMap(final HashMap<?, ?> map) {
        for (final Entry<?, ?> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
