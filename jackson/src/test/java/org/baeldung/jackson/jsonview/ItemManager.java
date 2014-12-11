package org.baeldung.jackson.jsonview;

public class ItemManager {

    public static Item getById(final int id) {
        final User owner = new User(1, "John");
        final Item item = new Item(2, "book", owner);
        return item;
    }
}
