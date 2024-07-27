package com.javaobjectcopy.demo.shallowcopy;

public class ShoppingCartShallowCopy implements Cloneable {
    private int numOfItems;
    private Item1 item;

    public ShoppingCartShallowCopy(int numOfItems, Item1 item) {
        this.numOfItems = numOfItems;
        this.item = item;
    }

    public Object tryClone() throws CloneNotSupportedException {
        return this.clone();
    }

    // standard setters and getters

    public int getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    public Item1 getItem() {
        return item;
    }

    public void setItem(Item1 item) {
        this.item = item;
    }
}

class Item1 {
    private String name;

    public Item1(String name) {
        this.name = name;
    }

    // standard setters and getters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}