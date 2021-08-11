package com.baeldung.hexagonal.service;

import com.baeldung.hexagonal.domain.User;

public interface UserService {
    User getUserById(int id);
}
