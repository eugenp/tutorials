package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.ports.EmailValidator;

public class RegexEmailValidator implements EmailValidator {

    @Override
    public boolean validate(String emailAddress) {
        return emailAddress.matches("[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}");
    }

}
