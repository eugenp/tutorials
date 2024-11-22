package com.baeldung.boot.daos.user;

import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baeldung.boot.domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Stream<User> findAllByName(String name);

}
