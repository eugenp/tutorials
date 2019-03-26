package com.baeldung.architecture.hexa.inside;

import java.util.List;

public interface ShapeRepository {

    List<Shape> loadShapes(String color);

}
