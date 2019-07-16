package com.baeldung.algorithms.linesintersection;

import java.awt.Point;
import java.util.Optional;

public class LinesIntersectionService {

    public Optional<Point> calculateIntersectionPoint(double m1, double b1, double m2, double b2) {

        if (m1 == m2) {
            return Optional.empty();
        }

        double x = (b2 - b1) / (m1 - m2);
        double y = m1 * x + b1;

        Point point = new Point();
        point.setLocation(x, y);
        return Optional.of(point);
    }
}
