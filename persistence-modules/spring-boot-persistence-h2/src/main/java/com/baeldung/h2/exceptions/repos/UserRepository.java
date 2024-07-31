package com.baeldung.h2.exceptions.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.h2.exceptions.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
