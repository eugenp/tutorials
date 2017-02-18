package baeldung.com;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DependentTests {

    private String validEmail = "abc@qwe.com";
    private EmailValidator emailValidator;
    private LoginValidator loginValidator;

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
    public void givenValidEmail_whenLoggedIn_thenTrue() {
        boolean valid = loginValidator.validate();
        Assert.assertEquals(valid, true);
    }
}

class EmailValidator {

    boolean validate(String validEmail) {
        return true;
    }

}

class LoginValidator {

    boolean validate() {
        return true;
    }

}
