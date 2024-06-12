package com.baeldung.deep_vs_shallow_object_copy;

import org.junit.Test;
import static org.junit.Assert.*;

public class ReferenceRectangleTest {

    @Test
    public void givenReferenceRectangle_whenShallowCopy_thenOriginalAndCopyShouldShareReferences() {
        // Given
        Dimension length = new Dimension(10);
        Dimension width = new Dimension(20);
        ReferenceRectangle original = new ReferenceRectangle(length, width);

        // When
        ReferenceRectangle copy = original.shallowCopy(original);

        // Then
        length.setValue(30);
        width.setValue(40);
        assertEquals(30, copy.getLength().getValue());
        assertEquals(40, copy.getWidth().getValue());
    }

    @Test
    public void givenReferenceRectangle_whenDeepCopy_thenOriginalAndCopyShouldNotShareReferences() {
        // Given
        Dimension length = new Dimension(10);
        Dimension width = new Dimension(20);
        ReferenceRectangle original = new ReferenceRectangle(length, width);

        // When
        ReferenceRectangle copy = original.deepCopy(original);

        // Then
        length.setValue(30);
        width.setValue(40);
        assertEquals(10, copy.getLength().getValue());
        assertEquals(20, copy.getWidth().getValue());
    }
}