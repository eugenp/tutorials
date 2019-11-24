package com.baeldung.jackson.annotation.bidirection;

public class ItemWithIgnore {
    public int id;
    public String itemName;
    public UserWithIgnore owner;

    public ItemWithIgnore() {
        super();
    }

    public ItemWithIgnore(final int id, final String itemName, final UserWithIgnore owner) {
        this.id = id;
        this.itemName = itemName;
        this.owner = owner;
    }
}
