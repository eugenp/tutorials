package com.baeldung.hexagonal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
