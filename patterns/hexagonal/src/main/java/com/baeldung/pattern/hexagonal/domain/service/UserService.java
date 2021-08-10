package com.baeldung.pattern.hexagonal.domain.service;

import com.baeldung.pattern.hexagonal.domain.model.User;

public interface UserService {

    void save(User user);

    User getByEmail(String email);
}
