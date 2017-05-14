package com.baeldung.commons.math;

import org.apache.commons.math3.geometry.euclidean.twod.Line;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class GeometryExample {

    public void run() {
        Line l1 = new Line(new Vector2D(0, 0), new Vector2D(1, 1), 0);
        Line l2 = new Line(new Vector2D(0, 1), new Vector2D(1, 1.5), 0);

        Vector2D intersection = l1.intersection(l2);

        System.out.println(intersection);
    }

}
