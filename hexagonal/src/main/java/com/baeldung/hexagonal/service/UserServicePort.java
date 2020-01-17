package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.domain.User;

public interface UserServicePort {

    public void createUser(String name);

    public User getUser(Long userId);
}
