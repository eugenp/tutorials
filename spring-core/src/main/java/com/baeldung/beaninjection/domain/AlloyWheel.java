package com.baeldung.beaninjection.domain;

public class AlloyWheel implements Wheel {

    private int width;

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int widthInMillimeters) {
        this.width = widthInMillimeters;
    }

}
