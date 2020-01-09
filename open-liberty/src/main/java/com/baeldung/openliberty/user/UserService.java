package com.baeldung.openliberty.user;

import javax.enterprise.context.ApplicationScoped;

import com.baeldung.openliberty.user.model.User;

@ApplicationScoped
public class UserService {

    public User getUser() {
        User user = new User();
        user.setFirstName("Norman");
        user.setLastName("Lewis");
        user.setEmail("normanlewis@email.com");
        return user;
    }

}
