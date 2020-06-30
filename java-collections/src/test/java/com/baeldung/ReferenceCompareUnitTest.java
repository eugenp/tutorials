package com.baeldung;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

public class ReferenceCompareUnitTest {

    @Test
    public void givenArray1andArray2_whenEquals_thenEqual() {
        final String[] planes1 = new String[] { "A320", "B738", "A321", "A319", "B77W", "B737", "A333", "A332" };
        final String[] planes2 = planes1;

        assertSame("Objects are not equal!", planes1, planes2);

        planes2[0] = "747";

        assertSame("Objects are not same!", planes1, planes2);
        assertEquals("Objects are not equal!", "747", planes2[0]);
        assertEquals("Objects are not equal!", "747", planes1[0]);
    }

    @Test
    public void givenArray1andArray2_whenDifferentValues_thenNotEqual() {
        final String[] planes1 = new String[] { "A320", "B738", "A321", "A319", "B77W", "B737", "A333", "A332" };
        final String[] planes2 = new String[] { "A320", "B738", "A321", "A319", "B77W", "B737", "A333", "A332" };

        assertNotSame("Objects are the same!", planes1, planes2);
        assertNotEquals("Objects are equal!", planes1, planes2);
    }
}
