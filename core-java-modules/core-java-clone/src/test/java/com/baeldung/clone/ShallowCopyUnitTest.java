package com.baeldung.clone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

public class ShallowCopyUnitTest {

    @Test
    public void whenShadowCopy_thenObjectsAreEqualAndTheSame() {
        ValueObject original = new ValueObject("original");
        ValueObject shallow = original;

        assertEquals(original, shallow);
        assertSame(original, shallow);
        assertEquals("original", original.getValue());
        assertEquals("original", shallow.getValue());
    }

    @Test
    public void givenShallowCopy_whenShallowChanges_thenOriginalIsChanged() {
        ValueObject original = new ValueObject("original");
        ValueObject shallow = original;

        shallow.setValue("shallow");

        assertEquals(original, shallow);
        assertSame(original, shallow);
        assertEquals("shallow", original.getValue());
        assertEquals("shallow", shallow.getValue());
    }

}
