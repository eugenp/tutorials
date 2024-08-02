package com.javaobjectcopy.demo.deepcopy;

public class ShoppingCartDeepCopy implements Cloneable {
    private int numOfItems;
    private Item1 item;

    public ShoppingCartDeepCopy(int numOfItems, Item1 item) {
        this.numOfItems = numOfItems;
        this.item = item;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        ShoppingCartDeepCopy copy = (ShoppingCartDeepCopy) super.clone();
        copy.item = (item != null) ? (Item1) item.clone() : null;
        return copy;
    }

    public ShoppingCartDeepCopy(ShoppingCartDeepCopy deepCopyUsingCopyConstructor) {
        this.numOfItems = deepCopyUsingCopyConstructor.numOfItems;
        this.item = new Item1(deepCopyUsingCopyConstructor.item);
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