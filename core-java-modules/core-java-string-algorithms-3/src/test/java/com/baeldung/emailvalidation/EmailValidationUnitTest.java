package com.baeldung.emailvalidation;

import static org.junit.Assert.assertTrue;
import org.apache.commons.validator.routines.EmailValidator;
import org.junit.Test;

public class EmailValidationUnitTest {

    private String emailAddress;
    private String regexPattern;

    @Test
    public void testUsingEmailValidator() {
        emailAddress = "username@domain.com";
        assertTrue(EmailValidator.getInstance()
            .isValid(emailAddress));
    }

    @Test
    public void testUsingSimpleRegex() {
        emailAddress = "username@domain.com";
        regexPattern = "^(.+)@(\\S+)$";
        assertTrue(EmailValidation.patternMatches(emailAddress, regexPattern));
    }

    @Test
    public void testUsingStrictRegex() {
        emailAddress = "username@domain.com";
        regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        assertTrue(EmailValidation.patternMatches(emailAddress, regexPattern));
    }

    @Test
    public void testUsingUnicodeRegex() {
        emailAddress = "用户名@领域.电脑";
        regexPattern = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@" 
            + "[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
        assertTrue(EmailValidation.patternMatches(emailAddress, regexPattern));
    }

    @Test
    public void testUsingRFC5322Regex() {
        emailAddress = "username@domain.com";
        regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        assertTrue(EmailValidation.patternMatches(emailAddress, regexPattern));
    }

    @Test
    public void testRestrictDots() {
        emailAddress = "username@domain.com";
        regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@" 
            + "[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        assertTrue(EmailValidation.patternMatches(emailAddress, regexPattern));
    }

    @Test
    public void testOwaspValidation() {
        emailAddress = "username@domain.com";
        regexPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        assertTrue(EmailValidation.patternMatches(emailAddress, regexPattern));
    }

    @Test
    public void testTopLevelDomain() {
        emailAddress = "username@domain.com";
        regexPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*" 
            + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        assertTrue(EmailValidation.patternMatches(emailAddress, regexPattern));
    }
    
    @Test
    public void testGmailSpecialCase() {
        emailAddress = "username+something@domain.com";
        regexPattern = "^(?=.{1,64}@)[A-Za-z0-9\\+_-]+(\\.[A-Za-z0-9\\+_-]+)*@" 
            + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2,})$";
        assertTrue(EmailValidation.patternMatches(emailAddress, regexPattern));
    }
}
