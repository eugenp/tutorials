package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.model.User;
import com.baeldung.hexagonal.port.UserApiPort;
import com.baeldung.hexagonal.service.UserService;

public class WebLoginAdapter implements UserApiPort {

    private UserService userService;

    public WebLoginAdapter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String getLoginToken(String username, String password) {

        User user = userService.findUser(username);
        return "Web-Login-Token | " + user.getToken();
    }
}