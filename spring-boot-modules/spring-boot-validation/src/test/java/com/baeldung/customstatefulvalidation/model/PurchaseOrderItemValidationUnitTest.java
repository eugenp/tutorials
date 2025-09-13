package com.baeldung.customstatefulvalidation.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.baeldung.customstatefulvalidation.model.PurchaseOrderItemFactory.createValidPurchaseOrderItem;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PurchaseOrderItemValidationUnitTest {

    @Autowired
    private Validator validator;

    @Test
    void givenInvalidPurchaseOrderItem_thenInvalid() {
        Set<ConstraintViolation<PurchaseOrderItem>> violations = validator.validate(new PurchaseOrderItem());
        assertThat(violations).isNotEmpty();
    }

    @Test
    void givenInvalidProductId_thenProductIdInvalid() {
        PurchaseOrderItem item = createValidPurchaseOrderItem();
        item.setProductId("B-99-D");

        Set<ConstraintViolation<PurchaseOrderItem>> violations = validator.validate(item);

        assertThat(collectViolations(violations))
                .contains("productId: must match \"A-\\d{8}-\\d\"");
    }

    @Test
    void givenValidProductId_thenProductIdIsValid() {
        PurchaseOrderItem item = createValidPurchaseOrderItem();
        item.setProductId("A-12345678-6");

        Set<ConstraintViolation<PurchaseOrderItem>> violations = validator.validate(item);
        assertThat(violations).isEmpty();
    }

    @Test
    void givenInvalidClientUuid_thenInvalid() {
        PurchaseOrderItem item = createValidPurchaseOrderItem();
        item.setClientUuid("not a uuid");

        Set<ConstraintViolation<PurchaseOrderItem>> violations = validator.validate(item);

        assertThat(collectViolations(violations))
                .contains("clientUuid: must be a valid UUID");
    }

    @Test
    void givenProductIdWithInvalidCheckDigit_thenProductIdIsInvalid() {
        PurchaseOrderItem item = createValidPurchaseOrderItem();
        item.setProductId("A-12345678-1");

        Set<ConstraintViolation<PurchaseOrderItem>> violations = validator.validate(item);

        assertThat(collectViolations(violations))
                .containsExactly("productId: must have valid check digit");
    }

    @Test
    void givenNullProductId_thenProductIdIsInvalid() {
        PurchaseOrderItem item = createValidPurchaseOrderItem();
        item.setProductId(null);

        Set<ConstraintViolation<PurchaseOrderItem>> violations = validator.validate(item);
        assertThat(collectViolations(violations))
                .containsExactly("productId: must have valid check digit",
                        "productId: must not be null");
    }

    @Test
    void givenInvalidCombinationOfIndividualAndPack_thenInvalid() {
        PurchaseOrderItem item = createValidPurchaseOrderItem();
        item.setNumberOfIndividuals(10);
        item.setNumberOfPacks(20);
        item.setItemsPerPack(0);

        Set<ConstraintViolation<PurchaseOrderItem>> violations = validator.validate(item);
        assertThat(collectViolations(violations))
                .containsExactly("itemsPerPack: cannot be 0 when using packs",
                        "numberOfIndividuals: cannot be combined with number of packs",
                        "numberOfPacks: cannot be combined with number of individuals");
    }

    @Test
    void givenInvalidCombinationOfPacksAndItemsPerPack_thenInvalid() {
        PurchaseOrderItem item = createValidPurchaseOrderItem();
        item.setNumberOfIndividuals(0);
        item.setNumberOfPacks(20);
        item.setItemsPerPack(0);

        Set<ConstraintViolation<PurchaseOrderItem>> violations = validator.validate(item);
        assertThat(collectViolations(violations))
                .containsExactly("itemsPerPack: cannot be 0 when using packs");
    }

    @Test
    void givenNeitherPacksNorIndividuals_thenInvalid() {
        PurchaseOrderItem item = createValidPurchaseOrderItem();
        item.setNumberOfIndividuals(0);
        item.setNumberOfPacks(0);
        item.setItemsPerPack(0);

        Set<ConstraintViolation<PurchaseOrderItem>> violations = validator.validate(item);
        assertThat(collectViolations(violations))
                .containsExactly("numberOfIndividuals: must choose a quantity when no packs",
                        "numberOfPacks: must choose a quantity when no individuals");
    }

    @Test
    void givenUnexpectedChannel_thenInvalid() {
        PurchaseOrderItem item = createValidPurchaseOrderItem();
        item.setTenantChannel("ebay");

        Set<ConstraintViolation<PurchaseOrderItem>> violations = validator.validate(item);
        assertThat(collectViolations(violations))
                .containsExactly("tenantChannel: must be available tenant channel");
    }

    @Test
    void givenInvalidWarehouseRoute_thenInvalid() {
        PurchaseOrderItem item = createValidPurchaseOrderItem();
        item.setSourceWarehouse("Auberry");
        item.setDestinationCountry("IT");

        Set<ConstraintViolation<PurchaseOrderItem>> violations = validator.validate(item);
        assertThat(collectViolations(violations))
                .containsExactly(": chosen warehouse route must be active");
    }


    private static List<String> collectViolations(Set<ConstraintViolation<PurchaseOrderItem>> violations) {
        return violations.stream()
            .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
            .sorted()
            .collect(Collectors.toList());
    }
}