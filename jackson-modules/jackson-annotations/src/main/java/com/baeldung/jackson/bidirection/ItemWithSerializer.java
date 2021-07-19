package com.baeldung.jackson.bidirection;

public class ItemWithSerializer {
    public int id;
    public String itemName;
    public UserWithSerializer owner;

    public ItemWithSerializer() {
        super();
    }

    public ItemWithSerializer(final int id, final String itemName, final UserWithSerializer owner) {
        this.id = id;
        this.itemName = itemName;
        this.owner = owner;
    }
}
