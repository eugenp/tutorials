package com.baeldung.test.comparison;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DependentTests {

    private EmailValidator emailValidator;
    private LoginValidator loginValidator;
    private String validEmail = "abc@qwe.com";

    @BeforeClass
    public void setup() {
        emailValidator = new EmailValidator();
        loginValidator = new LoginValidator();
    }

    @Test
    public void givenEmail_ifValid_thenTrue() {
        boolean valid = emailValidator.validate(validEmail);
        Assert.assertEquals(valid, true);
    }

    @Test(dependsOnMethods = { "givenEmail_ifValid_thenTrue" })
    public void givenValidEmail_whenLoggedin_thenTrue() {
        boolean valid = loginValidator.validate();
        Assert.assertEquals(valid, true);
    }
}

class EmailValidator {

    public boolean validate(String validEmail) {
        return true;
    }

}

class LoginValidator {

    public boolean validate() {
        return true;
    }

}
