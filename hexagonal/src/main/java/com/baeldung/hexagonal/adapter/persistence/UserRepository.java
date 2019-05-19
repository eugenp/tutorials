package com.baeldung.hexagonal.adapter.persistence;

import com.baeldung.hexagonal.domain.User;

public interface UserRepository {
    User findUserByName(String name);
}
