package com.baeldung.prototype;

public class Tree implements TreeCloneable {

    private double mass;
    private double height;
    private Position position;

    public Tree(double mass, double height) {
        this.mass = mass;
        this.height = height;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public double getMass() {
        return mass;
    }

    public double getHeight() {
        return height;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Tree [mass=" + mass + ", height=" + height + ", position=" + position + "]";
    }

    @Override
    public TreeCloneable createA_Clone() {
        Tree tree = null;
        try {
            tree = (Tree) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return tree;
    }

}
