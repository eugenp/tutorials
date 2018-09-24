package com.baeldung.hexagonal.adapters;

import com.baeldung.hexagonal.ports.PhoneValidator;

public class RegexPhoneValidator implements PhoneValidator {

    @Override
    public boolean validate(String phoneNumber) {
        return phoneNumber.matches("\\(?([1-9][0-9]{2})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})");
    }

}
