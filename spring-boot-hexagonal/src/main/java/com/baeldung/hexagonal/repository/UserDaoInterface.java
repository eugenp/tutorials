package com.baeldung.hexagonal.repository;

import com.baeldung.hexagonal.domain.User;

public interface UserDaoInterface {
    User getUserById(int id);
}
