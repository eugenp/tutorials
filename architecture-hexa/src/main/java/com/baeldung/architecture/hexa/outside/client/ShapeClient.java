package com.baeldung.architecture.hexa.outside.client;

import java.util.Map;

import com.baeldung.architecture.hexa.inside.ShapeService;

public class ShapeClient {

    private ShapeService shapeService;

    public ShapeClient(ShapeService shapeService) {
        this.shapeService = shapeService;
    }

    public void dispalyRedShapes(Map<String, Object> model) {
        model.put("shapes", shapeService.findShapesByColor("red"));
    }

}
