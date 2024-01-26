package com.baeldung.deepvsshallowcopy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ProductDeepCopyUnitTest {

    @Test
    public void givenProduct_whenDeepCopy_thenNewReference() {
        Product watch = new Product("Watch", 12.95);
        Product deepCopy = watch.deepCopy();

        assertNotEquals(watch, deepCopy);
    }

    @Test
    public void whenUpdatingOriginalProduct_thenCopyShouldNotChange() {
        Product watch = new Product("Watch", 12.95);
        Product deepCopy = watch.deepCopy();

        watch.setName("Smart Watch");
        assertNotEquals(deepCopy.getName(), "Smart Watch");
    }
}
