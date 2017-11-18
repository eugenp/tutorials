package com.baeldung.factorybean;

public class Tool {
    private int id;

    public Tool() {
    }

    public Tool(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
