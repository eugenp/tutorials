package com.baeldung.javaxval.bigdecimal;

import com.baeldung.javaxval.LocaleAwareUnitTest;
import org.junit.BeforeClass;
import org.junit.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class InvoiceUnitTest extends LocaleAwareUnitTest {

    private static Validator validator;

    @BeforeClass
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory()
            .getValidator();
    }

    @Test
    public void whenLessThanThreeIntegerDigits_thenShouldNotGiveConstraintViolations() {
        Invoice invoice = new Invoice(new BigDecimal("10.21"), "Book purchased");
        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        assertThat(violations).isEmpty();
    }

    @Test
    public void whenThreeIntegerDigits_thenShouldNotGiveConstraintViolations() {
        Invoice invoice = new Invoice(new BigDecimal("102.21"), "Book purchased");
        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        assertThat(violations).isEmpty();
    }

    @Test
    public void whenMoreThanThreeIntegerDigits_thenShouldGiveConstraintViolations() {
        Invoice invoice = new Invoice(new BigDecimal("1021.21"), "Book purchased");
        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        assertThat(violations).hasSize(1);
        assertThat(violations)
            .extracting("message")
            .containsOnly("numeric value out of bounds (<3 digits>.<2 digits> expected)");
    }

    @Test
    public void whenLessThanTwoFractionDigits_thenShouldNotGiveConstraintViolations() {
        Invoice invoice = new Invoice(new BigDecimal("99.9"), "Book purchased");
        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        assertThat(violations).isEmpty();
    }

    @Test
    public void whenTwoFractionDigits_thenShouldNotGiveConstraintViolations() {
        Invoice invoice = new Invoice(new BigDecimal("99.99"), "Book purchased");
        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        assertThat(violations).isEmpty();
    }

    @Test
    public void whenMoreThanTwoFractionDigits_thenShouldGiveConstraintViolations() {
        Invoice invoice = new Invoice(new BigDecimal("99.999"), "Book purchased");
        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        assertThat(violations).hasSize(1);
        assertThat(violations)
            .extracting("message")
            .containsOnly("numeric value out of bounds (<3 digits>.<2 digits> expected)");
    }

    @Test
    public void whenPriceIsZero_thenShouldGiveConstraintViolations() {
        Invoice invoice = new Invoice(new BigDecimal("0.00"), "Book purchased");
        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        assertThat(violations).hasSize(1);
        assertThat(violations)
            .extracting("message")
            .containsOnly("must be greater than 0.0");
    }

    @Test
    public void whenPriceIsGreaterThanZero_thenShouldNotGiveConstraintViolations() {
        Invoice invoice = new Invoice(new BigDecimal("100.50"), "Book purchased");
        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        assertThat(violations).isEmpty();
    }
}
