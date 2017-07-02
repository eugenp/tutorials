package com.baeldung.list.listoflist;

class Pencil implements Stationery{
    
    private String name;
    
    public Pencil(String name) {
        this.name = name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}