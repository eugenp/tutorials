package com.baeldung.architecture.hexa.inside;

import java.util.List;

public interface ShapeServicePort {

    List<Shape> findShapesByColor(String color);

}
