package com.baeldung.deserialization;

public class ItemWithWrappedUser {

    public int id;
    public String itemName;
    public Wrapper<User> owner;

    public ItemWithWrappedUser() {
        super();
    }

    public ItemWithWrappedUser(final int id, final String itemName, final Wrapper<User> owner) {
        this.id = id;
        this.itemName = itemName;
        this.owner = owner;
    }
    
    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public Wrapper<User> getOwner() {
        return owner;
    }

}
