package com.baeldung.javaxval.notnull;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class NotNullMethodParameterUnitTest {

    private NotNullMethodParameter demo = new NotNullMethodParameter();

    @Test
    public void givenNull_whenInvokedwithNoValidator_thenNullPointerException() {
        assertThrows(NullPointerException.class, () -> demo.doesNotValidateNotNull(null));
    }

    @Test
    public void givenNull_whenInvokedWithValidator_thenIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> demo.validateNotNull(null));
    }

}