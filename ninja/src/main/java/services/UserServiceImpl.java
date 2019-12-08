package services;

import models.User;

public class UserServiceImpl implements UserService {

    @Override
    public User getUser() {
        User user = new User();
        user.firstName = "Norman";
        user.email = "norman@email.com";
        return user;
    }

}
