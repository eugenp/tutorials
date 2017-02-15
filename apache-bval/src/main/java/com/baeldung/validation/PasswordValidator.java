package com.baeldung.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private int length;
    private int nonAlfa;

    @Override
    public void initialize(Password password) {
        this.length = password.length();
        this.nonAlfa = password.nonAlfa();

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.length() < length) {
            return false;
        }
        int nonAlfaNr = 0;
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isLetterOrDigit(value.charAt(i))) {
                nonAlfaNr++;
            }
        }
        if (nonAlfaNr < nonAlfa) {
            return false;
        }
        return true;
    }

}
