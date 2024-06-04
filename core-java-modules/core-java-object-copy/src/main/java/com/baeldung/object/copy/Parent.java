package com.baeldung.object.copy;

public class Parent {

    public String name;

    public Child child1;
    public Child child2;

    public Parent() {
    }

    public Parent(String name) {
        this.name = name;
    }

    /**
     * Copy constructor
     * 
     * @param other The other Parent to copy.
     * @param deep Pass true if a deep copy is required, false otherwise.
     */
    public Parent(Parent other, boolean deep) {
        this.name = other.name;

        if (deep) {
            this.child1 = new Child(other.child1.name);
            this.child2 = new Child(other.child2.name);
        } else {
            this.child1 = other.child1;
            this.child2 = other.child2;
        }
    }

}
