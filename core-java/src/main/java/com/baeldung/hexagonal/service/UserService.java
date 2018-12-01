package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.data.User;

public interface UserService {

    public String login(String uid, String pwd);

    public String register(User user);
}
