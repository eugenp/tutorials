package com.baeldung.architecture.hexa.outside.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.baeldung.architecture.hexa.inside.Shape;
import com.baeldung.architecture.hexa.inside.ShapeRepositoryPort;

public class DummyShapeRepositoryAdapter implements ShapeRepositoryPort {

    private List<Shape> shapesDb = new ArrayList<>();

    public DummyShapeRepositoryAdapter() {
        this.shapesDb.add(new Shape("red"));
        this.shapesDb.add(new Shape("blue"));
        this.shapesDb.add(new Shape("red"));
    }

    @Override
    public List<Shape> loadShapes(String color) {
        return shapesDb.stream() 
            .filter(s -> s.getColor().equals(color)) //
            .collect(Collectors.toList());
    }

}
