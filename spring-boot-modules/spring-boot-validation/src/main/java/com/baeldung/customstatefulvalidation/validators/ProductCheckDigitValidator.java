package com.baeldung.customstatefulvalidation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.stream.IntStream;

public class ProductCheckDigitValidator implements ConstraintValidator<ProductCheckDigit, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        String[] parts = value.split("-");

        return parts.length == 3 && checkDigitMatches(parts[1], parts[2]);
    }

    private static boolean checkDigitMatches(String productCode, String checkDigit) {
        int sumOfDigits = IntStream.range(0, productCode.length())
                .map(character -> Character.getNumericValue(productCode.charAt(character)))
                .sum();

        int checkDigitProvided = Character.getNumericValue(checkDigit.charAt(0));
        return checkDigitProvided == sumOfDigits % 10;
    }
}
