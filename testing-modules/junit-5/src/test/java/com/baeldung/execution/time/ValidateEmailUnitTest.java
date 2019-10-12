package com.baeldung.execution.time;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidateEmailUnitTest {

    @Test
    public void whenValidEmailThenReturnTrue() {
        String email = "execution.time@baeldung.com";

        assertTrue(isValid(email));
    }

    public boolean isValid(String email) {
        if (email == null || email.isEmpty())
            return false;

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);

        return pat.matcher(email).matches();

    }
}
