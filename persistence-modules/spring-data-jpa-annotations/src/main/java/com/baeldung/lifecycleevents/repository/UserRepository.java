package com.baeldung.lifecycleevents.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.lifecycleevents.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUserName(String userName);
}
