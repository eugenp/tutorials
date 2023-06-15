package com.baeldung.fieldinjection;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public static final String INVALID_EMAIL = "Invalid email";
    private final EmailValidator emailValidator;

    public EmailService(final EmailValidator emailValidator) {
        this.emailValidator = emailValidator;
    }

    public void process(String email) {
        if (!emailValidator.isValid(email)) {
            throw new IllegalArgumentException(INVALID_EMAIL);
        }
        // ...
    }
}