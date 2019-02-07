package org.baeldung.web.dto;

public class ItemManager {

    public static Item getById(final int id) {
        final Item item = new Item(2, "book", "John");
        return item;
    }
}
