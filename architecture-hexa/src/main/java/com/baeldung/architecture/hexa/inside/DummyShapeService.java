package com.baeldung.architecture.hexa.inside;

import java.util.List;

public class DummyShapeService implements ShapeServicePort {

    private ShapeRepositoryPort ShapeRepositoryPort;

    public DummyShapeService(ShapeRepositoryPort shapeRepository) {
        ShapeRepositoryPort = shapeRepository;
    }

    @Override
    public List<Shape> findShapesByColor(String color) {
        return ShapeRepositoryPort.loadShapes(color);
    }

}
