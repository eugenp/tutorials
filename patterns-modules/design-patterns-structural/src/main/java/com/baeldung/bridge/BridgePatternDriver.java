package com.baeldung.bridge;

import static com.baeldung.util.LoggerUtil.LOG;

public class BridgePatternDriver {

    public static void main(String[] args) {
        //a square with red color
        Shape square = new Square(new Red());
        LOG.info(square.draw());
        
        //a triangle with blue color
        Shape triangle = new Triangle(new Blue());
        LOG.info(triangle.draw());
    }
}
