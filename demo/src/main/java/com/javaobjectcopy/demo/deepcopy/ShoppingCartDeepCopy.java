package com.javaobjectcopy.demo.deepcopy;

public class ShoppingCartDeepCopy implements Cloneable {
    private int numOfItems;
    private Item2 item;

    public ShoppingCartDeepCopy(int numOfItems, Item2 item) {
        this.numOfItems = numOfItems;
        this.item = item;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        ShoppingCartDeepCopy copy = (ShoppingCartDeepCopy) super.clone();
        copy.item = (item != null) ? (Item2) item.clone() : null;
        return copy;
    }

    public ShoppingCartDeepCopy(ShoppingCartDeepCopy deepCopyUsingCopyConstructor) {
        this.numOfItems = deepCopyUsingCopyConstructor.numOfItems;
        this.item = new Item2(deepCopyUsingCopyConstructor.item);
    }

    // standard setters and getters

    public int getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    public Item2 getItem() {
        return item;
    }

    public void setItem(Item2 item) {
        this.item = item;
    }
}

class Item2 implements Cloneable {
    private String name;

    public Item2(String name) {
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Item2(Item2 item2UsingCopyConstructor) {
        this.name = item2UsingCopyConstructor.name;
    }

    // standard setters and getters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}