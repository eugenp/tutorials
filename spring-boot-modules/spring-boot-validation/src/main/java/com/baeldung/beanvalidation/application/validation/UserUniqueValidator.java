package com.baeldung.beanvalidation.application.validation;

import com.baeldung.beanvalidation.application.repositories.AppUserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UserUniqueValidator implements ConstraintValidator<UserUnique, String> {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return appUserRepository.findByUsername(username) == null;
    }
}