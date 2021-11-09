package com.baeldung.openapi.petstore.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CamelcaseValidator implements ConstraintValidator<Camelcase, String> {

	private Camelcase uppercaseAnnotation;

	public void initialize(Camelcase constraintAnnotation) {
		this.uppercaseAnnotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(String nameField, ConstraintValidatorContext context) {
		String correctName = buildCorrectName(nameField);
		if (uppercaseAnnotation.required()
		  && (Objects.isNull(nameField)  || nameField.isBlank()
		  || !correctName.equals(nameField))) {
			context = context
			  .buildConstraintViolationWithTemplate(this.uppercaseAnnotation.message())
			  .addConstraintViolation();
			context.disableDefaultConstraintViolation();
			return false;
		}
		return true;
	}

	private String buildCorrectName(String nameField) {
		String upCase = String.valueOf(nameField.charAt(0)).toUpperCase();
		String lowCase = nameField.substring(1).toLowerCase();
		return upCase.concat(lowCase);
	}
}