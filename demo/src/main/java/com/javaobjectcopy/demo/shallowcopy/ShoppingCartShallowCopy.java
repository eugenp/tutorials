package com.javaobjectcopy.demo.shallowcopy;

import com.javaobjectcopy.demo.deepcopy.Item;

public class ShoppingCartShallowCopy implements Cloneable {
    private int numOfItems;
    private Item item;

    public ShoppingCartShallowCopy(int numOfItems, Item item) {
        this.numOfItems = numOfItems;
        this.item = item;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    // standard setters and getters
    public int getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}