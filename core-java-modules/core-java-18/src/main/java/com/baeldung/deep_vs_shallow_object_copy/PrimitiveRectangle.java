package com.baeldung.deep_vs_shallow_object_copy;

public class PrimitiveRectangle {
    private int width;
    private int height;

    PrimitiveRectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    PrimitiveRectangle(PrimitiveRectangle original) {
        this.width = original.getWidth();
        this.height = original.getHeight();
    }

    @Override
    public String toString() {
        return "PrimitiveRectangle{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}