package com.baeldung.deep_vs_shallow_object_copy;

public class ReferenceRectangle {

    Dimension length;
    Dimension width;

    public ReferenceRectangle(Dimension length, Dimension width) {
        this.length = length;
        this.width = width;
    }

    public ReferenceRectangle shallowCopy(ReferenceRectangle original) {
        return new ReferenceRectangle(
                original.getLength(),
                original.getWidth()
        );
    }

    public ReferenceRectangle deepCopy(ReferenceRectangle original) {
        return new ReferenceRectangle(
                new Dimension(getLength().getValue()),
                new Dimension(getWidth().getValue())
        );
    }

    @Override
    public String toString() {
        return "ReferenceRectangle{" +
                "length=" + length +
                ", width=" + width +
                '}';
    }

    public Dimension getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = new Dimension(length);
    }

    public Dimension getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = new Dimension(width);
    }
}