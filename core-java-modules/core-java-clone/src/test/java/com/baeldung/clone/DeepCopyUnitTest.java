package com.baeldung.clone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Test;

public class DeepCopyUnitTest {

    @Test
    public void whenDeepCopy_thenObjectsAreEqualAndNotTheSame() {
        ValueObject original = new ValueObject("original");
        ValueObject shallow = new ValueObject(original.getValue());

        assertEquals(original, shallow);
        assertNotSame(original, shallow);
        assertEquals("original", original.getValue());
        assertEquals("original", shallow.getValue());
    }

    @Test
    public void givenDeepCopy_whenShadowChanges_thenOriginalIsNotChanged() {
        ValueObject original = new ValueObject("original");
        ValueObject shallow = new ValueObject(original.getValue());

        shallow.setValue("shallow");

        assertNotEquals(original, shallow);
        assertNotSame(original, shallow);
        assertEquals("original", original.getValue());
        assertEquals("shallow", shallow.getValue());
    }

}
