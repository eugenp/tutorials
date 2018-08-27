package com.baeldung.linesintersection;

import java.awt.Point;
import java.util.Optional;

public class LinesIntersectionService {

    public Optional<Point> calculateIntersectionPoint(float m1, float b1, float m2, float b2) {

        if (m1 - m2 == 0) {
            return Optional.empty();
        }

        float x = (b2 - b1) / (m1 - m2);
        float y = m1 * x + b1;
        System.out.println(x + " " + y);
        System.out.println((int) x + " " + (int) y);

        Point point = new Point(Math.round(x), Math.round(y));
        point.setLocation(x, y);
        System.out.println(point.getX() + " " + point.getY());
        return Optional.of(point);
    }
}
