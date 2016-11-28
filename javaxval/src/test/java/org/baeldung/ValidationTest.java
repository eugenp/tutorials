package org.baeldung;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Assert;
import org.junit.Test;

public class ValidationTest {

	@Test
	public void ifNameIsNull_nameValidationFails() {
		User user = new User();
		user.setWorking(true);
		user.setAboutMe("Its all about me!!");
		user.setAge(50);

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(user);					
		Assert.assertEquals(violations.isEmpty(), false);
	}

	@Test
	public void ifSizeNotInRange_aboutMeValidationFails() {
		User user = new User();
		user.setName("MyName");
		user.setAboutMe("Its all about me!!");
		user.setAge(50);

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		Assert.assertEquals(violations.isEmpty(), false);
	}

	@Test
	public void ifWorkingIsFalse_workingValidationFails() {
		User user = new User();
		user.setName("MyName");
		user.setAboutMe("Its all about me!!");
		user.setAge(50);

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		Assert.assertEquals(violations.isEmpty(), false);
	}

	@Test
	public void ifAgeNotRange_ageValidationFails() {
		User user = new User();
		user.setName("MyName");
		user.setAboutMe("Its all about me!!");
		user.setAge(8);

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		Assert.assertEquals(violations.isEmpty(), false);
	}
	
	
	@Test
	public void ifFnameNullAgeNotRangeAndWorkingIsFalse_validationFailsWithThreeErrors() {
		User user = new User();		
		user.setAboutMe("Its all about me!!");
		user.setAge(300);

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(user);		
		Assert.assertEquals(violations.isEmpty(), false);
		Assert.assertEquals(violations.size(), 3);
	}
}
