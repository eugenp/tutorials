package com.baeldung.prototype;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private final Map<String, Item> itemMap;

    public ItemManager(Map<String, Item> itemMap) {
        this.itemMap = new HashMap<>(itemMap);
    }

    public Item getItem(String name) {
        if (itemMap.get(name) != null) {
            return itemMap.get(name).clone();
        }
        throw new IllegalArgumentException("No item found, please create new one");
    }
}
