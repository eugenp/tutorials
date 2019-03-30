package com.baeldung.architecture.hexa.inside;

import java.util.List;

public interface ShapeRepositoryPort {

    List<Shape> loadShapes(String color);

}
