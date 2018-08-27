package com.baeldung.linesintersection;

import java.awt.Point;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class LinesIntersectionServiceUnitTest {
    private LinesIntersectionService service = new LinesIntersectionService();

    @Test
    public void givenNotParallelLines_whenCalculatePoint_thenPresent() {

        float m1 = 0f;
        float b1 = 0f;
        float m2 = 1f;
        float b2 = -1f;

        Optional<Point> point = service.calculateIntersectionPoint(m1, b1, m2, b2);

        assertThat(point.isPresent(), equalTo(true));
        assertThat(point.get()
            .getX(), closeTo(1, 0.001));
        assertThat(point.get()
            .getY(), closeTo(0, 0.001));
    }

    @Test
    public void givenParallelLines_whenCalculatePoint_thenEmpty() {
        float m1 = 0.1999f;
        float m2 = 0.1999f;
        float b1 = 1.2f;
        float b2 = 1.3f;

        Optional<Point> point = service.calculateIntersectionPoint(m1, b1, m2, b2);

        assertThat(point.isPresent(), equalTo(false));
    }
}
