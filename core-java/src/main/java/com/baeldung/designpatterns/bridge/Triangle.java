package com.baeldung.designpatterns.bridge;

import static com.baeldung.designpatterns.util.LogerUtil.LOG;

public class Triangle extends Shape {

    public Triangle(Color color) {
        super(color);
    }

    @Override
    public void drawShape() {
        LOG.info("Triangle drawn. ");
        color.fillColor();
    }
}
