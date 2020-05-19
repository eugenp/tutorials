package com.baeldung.javaxval.bigdecimal;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import com.baeldung.javaxval.LocaleAwareUnitTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class InvoiceUnitTest extends LocaleAwareUnitTest {

    private static Validator validator;

    @BeforeClass
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory()
            .getValidator();
    }

    @Test
    public void whenPriceIntegerDigitLessThanThreeWithDecimalValue_thenShouldGiveConstraintViolations() {
        Invoice invoice = new Invoice(new BigDecimal(10.21), "Book purchased");
        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage()).isEqualTo("numeric value out of bounds (<3 digits>.<2 digits> expected)"));
    }

    @Test
    public void whenPriceIntegerDigitLessThanThreeWithIntegerValue_thenShouldNotGiveConstraintViolations() {
        Invoice invoice = new Invoice(new BigDecimal(10), "Book purchased");
        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void whenPriceIntegerDigitGreaterThanThree_thenShouldGiveConstraintViolations() {
        Invoice invoice = new Invoice(new BigDecimal(1021.21), "Book purchased");
        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage()).isEqualTo("numeric value out of bounds (<3 digits>.<2 digits> expected)"));
    }

    @Test
    public void whenPriceIsZero_thenShouldGiveConstraintViolations() {
        Invoice invoice = new Invoice(new BigDecimal(000.00), "Book purchased");
        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        assertThat(violations.size()).isEqualTo(1);
        violations.forEach(action -> assertThat(action.getMessage()).isEqualTo("must be greater than 0.0"));
    }

    @Test
    public void whenPriceIsGreaterThanZero_thenShouldNotGiveConstraintViolations() {
        Invoice invoice = new Invoice(new BigDecimal(100.50), "Book purchased");
        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        assertThat(violations.size()).isEqualTo(0);
    }

}
