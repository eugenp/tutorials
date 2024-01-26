package com.baeldung.deepvsshallowcopy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductShallowCopyPersonUnitTest {

    @Test
    public void givenProduct_whenShallowCopy_thenSharedReference() {
        Product watch = new Product("Watch", 12.95);
        Product shallowCopy = watch;

        assertEquals(watch, shallowCopy);
    }

    @Test
    public void whenUpdatingOriginalProduct_thenCopyShouldChange() {
        Product watch = new Product("Watch", 12.95);
        Product shallowCopy = watch;

        watch.setName("Smart Watch");
        assertEquals(shallowCopy.getName(), "Smart Watch");
    }
}
