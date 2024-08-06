package com.javaobjectcopy.demo.deepcopy;

public class Item implements Cloneable {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Item(Item itemUsingCopyConstructor) {
        this.name = itemUsingCopyConstructor.name;
    }

    // standard setters and getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}