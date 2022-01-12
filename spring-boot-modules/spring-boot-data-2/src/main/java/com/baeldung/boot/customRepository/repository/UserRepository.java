package com.baeldung.boot.customRepository.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.boot.customRepository.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, CustomUserRepository {

}
