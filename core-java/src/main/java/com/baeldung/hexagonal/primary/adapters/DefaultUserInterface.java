package com.baeldung.hexagonal.primary.adapters;

import com.baeldung.hexagonal.data.User;
import com.baeldung.hexagonal.primary.ports.UserInterface;
import com.baeldung.hexagonal.service.UserService;

public class DefaultUserInterface implements UserInterface {

    private UserService userService;

    public DefaultUserInterface(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String loginUser(String uid, String pwd) {
        boolean loginSuccess = userService.login(uid, pwd);
        if (loginSuccess) {
            return "User is logged in successfully";
        } else {
            return "User not able to login due to incorrect username or password";
        }
    }

    @Override
    public String registerUser(String uid, String pwd) {
        User user = new User();
        user.setUid(uid);
        user.setPassword(pwd);
        boolean registrationSuccess = userService.register(user);
        if (registrationSuccess) {
            return "User registration success";
        } else {
            return "User registration failed";
        }
    }

}
