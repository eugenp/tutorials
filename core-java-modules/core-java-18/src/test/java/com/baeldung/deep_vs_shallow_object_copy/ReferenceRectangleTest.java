package com.baeldung.deep_vs_shallow_object_copy;

import org.junit.Test;

public class ReferenceRectangleTest {

    @Test
    public void givenReferenceRectangle_whenShallowCopy_thenOriginalAndCopyShouldShareReferences() {
        Dimension length = new Dimension(10);
        Dimension width = new Dimension(20);
        ReferenceRectangle original = new ReferenceRectangle(length, width);
        ReferenceRectangle copy = original.shallowCopy(original);

        length.setValue(30);
        width.setValue(40);

        System.out.println("Original reference rectangle: " + original);
        System.out.println("Copy of reference rectangle: " + copy);
    }

    @Test
    public void givenReferenceRectangle_whenDeepCopy_thenOriginalAndCopyShouldNotShareReferences() {
        Dimension length = new Dimension(10);
        Dimension width = new Dimension(20);
        ReferenceRectangle original = new ReferenceRectangle(length, width);
        ReferenceRectangle copy = original.deepCopy(original);

        length.setValue(30);
        width.setValue(40);

        System.out.println("Original reference rectangle: " + original);
        System.out.println("Copy of reference rectangle: " + copy);
    }

}
