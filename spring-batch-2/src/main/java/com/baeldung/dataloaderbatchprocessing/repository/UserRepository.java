package com.baeldung.dataloaderbatchprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.dataloaderbatchprocessing.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
}

