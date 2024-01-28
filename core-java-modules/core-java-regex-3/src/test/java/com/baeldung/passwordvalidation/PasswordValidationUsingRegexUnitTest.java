package com.baeldung.passwordvalidation;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordValidationUsingRegexUnitTest {
    String password = "Baeldung20@";

    @Test
    public void givenStringPassword_whenUsingDynamicPasswordValidationRules_thenCheckIfPasswordValid() {
        boolean result = false;
        try {
            if (password != null) {
                String MIN_LENGTH = "8";
                String MAX_LENGTH = "20";
                boolean SPECIAL_CHAR_NEEDED = false;

                String ONE_DIGIT = "(?=.*[0-9])";
                String LOWER_CASE = "(?=.*[a-z])";
                String UPPER_CASE = "(?=.*[A-Z])";
                String SPECIAL_CHAR = SPECIAL_CHAR_NEEDED ? "(?=.*[@#$%^&+=])" : "";
                String NO_SPACE = "(?=\\S+$)";

                String MIN_MAX_CHAR = ".{" + MIN_LENGTH + "," + MAX_LENGTH + "}";
                String PATTERN = ONE_DIGIT + LOWER_CASE + UPPER_CASE + SPECIAL_CHAR + NO_SPACE + MIN_MAX_CHAR;

                assertTrue(password.matches(PATTERN));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            fail("Exception occurred: " + ex.getMessage());
        }
    }

    @Test
    public void givenStringPassword_whenUsingRegulaExpressions_thenCheckIfPasswordValid() {


        String regExpn =
                "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(password);

        assertTrue(matcher.matches());
    }

}
