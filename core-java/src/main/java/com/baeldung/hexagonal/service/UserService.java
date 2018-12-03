package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.data.User;

public interface UserService {

    public boolean login(String uid, String pwd);

    public boolean register(User user);
}
