package com.baeldung.service;

import com.baeldung.model.BasicUser;

import java.util.Optional;

public interface UserService {
    Optional<BasicUser> findOne(String username);
}
