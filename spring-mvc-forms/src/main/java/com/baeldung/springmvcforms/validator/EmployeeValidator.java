package com.baeldung.springmvcforms.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.baeldung.springmvcforms.domain.Employee;

@Component
public class EmployeeValidator implements Validator {

	public boolean supports(Class<?> clazz) {
		return Employee.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) 
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.name", "Name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactNumber", "error.contactNumber", "Contact Number is required.");
	}

}
