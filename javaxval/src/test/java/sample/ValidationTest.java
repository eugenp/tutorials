package sample;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Assert;
import org.junit.Test;

import sample.model.User;

public class ValidationTest {

	@Test
	public void validation_when_fname_is_null() {
		System.out
				.println("\n---------------validation_when_fname_is_null--------------");

		// [1]
		User user = new User();
		user.setLname("Last");
		user.setEmail("first.last@gmail.com");
		user.setGender("male");
		user.setAge(29);

		// [2]
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		// [3]
		Validator validator = factory.getValidator();
		// [4]
		Set<ConstraintViolation<User>> violations = validator.validate(user);

		// [5]
		Iterator<ConstraintViolation<User>> iter = violations.iterator();

		while (iter.hasNext()) {
			// [6]
			ConstraintViolation<User> cv = iter.next();
			
			// [7]
			System.out.println(cv.getMessage());
		}

		Assert.assertEquals(violations.isEmpty(), false);
	}

	@Test
	public void validation_when_empty_lname() {
		System.out
				.println("\n---------------validation_when_empty_lname--------------");
		User user = new User();
		user.setFname("First");
		user.setLname("");
		user.setEmail("first.last@gmail.com");
		user.setGender("male");
		user.setAge(29);

		// validate the input
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(user);

		Iterator<ConstraintViolation<User>> iter = violations.iterator();

		while (iter.hasNext()) {
			ConstraintViolation<User> cv = iter.next();
			System.out.println(cv.getMessage());
		}

		Assert.assertEquals(violations.isEmpty(), false);
	}

	@Test
	public void validation_when_email_is_invalid() {
		System.out
				.println("\n---------------validation_when_email_is_invalid--------------");
		User user = new User();
		user.setFname("First");
		user.setLname("Last");
		user.setEmail("firstgmail.com");
		user.setGender("male");
		user.setAge(29);

		// validate the input
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<User>> violations = validator.validate(user);

		Iterator<ConstraintViolation<User>> iter = violations.iterator();

		while (iter.hasNext()) {
			ConstraintViolation<User> cv = iter.next();
			System.out.println(cv.getMessage());
		}

		Assert.assertEquals(violations.isEmpty(), false);
	}
}
