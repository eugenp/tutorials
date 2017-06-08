package com.baeldung.diconst.model;

public class Engine {
    private String type;
    private int displacement;
    
    public Engine(String type, int displacement) {
        this.type = type;
        this.displacement = displacement;
    }
    
    @Override
    public String toString() {
        return "Engine of type " + this.type + " and displacement " + this.displacement + " cc.";
    }
}
