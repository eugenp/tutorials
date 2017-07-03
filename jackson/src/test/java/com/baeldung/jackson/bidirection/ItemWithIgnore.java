package com.baeldung.jackson.bidirection;

public class ItemWithIgnore {
    private int id;
    private String itemName;
    private UserWithIgnore owner;

    public ItemWithIgnore() {
        super();
    }

    public ItemWithIgnore(final int id, final String itemName, final UserWithIgnore owner) {
        this.id = id;
        this.itemName = itemName;
        this.owner = owner;
    }
}
