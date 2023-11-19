package com.baeldung.javaxval.notnull;

import jakarta.validation.constraints.NotNull;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class ValidatingComponent {

    public int validateNotNull(@NotNull String data)
    {
        return data.length();
    }

    public int callAnnotatedMethod(String data) {
        return validateNotNull(data);
    }

}
