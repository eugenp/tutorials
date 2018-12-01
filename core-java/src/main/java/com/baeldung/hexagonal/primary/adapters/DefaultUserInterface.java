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
        return userService.login(uid, pwd);
    }

    @Override
    public String registerUser(String uid, String pwd) {
        User user = new User();
        user.setUid(uid);
        user.setPassword(pwd);
        return userService.register(user);
    }

}
