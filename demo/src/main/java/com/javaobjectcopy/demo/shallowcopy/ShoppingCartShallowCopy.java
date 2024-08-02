package com.javaobjectcopy.demo.shallowcopy;

import com.javaobjectcopy.demo.deepcopy.Item1;

public class ShoppingCartShallowCopy implements Cloneable {
    private int numOfItems;
    private Item1 item;

    public ShoppingCartShallowCopy(int numOfItems, Item1 item) {
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

    public Item1 getItem() {
        return item;
    }

    public void setItem(Item1 item) {
        this.item = item;
    }
}