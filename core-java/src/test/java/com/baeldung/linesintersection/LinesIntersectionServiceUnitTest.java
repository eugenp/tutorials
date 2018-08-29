package com.baeldung.linesintersection;

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

        float m1 = 0;
        float b1 = 0;
        float m2 = 1;
        float b2 = -1;

        Optional<Point> point = service.calculateIntersectionPoint(m1, b1, m2, b2);

        assertTrue(point.isPresent());
        assertEquals(point.get().x, 1);
        assertEquals(point.get().y, 0);
    }

    @Test
    public void givenParallelLines_whenCalculatePoint_thenEmpty() {
        float m1 = 1;
        float b1 = 0;
        float m2 = 1;
        float b2 = -1;

        Optional<Point> point = service.calculateIntersectionPoint(m1, b1, m2, b2);

        assertFalse(point.isPresent());
    }
}
