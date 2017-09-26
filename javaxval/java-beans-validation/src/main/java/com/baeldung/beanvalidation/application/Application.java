package com.baeldung.beanvalidation.application;

import java.util.Date;
import javax.validation.Validation;
import javax.validation.Validator;
import com.baeldung.beanvalidation.model.User;

public class Application {
    
    public static void main( String[] args ) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        User user = new User();
        user.setName("Mary");
        user.setEmail("no-email");
        user.setBirthday(new Date());
        validator.validate(user).stream().forEach(violation -> System.out.println(violation.getMessage()));
    }
}
