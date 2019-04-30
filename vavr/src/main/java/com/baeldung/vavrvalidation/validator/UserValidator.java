package com.baeldung.vavrvalidation.validator;

import com.baeldung.vavrvalidation.model.User;
import io.vavr.collection.CharSeq;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;

public class UserValidator {
    
    private static final String NAME_PATTERN = "^[a-zA-Z0-9]+$";
    private static final String NAME_ERROR = "Name contains invalid characters";
    private static final String EMAIL_PATTERN =
        "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
          + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String EMAIL_ERROR = "Email must be a well-formed email address";
    
	public Validation<Seq<String>, User> validateUser(String name, String email) {
        return Validation
          .combine(validateField(name, NAME_PATTERN, NAME_ERROR)
              ,validateField(email, EMAIL_PATTERN, EMAIL_ERROR))
                .ap(User::new);
    }
    
    private Validation<String, String> validateField(String field, String pattern, String error) {
        return CharSeq.of(field).replaceAll(pattern, "").transform(seq -> seq.isEmpty()
          ? Validation.valid(field) 
            : Validation.invalid(error));
    }
}
