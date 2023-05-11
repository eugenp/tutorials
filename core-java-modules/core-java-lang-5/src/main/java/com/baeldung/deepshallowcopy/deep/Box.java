package com.baeldung.deepshallowcopy.deep;

public class Box implements Cloneable {
    public String[] smallerBoxes;

    public Box clone() throws CloneNotSupportedException {
        Box clonedBox = (Box) super.clone();
        clonedBox.smallerBoxes = smallerBoxes.clone();
        return clonedBox;
    }
}
