package com.baeldung.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.baeldung.model.Employee;

@Component
public class EmployeeValidator implements Validator {

	public boolean supports(Class clazz) {
		return Employee.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) 
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.name", "Name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "error.id", "Id is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contactNumber", "error.contactNumber", "Contact Number is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "workingArea", "error.workingArea", "Working Area is required.");
	}

}
