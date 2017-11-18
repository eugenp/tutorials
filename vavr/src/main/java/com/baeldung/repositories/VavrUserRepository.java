package com.baeldung.repositories;

import org.springframework.data.repository.Repository;

import com.baeldung.vavr.User;

import io.vavr.collection.Seq;
import io.vavr.control.Option;

public interface VavrUserRepository extends Repository<User, Long> {

    Option<User> findById(long id);

    Seq<User> findByName(String name);

    User save(User user);

}
