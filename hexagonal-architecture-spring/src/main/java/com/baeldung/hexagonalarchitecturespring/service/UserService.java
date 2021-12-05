package com.baeldung.hexagonalarchitecturespring.service;

import com.baeldung.hexagonalarchitecturespring.domain.User;

import java.util.UUID;

public interface UserService {
    User create(String name, int age);
    void enable(UUID id);
    void disable(UUID id);
}
