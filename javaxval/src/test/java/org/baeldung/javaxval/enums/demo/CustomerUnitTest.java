package org.baeldung.javaxval.enums.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.function.Predicate;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.baeldung.javaxval.LocaleAwareUnitTest;
import org.junit.BeforeClass;
import org.junit.Test;

public class CustomerUnitTest extends LocaleAwareUnitTest {

    private static Validator validator;

    @BeforeClass
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory()
            .getValidator();
    }

    @Test
    public void whenAllAcceptable_thenShouldNotGiveConstraintViolations() {
        Customer customer = new Customer();
        customer.setCustomerTypeOfSubset(CustomerType.NEW);
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations).isEmpty();
    }

    @Test
    public void whenAllNull_thenOnlyNotNullShouldGiveConstraintViolations() {
        Customer customer = new Customer();
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations.size()).isEqualTo(1);

        assertThat(violations).anyMatch(havingPropertyPath("customerTypeOfSubset").and(havingMessage("must not be null")));
    }

    @Test
    public void whenAllInvalid_thenViolationsShouldBeReported() {
        Customer customer = new Customer();
        customer.setCustomerTypeString("invalid");
        customer.setCustomerTypeOfSubset(CustomerType.DEFAULT);
        customer.setCustomerTypeMatchesPattern(CustomerType.OLD);

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        assertThat(violations.size()).isEqualTo(3);

        assertThat(violations).anyMatch(havingPropertyPath("customerTypeString").and(havingMessage("must be any of enum class org.baeldung.javaxval.enums.demo.CustomerType")));
        assertThat(violations).anyMatch(havingPropertyPath("customerTypeOfSubset").and(havingMessage("must be any of [NEW, OLD]")));
        assertThat(violations).anyMatch(havingPropertyPath("customerTypeMatchesPattern").and(havingMessage("must match \"NEW|DEFAULT\"")));
    }

    public static Predicate<ConstraintViolation<Customer>> havingMessage(String message) {
        return l -> message.equals(l.getMessage());
    }

    public static Predicate<ConstraintViolation<Customer>> havingPropertyPath(String propertyPath) {
        return l -> propertyPath.equals(l.getPropertyPath()
            .toString());
    }
}