package org.baeldung.javabeanconstraints.bigdecimal;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.BeforeClass;
import org.junit.Test;

public class InvoiceUnitTest {
	
	private static Validator validator;

    @BeforeClass
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
    
    @Test
    public void whenPriceIntegerDigitLessThanThree_thenShouldGiveConstraintViolations() {
    	Invoice invoice = new Invoice(new BigDecimal(10.20), "Book purchased");
        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        assertThat(violations.size()).isEqualTo(1);
    }
    
    @Test
    public void whenPriceIntegerDigitGreaterThanThree_thenShouldGiveConstraintViolations() {
    	Invoice invoice = new Invoice(new BigDecimal(1021.20), "Book purchased");
        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        assertThat(violations.size()).isEqualTo(1);
    }
    
    @Test
    public void whenPriceIsZero_thenShouldGiveConstraintViolations() {
    	Invoice invoice = new Invoice(new BigDecimal(000.00), "Book purchased");
        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        assertThat(violations.size()).isEqualTo(1);
    }
    
    @Test
    public void whenPriceIsGreaterThanZero_thenShouldNotGiveConstraintViolations() {
    	Invoice invoice = new Invoice(new BigDecimal(100.50), "Book purchased");
        Set<ConstraintViolation<Invoice>> violations = validator.validate(invoice);
        assertThat(violations.size()).isEqualTo(0);
    }

}
