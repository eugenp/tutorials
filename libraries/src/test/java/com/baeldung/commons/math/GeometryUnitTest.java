package com.baeldung.commons.math;

import org.apache.commons.math3.geometry.euclidean.twod.Line;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.junit.Assert;
import org.junit.Test;

public class GeometryUnitTest {

    @Test
    public void whenLineIntersection_thenCorrect() {
        Line l1 = new Line(new Vector2D(0, 0), new Vector2D(1, 1), 0);
        Line l2 = new Line(new Vector2D(0, 1), new Vector2D(1, 1.5), 0);

        Vector2D intersection = l1.intersection(l2);

        Assert.assertEquals(2, intersection.getX(), 1e-7);
        Assert.assertEquals(2, intersection.getY(), 1e-7);
    }

}
