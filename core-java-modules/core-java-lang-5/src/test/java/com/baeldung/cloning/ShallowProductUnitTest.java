package com.baeldung.cloning;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ShallowProductUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(ShallowProductUnitTest.class);

    @Test
    void givenShallowCopy_whenOriginalDataModified_thenCloneAffected() {
        try {
            ShallowProduct shallowProduct = new ShallowProduct("Test Shallow Product");
            shallowProduct.setCode("Product Code - Old");
            ShallowProductCategory shallowProductCategory = new ShallowProductCategory("Test Shallow Product Category");
            shallowProductCategory.setCode("Product Category Code - Old");
            shallowProduct.setShallowProductCategory(shallowProductCategory);

            logger.debug("[Before cloning] Shallow Product: {}", shallowProduct);

            // now clone the product
            final ShallowProduct clonedShallowProduct = (ShallowProduct) shallowProduct.clone();
            logger.debug("[After cloning] Shallow Product: {}", shallowProduct);
            logger.debug("[After cloning] Cloned Shallow Product: {}", clonedShallowProduct);

            // now update the cloned product
            shallowProduct.setCode("Product Code - New");
            shallowProduct.getShallowProductCategory()
                .setCode("Product Category Code - New");

            logger.debug("[After updating cloned] Shallow Product: {}", shallowProduct);
            logger.debug("[After updating cloned] Cloned Shallow Product: {}", clonedShallowProduct);

            assertEquals("Product Code - Old", clonedShallowProduct.getCode(), "Cloned product code should not get updated.");
            assertEquals("Product Category Code - New", clonedShallowProduct.getShallowProductCategory()
                .getCode(), "Cloned product category code should get updated.");
        } catch (CloneNotSupportedException ex) {
            Assertions.fail("Failed to clone.", ex);
        }
    }

    @Test
    void givenShallowCopy_whenClonedDataModified_thenOriginalAffected() {
        try {
            ShallowProduct shallowProduct = new ShallowProduct("Test Shallow Product");
            shallowProduct.setCode("Product Code - Old");
            ShallowProductCategory shallowProductCategory = new ShallowProductCategory("Test Shallow Product Category");
            shallowProductCategory.setCode("Product Category Code - Old");
            shallowProduct.setShallowProductCategory(shallowProductCategory);

            logger.debug("[Before cloning] Shallow Product: {}", shallowProduct);

            // now clone the product
            final ShallowProduct clonedShallowProduct = (ShallowProduct) shallowProduct.clone();
            logger.debug("[After cloning] Shallow Product: {}", shallowProduct);
            logger.debug("[After cloning] Cloned Shallow Product: {}", clonedShallowProduct);

            // now update the cloned product
            clonedShallowProduct.setCode("Product Code - New");
            clonedShallowProduct.getShallowProductCategory()
                .setCode("Product Category Code - New");

            logger.debug("[After updating cloned] Shallow Product: {}", shallowProduct);
            logger.debug("[After updating cloned] Cloned Shallow Product: {}", clonedShallowProduct);

            assertEquals("Product Code - Old", shallowProduct.getCode(), "Product code should not get updated.");
            assertEquals("Product Category Code - New", shallowProductCategory.getCode(), "Product category code should get updated.");
        } catch (CloneNotSupportedException ex) {
            Assertions.fail("Failed to clone.", ex);
        }
    }
}