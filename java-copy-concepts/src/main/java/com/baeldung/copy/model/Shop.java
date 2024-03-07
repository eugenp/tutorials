package com.baeldung.copy.model;

public class Shop implements Cloneable {
    private String name;
    private Item item;

    public Shop(Shop that) {
        this(that.name, new Item(that.item));
    }

    public Shop(String name, Item item) {
        this.name = name;
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    //This clone implementation is used for Deep Clone
/*    @Override
    public Object clone() throws CloneNotSupportedException {
        Shop shop = (Shop) super.clone();
        shop.item = (Item) item.clone();
        return shop;
    }

 */

    //This clone implementation is used for Shallow Clone
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
