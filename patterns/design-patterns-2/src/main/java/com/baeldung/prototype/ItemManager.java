package com.baeldung.prototype;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private final Map<String, Item> itemManager;

    public ItemManager(Map<String, Item> itemManager) {
        this.itemManager = new HashMap<>(itemManager);
    }

    public Item getShape(String name) {
        if (itemManager.get(name) != null) {
            return itemManager.get(name).clone();
        }
        throw new IllegalArgumentException("No item found, please create new one");
    }
}
