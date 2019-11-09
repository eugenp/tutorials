package com.baeldung.prototype;

public class PlasticTree extends Tree {

    private String name;

    public PlasticTree(double mass, double height) {
        super(mass, height);
        this.name = "PlasticTree";
    }
    
    public String getName() {
        return name;
    }

    @Override
    public Tree copy() {
        return new PlasticTree(this.getMass(), this.getHeight());
    }

}
