package com.baeldung.architecture.hexa.outside.client;

import java.util.Map;

import com.baeldung.architecture.hexa.inside.ShapeServicePort;

public class ShapeServiceAdapter {

    private ShapeServicePort shapeServicePort;

    public ShapeServiceAdapter(ShapeServicePort shapeService) {
        this.shapeServicePort = shapeService;
    }

    public void dispalyRedShapes(Map<String, Object> model) {
        model.put("shapes", shapeServicePort.findShapesByColor("red"));
    }

}
