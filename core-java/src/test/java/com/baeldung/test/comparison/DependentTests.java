package com.baeldung.test.comparison;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DependentTests {
	
	private EmailValidator emailValidator;
	private LoginValidator loginValidator;
	private String validEmail = "abc@qwe.com";
	
	@BeforeClass
	public void setup(){
		emailValidator = new EmailValidator();
		loginValidator = new LoginValidator();
	}
	
	@Test
	public void validEmailTest() {
	    boolean valid = emailValidator.validate(validEmail);
	    Assert.assertEquals(valid, true);
	}

	@Test(dependsOnMethods={"validEmailTest"})
	public void validateLogin() {
	    boolean valid = loginValidator.validate();
	    Assert.assertEquals(valid, true);
	}
}

class EmailValidator{

	public boolean validate(String validEmail) {
		return true;
	}
	
}

class LoginValidator{

	public boolean validate() {
		return true;
	}
	
}
