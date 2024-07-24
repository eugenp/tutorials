package com.baeldung.javaxval.enums;

import static com.baeldung.javaxval.enums.demo.CustomerType.DEFAULT;
import static com.baeldung.javaxval.enums.demo.CustomerType.OLD;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.javaxval.enums.demo.Customer;
import com.baeldung.javaxval.enums.demo.CustomerType;
import com.baeldung.javaxval.enums.demo.CustomerUnitTest;

public class EnumNamePatternValidatorUnitTest {

    private static Validator validator;

    @BeforeClass
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory()
            .getValidator();
    }

    @Test
    public void whenEnumMatchesRegex_thenShouldNotReportConstraintViolations() {
        Customer customer = new Customer.Builder().withCustomerTypeMatchesPattern("DEFAULT")
            .build();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    public void whenEnumNull_thenShouldNotReportConstraintViolations() {
        Customer customer = new Customer.Builder().withCustomerTypeMatchesPattern(null)
            .build();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations.isEmpty()).isTrue();
    }

    @Test
    public void whenEnumDoesNotMatchRegex_thenShouldGiveOccurrenceOfConstraintViolations() {
        Customer customer = new Customer.Builder().withCustomerTypeMatchesPattern("OLD")
            .build();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations.size()).isEqualTo(1);

        assertThat(violations).anyMatch(CustomerUnitTest.havingPropertyPath("customerTypeMatchesPattern")
            .and(CustomerUnitTest.havingMessage("must match \"NEW|DEFAULT\"")));
    }

    @Test
    public void whenEnumDoesNotMatchRegexAndCustomerType_thenShouldGiveOccurrenceOfConstraintViolations() {
        Customer customer = new Customer.Builder().withCustomerTypeMatchesPattern("TESTING")
            .build();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        for (ConstraintViolation<Customer> violation : violations) {
            System.out.printf("Property: %s, Invalid value: %s, Error message: %s%n",
                violation.getPropertyPath(), violation.getInvalidValue(), violation.getMessage());
        }
        // Check for constraint violations
        assertThat(violations.size()).isEqualTo(1);

        assertThat(violations).anyMatch(violation ->
            violation.getPropertyPath().toString().equals("customerTypeMatchesPattern") &&
                violation.getMessage().equals("must match \"NEW|DEFAULT\"")
        );
    }
}