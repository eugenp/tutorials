package com.baeldung.mapandhashmap.printer;

import java.util.Map;
import java.util.Map.Entry;

public class MapReporter {

    private final Map<?, ?> map;

    public MapReporter(final Map<?, ?> map) {
        this.map = map;
    }

    public void printMap() {
        for (final Entry<?, ?> entry : this.map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
