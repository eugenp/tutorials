package com.baeldung.emailvalidation;

import static org.junit.Assert.assertEquals;
import org.apache.commons.validator.routines.EmailValidator;
import org.junit.Test;

public class EmailValidationUnitTest {

    private String emailAddress;

    @Test
    public void testUsingEmailValidator() {
        emailAddress = "username@domain.com";
        assertEquals(EmailValidator.getInstance()
            .isValid(emailAddress), true);
    }

    @Test
    public void testUsingSimpleRegex() {
        emailAddress = "username@domain.com";
        assertEquals(EmailValidation.usingSimpleRegex(emailAddress), true);
    }

    @Test
    public void testUsingStrictRegex() {
        emailAddress = "username@domain.com";
        assertEquals(EmailValidation.usingStrictRegex(emailAddress), true);
    }

    @Test
    public void testUsingUnicodeRegex() {
        emailAddress = "用户名@领域.电脑";
        assertEquals(EmailValidation.usingUnicodeRegex(emailAddress), true);
    }

    @Test
    public void testUsingRFC5322Regex() {
        emailAddress = "username@domain.com";
        assertEquals(EmailValidation.usingRFC5322Regex(emailAddress), true);
    }

    @Test
    public void testRestrictDots() {
        emailAddress = "username@domain.com";
        assertEquals(EmailValidation.restrictDots(emailAddress), true);
    }

    @Test
    public void testOwaspValidation() {
        emailAddress = "username@domain.com";
        assertEquals(EmailValidation.owaspValidation(emailAddress), true);
    }

    @Test
    public void testTopLevelDomain() {
        emailAddress = "username@domain.com";
        assertEquals(EmailValidation.topLevelDomain(emailAddress), true);
    }
}