package com.baeldung.hexagonal.repository;

import java.util.List;

import com.baeldung.hexagonal.dto.User;

public interface UserRepository {
    public List<User> getUsers();
}
