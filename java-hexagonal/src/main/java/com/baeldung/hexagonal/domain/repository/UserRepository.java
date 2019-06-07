package com.baeldung.hexagonal.domain.repository;

import com.baeldung.hexagonal.domain.entity.User;

public interface UserRepository {
    User readUser();
}
