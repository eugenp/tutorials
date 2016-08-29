package com.baeldung.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baeldung.models.WebsiteUser;

@Component("beforeCreateWebsiteUserValidator")
public class WebsiteUserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return WebsiteUser.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {

        WebsiteUser user = (WebsiteUser) obj;
        if (user.getName() == null || user.getName().trim().length() == 0)
            errors.rejectValue("name", "name.empty");

        if (user.getEmail() == null || user.getEmail().trim().length() == 0) {
            errors.rejectValue("email", "email.empty");
        }
    }
}
