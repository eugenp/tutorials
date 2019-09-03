package org.baeldung.javabeanconstraints.validationgroup;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.BeforeClass;
import org.junit.Test;

public class RegistrationFormUnitTest {
	private static Validator validator;

	@BeforeClass
	public static void setupValidatorInstance() {
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}

	@Test
	public void whenBasicInfoIsNotComplete_thenShouldGiveConstraintViolationsOnlyForBasicInfo() {
		RegistrationForm form = buildRegistrationFormWithBasicInfo();
		form.setFirstName("");
		Set<ConstraintViolation<RegistrationForm>> violations = validator.validate(form, BasicInfo.class);
		assertThat(violations.size()).isEqualTo(1);
		violations.forEach(action -> {
			assertThat(action.getMessage()).isEqualTo("must not be blank");
			assertThat(action.getPropertyPath().toString()).isEqualTo("firstName");
		});
	}

	@Test
	public void whenAdvanceInfoIsNotComplete_thenShouldGiveConstraintViolationsOnlyForAdvanceInfo() {
		RegistrationForm form = buildRegistrationFormWithAdvanceInfo();
		form.setZipCode("");
		Set<ConstraintViolation<RegistrationForm>> violations = validator.validate(form, AdvanceInfo.class);
		assertThat(violations.size()).isEqualTo(1);
		violations.forEach(action -> {
			assertThat(action.getMessage()).isEqualTo("must not be blank");
			assertThat(action.getPropertyPath().toString()).isEqualTo("zipCode");
		});
	}

	@Test
	public void whenBasicAndAdvanceInfoIsComplete_thenShouldNotGiveConstraintViolations() {
		RegistrationForm form = buildRegistrationFormWithBasicAndAdvanceInfo();
		Set<ConstraintViolation<RegistrationForm>> violations = validator.validate(form);
		assertThat(violations.size()).isEqualTo(0);
	}

	private RegistrationForm buildRegistrationFormWithBasicInfo() {
		RegistrationForm form = new RegistrationForm();
		form.setFirstName("devender");
		form.setLastName("kumar");
		form.setEmail("anyemail@yopmail.com");
		form.setPhone("12345");
		return form;
	}

	private RegistrationForm buildRegistrationFormWithAdvanceInfo() {
		RegistrationForm form = new RegistrationForm();
		return populateAdvanceInfo(form);
	}

	private RegistrationForm populateAdvanceInfo(RegistrationForm form) {
		form.setCity("Berlin");
		form.setContry("DE");
		form.setStreet("alexa str.");
		form.setZipCode("19923");
		form.setHouseNumber("2a");
		return form;
	}

	private RegistrationForm buildRegistrationFormWithBasicAndAdvanceInfo() {
		RegistrationForm form = buildRegistrationFormWithBasicInfo();
		return populateAdvanceInfo(form);
	}
}
