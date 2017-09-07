package com.baeldung.designpatterns.bridge;

import static com.baeldung.designpatterns.util.LogerUtil.LOG;

public class Square extends Shape {

    public Square(Color color) {
        super(color);
    }

    @Override
    public void drawShape() {
        LOG.info("Square drawn. ");
        color.fillColor();
    }
}
