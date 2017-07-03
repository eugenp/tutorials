package com.baeldung.list.listoflist;

class Rubber implements Stationery {

    private String name;

    public Rubber(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}