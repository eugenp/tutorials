package com.javaobjectcopy.demo.deepcopy;

public class Item1 implements Cloneable {
    private String name;

    public Item1(String name) {
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Item1(Item1 item1UsingCopyConstructor) {
        this.name = item1UsingCopyConstructor.name;
    }

    // standard setters and getters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}