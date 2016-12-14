package com.baeldung.example.validator;

import org.springframework.stereotype.Component;

import com.baeldung.example.exception.ValidationException;
import com.baeldung.example.model.User;

@Component
public class UserValidator {

    public void validate(String userName, User user) {
        if (!userName.equals(user.getName())) {
            throw new ValidationException("the userName path variable does not match");
        }
        validate(user);
    }

    public void validate(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new ValidationException("the user name must not be empty");
        }
        if (user.getTld() == null || user.getTld().isEmpty()) {
            throw new ValidationException("the user tld must not be empty");
        }
    }
}
