package com.baeldung.copy;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShallowCopyUnitTest {

    @Test
    public void whenUpdatingOriginal_thenShallowCopyUpdatesSuccessfully() {
        ShallowCopy original = new ShallowCopy(1, "b", new StringBuilder("c"));
        ShallowCopy copy = new ShallowCopy(original);

        original.getC()
            .append("c");

        assertEquals(original.getA(), copy.getA());
        assertEquals(original.getB(), copy.getB());
        assertEquals(original.getC(), original.getC());
    }
}
