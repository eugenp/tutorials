package com.baeldung.deepvsshallowcopy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ProductDeepCopyUnitTest {

    @Test
    public void whenUpdatingOriginalProduct_thenCopyShouldNotChange() {
        Category electronics = new Category("Electronics", "Electronic Items");
        Product watch = new Product("Watch", 12.95, electronics);
        Product deepCopy = watch.deepCopy();

        electronics.setDescription("Wearable Electronics");
        assertNotEquals(deepCopy.getCategory()
            .getDescription(), watch.getCategory()
            .getDescription());
    }

    @Test
    public void whenUpdatingOriginalProduct_thenCloneCopyShouldNotChange() {
        Category electronics = new Category("Electronics", "Electronic Items");
        Product watch = new Product("Watch", 12.95, electronics);
        Product deepCopy = watch.clone();

        electronics.setDescription("Wearable Electronics");
        assertNotEquals(deepCopy.getCategory()
            .getDescription(), watch.getCategory()
            .getDescription());
    }
}
