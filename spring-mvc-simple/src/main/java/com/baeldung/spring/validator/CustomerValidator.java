package com.baeldung.spring.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.baeldung.spring.domain.Customer;

@Component
public class CustomerValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerId", "error.customerId", "Customer Id is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerName", "error.customerName", "Customer Name is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerContact", "error.customerNumber", "Customer Contact is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "customerEmail", "error.customerEmail", "Customer Email is required.");

    }

}
