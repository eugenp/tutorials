package com.baeldung.validations.functional.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.baeldung.validations.functional.model.OtherEntity;

public class OtherEntityValidator implements Validator {

    private static final int MIN_ITEM_QUANTITY = 1;

    @Override
    public boolean supports(Class<?> clazz) {
        return OtherEntity.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "item", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantity", "field.required");
        OtherEntity request = (OtherEntity) target;
        if (request.getQuantity() != null && request.getQuantity() < MIN_ITEM_QUANTITY) {
            errors.rejectValue("quantity", "field.min.length", new Object[] { Integer.valueOf(MIN_ITEM_QUANTITY) }, "There must be at least one item");
        }
    }
}
