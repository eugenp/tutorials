package com.baeldung.optionalsasparameterrecords;

import java.util.Optional;

public record User(String username, String email, String phoneNumber) {
    public Optional<String> getOptionalPhoneNumber() {
        return Optional.ofNullable(phoneNumber);
    }
}
