package com.baeldung.hexagonal.repository;

import com.baeldung.hexagonal.domain.User;
import com.baeldung.hexagonal.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    public User CreateUser(User user);

    public User UpdateUser(User user) throws UserNotFoundException;

    public List<User> findAll();

    public Optional<User> findCustomerById(int customerId);

}
