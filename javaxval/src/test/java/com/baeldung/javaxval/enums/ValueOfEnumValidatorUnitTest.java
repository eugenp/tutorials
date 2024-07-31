package com.baeldung.javaxval.enums;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.javaxval.enums.demo.Customer;
import com.baeldung.javaxval.enums.demo.CustomerUnitTest;

public class ValueOfEnumValidatorUnitTest {

    private static Validator validator;

    @BeforeClass
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory()
            .getValidator();
    }

    @Test
    public void whenStringAnyOfEnum_thenShouldNotReportConstraintViolations() {
        Customer customer = new Customer.Builder().withCustomerTypeString("DEFAULT")
            .build();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    public void whenStringNull_thenShouldNotReportConstraintViolations() {
        Customer customer = new Customer.Builder().withCustomerTypeString(null)
            .build();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    public void whenStringNotAnyOfEnum_thenShouldGiveOccurrenceOfConstraintViolations() {
        Customer customer = new Customer.Builder().withCustomerTypeString("test")
            .build();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations.size()).isEqualTo(1);

        assertThat(violations).anyMatch(CustomerUnitTest.havingPropertyPath("customerTypeString")
            .and(CustomerUnitTest.havingMessage("must be any of enum class com.baeldung.javaxval.enums.demo.CustomerType")));
    }
}