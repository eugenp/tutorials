package com.baeldung.osiv.service;

import com.baeldung.osiv.model.BasicUser;

import java.util.Optional;

public interface UserService {
    Optional<BasicUser> findOne(String username);
}
