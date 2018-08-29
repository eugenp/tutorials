package com.baeldung.linesintersection;

import java.awt.Point;
import java.util.Optional;

public class LinesIntersectionService {

    public Optional<Point> calculateIntersectionPoint(float m1, float b1, float m2, float b2) {

        if (m1 == m2) {
            return Optional.empty();
        }
        
        float x = (b2 - b1) / (m1 - m2);
        float y = m1 * x + b1;

        Point point = new Point(Math.round(x), Math.round(y));

        return Optional.of(point);
    }
}
