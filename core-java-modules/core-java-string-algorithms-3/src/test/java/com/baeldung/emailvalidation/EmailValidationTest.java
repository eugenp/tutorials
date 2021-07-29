package com.baeldung.emailvalidation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EmailValidationTest {

    @Test
    public void testUsingEmailValidator() {

        String emailaddress = "username@domain.com";

        boolean result = EmailValidation.usingEmailValidator(emailaddress);

        assertEquals(result, true);
    }

    @Test
    public void testUsingSimpleRegEx() {

        String emailaddress = "username@domain.com";

        boolean result = EmailValidation.usingSimpleRegEx(emailaddress);

        assertEquals(result, true);
    }

    @Test
    public void testUsingStrictRegEx() {

        String emailaddress = "username@domain.com";

        boolean result = EmailValidation.usingStrictRegEx(emailaddress);

        assertEquals(result, true);
    }

    @Test
    public void testUsingUnicodeRegEx() {

        String emailaddress = "用户名@领域.电脑";

        boolean result = EmailValidation.usingUnicodeRegEx(emailaddress);

        assertEquals(result, true);
    }

    @Test
    public void testUsingRFC5322RegEx() {

        String emailaddress = "username@domain.com";

        boolean result = EmailValidation.usingRFC5322RegEx(emailaddress);

        assertEquals(result, true);
    }

    @Test
    public void testRestrictDots() {

        String emailaddress = "username@domain.com";

        boolean result = EmailValidation.restrictDots(emailaddress);

        assertEquals(result, true);
    }

    @Test
    public void testOwaspValidation() {

        String emailaddress = "username@domain.com";

        boolean result = EmailValidation.owaspValidation(emailaddress);

        assertEquals(result, true);
    }

    @Test
    public void testTopLevelDomain() {

        String emailaddress = "username@domain.com";

        boolean result = EmailValidation.topLevelDomain(emailaddress);

        assertEquals(result, true);
    }

}
