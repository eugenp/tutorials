package com.baeldung.springvalidator;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (StringUtils.isEmpty(user.getName())) {
            errors.rejectValue("name", "name.required");
        }
        if (StringUtils.isEmpty(user.getEmail())) {
            errors.rejectValue("email", "email.required");
        }
        // Add more validation rules as needed
    }

    public void validate(Object target, Errors errors, Object... validationHints) {
        User user = (User) target;
        if (validationHints.length > 0) {
            if (validationHints[0] == "create") {
                if (StringUtils.isEmpty(user.getName())) {
                    errors.rejectValue("name", "name.required","Name cannot be empty");
                }
                if (StringUtils.isEmpty(user.getEmail())) {
                    errors.rejectValue("email", "email.required" , "Invalid email format");
                }
                if (user.getAge() < 18 || user.getAge() > 65) {
                    errors.rejectValue("age", "user.age.outOfRange", new Object[]{18, 65}, "Age must be between 18 and 65");
                }
            } else if (validationHints[0] == "update") {
                // Perform update-specific validation
                if (StringUtils.isEmpty(user.getName()) && StringUtils.isEmpty(user.getEmail())) {
                    errors.rejectValue("name", "name.or.email.required", "Name or email cannot be empty");
                }
            }
        } else {
            // Perform default validation
            if (StringUtils.isEmpty(user.getName())) {
                errors.rejectValue("name", "name.required");
            }
            if (StringUtils.isEmpty(user.getEmail())) {
                errors.rejectValue("email", "email.required");
            }
        }
    }
}