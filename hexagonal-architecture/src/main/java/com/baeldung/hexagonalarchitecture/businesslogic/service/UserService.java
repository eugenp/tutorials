package com.baeldung.hexagonalarchitecture.businesslogic.service;

import com.baeldung.hexagonalarchitecture.businesslogic.dto.User;
import com.baeldung.hexagonalarchitecture.userside.request.UserCreateRequest;

import java.util.UUID;

public interface UserService {
    UUID createUser(UserCreateRequest userCreateRequest);

    User activeUser(UUID uuid);
}
