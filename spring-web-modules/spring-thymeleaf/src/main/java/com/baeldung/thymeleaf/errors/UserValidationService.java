package com.baeldung.thymeleaf.errors;

import org.springframework.stereotype.Service;

@Service
public class UserValidationService {

    public String validateUser(User user) {

        String message = "";

        if (user.getCountry() != null && user.getPhoneNumber() != null) {
            if (user.getCountry()
                .equalsIgnoreCase("India")
                && !user.getPhoneNumber()
                    .startsWith("91")) {
                message = "Phone number is invalid for " + user.getCountry();
            }
        }
        return message;
    }
}
