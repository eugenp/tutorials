package com.baeldung.arraycompare;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.Assert.assertTrue;

public class OrderCompareUnitTest {
    @Test
    public void givenArray1andArray2_whenNotSameContent_thenNotDeepEquals() {
        final Plane[][] planes1 = new Plane[][] {
            new Plane[] { new Plane("Plane 1", "A320"), new Plane("Plane 2", "B738") } };
        final Plane[][] planes2 = new Plane[][] {
            new Plane[] { new Plane("Plane 2", "B738"), new Plane("Plane 1", "A320") } };

        Comparator<Plane> planeComparator = (o1, o2) -> {
            if (o1.getName()
                .equals(o2.getName())) {
                return o2.getModel()
                    .compareTo(o1.getModel());
            }
            return o2.getName()
                .compareTo(o1.getName());
        };
        Arrays.sort(planes1[0], planeComparator);
        Arrays.sort(planes2[0], planeComparator);

        boolean result = Arrays.deepEquals(planes1, planes2);
        assertTrue("Result is false", result);
    }
}
