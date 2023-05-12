package com.baeldung.deepshallowcopy.shallow;

public class Box implements Cloneable {
    public String[] smallerBoxes;

    @Override
    public Box clone() { // Note: that the clone() method in Java is protected. For testing purposes, we have overridden this method.
        try {
            return (Box) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
