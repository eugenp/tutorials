package com.baeldung.javaxval.constraint.composition;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class AccountService {

    @AlphanumericReturnValue
    public String getAnInvalidAlphanumericValue() {
        return "john";
    }

    @AlphanumericReturnValue
    public String getValidAlphanumericValue() {
        return "johnDoe1234";
    }
}
