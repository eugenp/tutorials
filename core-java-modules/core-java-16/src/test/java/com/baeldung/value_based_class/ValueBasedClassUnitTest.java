package com.baeldung.value_based_class;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class ValueBasedClassUnitTest {
    @Test
    public void givenAutoboxedAndPrimitive_whenCompared_thenReturnEquals() {
        List<Integer> list = new ArrayList<>();
        list.add(1); // this is autoboxed
        Assert.assertEquals(list.get(0), Integer.valueOf(1));
    }

    @Test
    public void givenValueBasedPoint_whenCreated_thenReturnsObjects() {
        Point p1 = Point.valueOfPoint(1, 2, 3);
        Point p2 = Point.valueOfPoint(2, 3, 4);

        Assert.assertNotEquals(p1, p2);
    }

    @Test
    public void givenValueBasedPoint_whenCompared_thenReturnEquals() {
        Point p1 = Point.valueOfPoint(1, 2, 3);
        Point p2 = Point.valueOfPoint(1, 2, 3);

        Assert.assertEquals(p1, p2);
    }

    @Test
    public void givenValueBasedPoint_whenOrigin_thenReturnCachedInstance() {
        Point p1 = Point.valueOfPoint(0, 0, 0);
        Point p2 = Point.valueOfPoint(0, 0, 0);
        Point p3 = Point.valueOfPoint(1, 2, 3);

        // the following should not be assumed for value-based classes

        Assert.assertTrue(p1 == p2);
        Assert.assertFalse(p1 == p3);
    }
}
