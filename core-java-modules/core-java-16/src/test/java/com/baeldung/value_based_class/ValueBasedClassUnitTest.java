package com.baeldung.value_based_class;


import org.junit.Assert;
import org.junit.Test;

public class ValueBasedClassUnitTest {
    @Test
    public void givenAutoboxedAndPrimitive_whenCompared_thenReturnEquals() {
        int primitive_a = 125;
        Integer obj_a = 125; // this is autoboxed
        Assert.assertSame(primitive_a, obj_a);
    }

    @Test
    public void givenValueBasedPoint_whenCreated_thenReturnsObjects() {
        Point p1 = Point.valueOfPoint(1,2,3);
        Point p2 = Point.valueOfPoint(2,3,4);

        Assert.assertNotEquals(p1, p2);
    }

@Test
public void givenValueBasedPoint_whenCompared_thenReturnEquals() {
    Point p1 = Point.valueOfPoint(1,2,3);
    Point p2 = Point.valueOfPoint(1,2,3);

    Assert.assertEquals(p1, p2);
}
}
