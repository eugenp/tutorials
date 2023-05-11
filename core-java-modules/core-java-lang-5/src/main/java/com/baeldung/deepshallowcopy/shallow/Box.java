package com.baeldung.deepshallowcopy.shallow;

public class Box implements Cloneable {
    public String[] smallerBoxes;

    @Override
    public Box clone() {
        try {
            // This automatically creates a shallow copy
            return (Box) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Can't happen
        }
    }
}
