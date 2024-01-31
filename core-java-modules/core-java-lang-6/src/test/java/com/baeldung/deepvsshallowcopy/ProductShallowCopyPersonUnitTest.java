package com.baeldung.deepvsshallowcopy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ProductShallowCopyPersonUnitTest {

    @Test
    public void givenProduct_whenShallowCopy_thenObjectsShouldNotBeSame() {
        Category electronics = new Category("Electronics", "Electronic Items");
        Product watch = new Product("Watch", 12.95, electronics);
        Product shallowCopy = new Product(watch.getName(), watch.getPrice(), watch.getCategory());

        assertNotEquals(watch, shallowCopy);
    }

    @Test
    public void whenUpdatingOriginalProduct_thenCopyShouldChange() {
        Category electronics = new Category("Electronics", "Electronic Items");
        Product watch = new Product("Watch", 12.95, electronics);
        Product shallowCopy = new Product(watch.getName(), watch.getPrice(), watch.getCategory());

        electronics.setDescription("Wearable Electronics");
        assertEquals(shallowCopy.getCategory()
            .getDescription(), watch.getCategory()
            .getDescription());
    }
}
