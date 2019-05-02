package com.baeldung.multipledb.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.multipledb.model.user.UserMultipleDB;

public interface UserRepository extends JpaRepository<UserMultipleDB, Integer> {
}