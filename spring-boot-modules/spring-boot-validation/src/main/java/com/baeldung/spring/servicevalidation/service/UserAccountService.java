package com.baeldung.spring.servicevalidation.service;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.spring.servicevalidation.dao.UserAccountDao;
import com.baeldung.spring.servicevalidation.domain.UserAccount;

@Service
public class UserAccountService {

    @Autowired
    private Validator validator;

    @Autowired
    private UserAccountDao dao;

    public String addUserAccount(UserAccount useraccount) {

        Set<ConstraintViolation<UserAccount>> violations = validator.validate(useraccount);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<UserAccount> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }

        dao.addUserAccount(useraccount);
        return "Account for " + useraccount.getName() + " Added!";
    }

}
