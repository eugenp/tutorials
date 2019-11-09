package com.baeldung.prototype;

public class PineTree extends Tree {

    private String type;

    public PineTree(double mass, double height) {
        super(mass, height);
        this.type = "Pine";
    }

    public String getType() {
        return type;
    }

    @Override
    public Tree copy() {
        return new PineTree(this.getMass(), this.getHeight());
    }

}
