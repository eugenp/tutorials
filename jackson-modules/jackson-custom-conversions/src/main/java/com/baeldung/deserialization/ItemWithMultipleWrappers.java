package com.baeldung.deserialization;

public class ItemWithMultipleWrappers {

    private int id;
    private String itemName;
    private Wrapper<User> owner;
    private Wrapper<Integer> count;

    public ItemWithMultipleWrappers() {
        super();
    }

    public ItemWithMultipleWrappers(int id, String itemName, Wrapper<User> owner, Wrapper<Integer> count) {
        this.id = id;
        this.itemName = itemName;
        this.owner = owner;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Wrapper<User> getOwner() {
        return owner;
    }

    public void setOwner(Wrapper<User> owner) {
        this.owner = owner;
    }

    public Wrapper<Integer> getCount() {
        return count;
    }

    public void setCount(Wrapper<Integer> count) {
        this.count = count;
    }
}