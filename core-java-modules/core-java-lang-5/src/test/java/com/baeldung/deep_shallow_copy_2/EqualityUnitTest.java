package com.baeldung.deep_shallow_copy_2;

import org.junit.jupiter.api.Assertions;

import com.baeldung.deep_shallow_copy_2.Example;
import org.junit.jupiter.api.Test;

class EqualityUnitTest {

    @Test
    public void givenExampleInstance_whenCreateReferenceForTheSameInstance_thenReferencesAreSameAndEquals() {

        Example first = new Example();
        first.setValue("SomeValue");

        Example second = first;

        Assertions.assertSame(first, second);
        Assertions.assertEquals(first, second);
    }

    @Test
    public void givenExampleInstance_whenCreateNewInstanceWithSameContent_thenBothAreEqualButNotTheSame() {
        Example first = new Example();
        first.setValue("SomeValue");
        Example second = new Example();
        second.setValue("SomeValue");
        Assertions.assertNotSame(first, second);
        Assertions.assertEquals(first, second);
    }
}