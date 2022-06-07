package com.baeldung.copy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class DeepCopyUnitTest {

    @Test
    public void whenUpdatingOriginal_thenDeepCopyDoesNotUpdateSuccessfully() {
        DeepCopy original = new DeepCopy(1, "b", new StringBuilder("c"));
        DeepCopy copy = original.clone();

        original.getC()
            .append("c");

        assertEquals(original.getA(), copy.getA());
        assertEquals(original.getB(), copy.getB());
        assertNotEquals(original.getC(), copy.getC());
    }
}
