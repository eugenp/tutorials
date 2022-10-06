package com.baeldung.deepcopy;

public class Item implements Cloneable {

    int id;
    String itemName;
    User user;

    public Item(int id, String itemName, User owner) {
        this.id = id;
        this.itemName = itemName;
        this.user = owner;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Item item = (Item) super.clone();
        item.user = (User) this.user.clone();
        return item;
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