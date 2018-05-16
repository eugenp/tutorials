package com.baeldung.list.listoflist;

public class Pen implements Stationery {

    public String name;

    public Pen(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}