package com.baeldung;

import com.baeldung.comparingarrays.Plane;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeepEqualsCompareUnitTest {

    @Test
    public void givenArray1andArray2_whenSameContent_thenDeepEquals() {
        final Plane[][] planes1 = new Plane[][] { new Plane[] { new Plane("Plane 1", "A320") },
            new Plane[] { new Plane("Plane 2", "B738") } };
        final Plane[][] planes2 = new Plane[][] { new Plane[] { new Plane("Plane 1", "A320") },
            new Plane[] { new Plane("Plane 2", "B738") } };

        boolean result = Arrays.deepEquals(planes1, planes2);
        assertTrue("Result is not true", result);
    }

    @Test
    public void givenArray1andArray2_whenNotSameContent_thenNotDeepEquals() {
        final Plane[][] planes1 = new Plane[][] { new Plane[] { new Plane("Plane 1", "A320") },
            new Plane[] { new Plane("Plane 2", "B738") } };
        final Plane[][] planes2 = new Plane[][] { new Plane[] { new Plane("Plane 2", "B738") },
            new Plane[] { new Plane("Plane 1", "A320") } };

        boolean result = Arrays.deepEquals(planes1, planes2);
        assertFalse("Result is true", result);
    }
}
