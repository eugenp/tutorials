package com.baeldung.algorithms.linesintersection;

import java.awt.Point;
import java.util.Optional;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class LinesIntersectionServiceUnitTest {
    private LinesIntersectionService service = new LinesIntersectionService();

    @Test
    public void givenNotParallelLines_whenCalculatePoint_thenPresent() {

        double m1 = 0;
        double b1 = 0;
        double m2 = 1;
        double b2 = -1;

        Optional<Point> point = service.calculateIntersectionPoint(m1, b1, m2, b2);

        assertTrue(point.isPresent());
        assertEquals(point.get().getX(), 1, 0.001);
        assertEquals(point.get().getY(), 0, 0.001);
    }

    @Test
    public void givenParallelLines_whenCalculatePoint_thenEmpty() {
        double m1 = 1;
        double b1 = 0;
        double m2 = 1;
        double b2 = -1;

        Optional<Point> point = service.calculateIntersectionPoint(m1, b1, m2, b2);

        assertFalse(point.isPresent());
    }
}
