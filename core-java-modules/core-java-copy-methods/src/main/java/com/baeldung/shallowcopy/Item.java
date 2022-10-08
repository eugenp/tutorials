package com.baeldung.shallowcopy;

public class Item implements Cloneable {

    int id;
    String itemName;
    User user;

    public Item(int id, String itemName, User user) {
        this.id = id;
        this.itemName = itemName;
        this.user = user;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}