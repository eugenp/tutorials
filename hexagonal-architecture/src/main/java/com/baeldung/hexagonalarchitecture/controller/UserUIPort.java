package com.baeldung.hexagonalarchitecture.controller;

import com.baeldung.hexagonalarchitecture.model.User;

public interface UserUIPort {

    public void createUser(User user);

    public User getUser(Long id);

}
