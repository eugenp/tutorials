package com.baeldung.javaxval.notnull;

import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

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
