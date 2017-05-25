package com.baeldung.disetter.model;

public class Engine {
    private String type;
    private int displacement;

    public void setType(String type) {
        this.type = type;
    }

    public void setDisplacement(int displacement) {
        this.displacement = displacement;
    }

    @Override
    public String toString() {
        return "Engine of type " + this.type + " and displacement " + this.displacement + " cc.";
    }
}
