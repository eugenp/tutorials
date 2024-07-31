package com.baeldung.holder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SupplierServiceUnitTest {

    @Test
    public void givenValidZipCode_whenGetSupplierByZipCode_thenTrue() {
        SupplierService service = new SupplierService();
        Holder<Boolean> resultHolder = new Holder<>(false);
        String zipCode = "98682";

        service.getSupplierByZipCode(zipCode, resultHolder);

        assertTrue(resultHolder.value);
    }

    @Test
    public void givenInvalidZipCode_whenGetSupplierByZipCode_thenFalse() {
        SupplierService service = new SupplierService();
        Holder<Boolean> resultHolder = new Holder<>(true);
        String zipCode = "12345";

        service.getSupplierByZipCode(zipCode, resultHolder);

        assertFalse(resultHolder.value);
    }
}
