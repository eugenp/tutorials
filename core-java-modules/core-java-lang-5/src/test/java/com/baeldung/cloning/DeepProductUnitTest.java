package com.baeldung.cloning;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DeepProductUnitTest {

    private static final Logger logger = LoggerFactory.getLogger(DeepProductUnitTest.class);

    @Test
    void givenDeepCopy_whenOriginalDataModified_thenCloneAffected() {
        try {
            DeepProduct deepProduct = new DeepProduct("Test Deep Product");
            deepProduct.setCode("Product Code - Old");
            DeepProductCategory deepProductCategory = new DeepProductCategory("Test Deep Product Category");
            deepProductCategory.setCode("Product Category Code - Old");
            deepProduct.setDeepProductCategory(deepProductCategory);

            logger.debug("[Before cloning] Deep Product: {}", deepProduct);

            // now clone the product
            final DeepProduct clonedDeepProduct = (DeepProduct) deepProduct.clone();
            logger.debug("[After cloning] Deep Product: {}", deepProduct);
            logger.debug("[After cloning] Cloned Deep Product: {}", clonedDeepProduct);

            // now update the cloned product
            deepProduct.setCode("Product Code - New");
            deepProduct.getDeepProductCategory()
                .setCode("Product Category Code - New");

            logger.debug("[After updating cloned] Deep Product: {}", deepProduct);
            logger.debug("[After updating cloned] Cloned Deep Product: {}", clonedDeepProduct);

            assertEquals("Product Code - Old", clonedDeepProduct.getCode(), "Cloned product code should not get updated.");
            assertEquals("Product Category Code - Old", clonedDeepProduct.getDeepProductCategory()
                .getCode(), "Cloned product category code should not get updated.");
        } catch (CloneNotSupportedException ex) {
            Assertions.fail("Failed to clone.", ex);
        }
    }

    @Test
    void givenDeepCopy_whenClonedDataModified_thenOriginalAffected() {
        try {

            DeepProduct deepProduct = new DeepProduct("Test Deep Product");
            deepProduct.setCode("Product Code - Old");
            DeepProductCategory deepProductCategory = new DeepProductCategory("Test Deep Product Category");
            deepProductCategory.setCode("Product Category Code - Old");
            deepProduct.setDeepProductCategory(deepProductCategory);

            logger.debug("[Before cloning] Deep Product: {}", deepProduct);

            // now clone the product
            final DeepProduct clonedDeepProduct = (DeepProduct) deepProduct.clone();
            logger.debug("[After cloning] Deep Product: {}", deepProduct);
            logger.debug("[After cloning] Cloned Deep Product: {}", clonedDeepProduct);

            // now update the cloned product
            clonedDeepProduct.setCode("Product Code - New");
            clonedDeepProduct.getDeepProductCategory()
                .setCode("Product Category Code - New");

            logger.debug("[After updating cloned] Deep Product: {}", deepProduct);
            logger.debug("[After updating cloned] Cloned Deep Product: {}", clonedDeepProduct);

            assertEquals("Product Code - Old", deepProduct.getCode(), "Product code should not get updated.");
            assertEquals("Product Category Code - Old", deepProductCategory.getCode(), "Product category code should not get updated.");
        } catch (CloneNotSupportedException ex) {
            Assertions.fail("Failed to clone.", ex);
        }
    }
}