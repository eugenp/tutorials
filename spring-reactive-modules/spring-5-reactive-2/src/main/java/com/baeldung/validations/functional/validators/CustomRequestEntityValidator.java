package com.baeldung.validations.functional.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.baeldung.validations.functional.model.CustomRequestEntity;

public class CustomRequestEntityValidator implements Validator {

    private static final int MINIMUM_CODE_LENGTH = 6;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return CustomRequestEntity.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "code", "field.required");
        CustomRequestEntity request = (CustomRequestEntity) target;
        if (request.getCode() != null && request.getCode()
            .trim()
            .length() < MINIMUM_CODE_LENGTH) {
            errors.rejectValue("code", "field.min.length", new Object[] { Integer.valueOf(MINIMUM_CODE_LENGTH) }, "The code must be at least [" + MINIMUM_CODE_LENGTH + "] characters in length.");
        }
    }
}
