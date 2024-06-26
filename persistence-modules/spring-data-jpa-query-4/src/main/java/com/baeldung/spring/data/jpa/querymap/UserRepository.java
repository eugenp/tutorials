package com.baeldung.spring.data.jpa.querymap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Streamable;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface UserRepository extends JpaRepository<User, Long> {

    String FIND_ALL_USERS = "select u from User u";

    @Query(FIND_ALL_USERS)
    Streamable<User> findAllAsStreamable();

    @Query(FIND_ALL_USERS)
    Stream<User> findAllAsStream();

    @Query(FIND_ALL_USERS)
    Users findAllUsers();

    default Map<Long, User> findAllAsMapUsingCollection() {
        return findAll().stream().collect(Collectors.toMap(User::getId, Function.identity()));
    }

    default Map<Long, User> findAllAsMapUsingStreamable() {
        return findAllAsStreamable().stream().collect(Collectors.toMap(User::getId, Function.identity()));
    }

    @Transactional
    default Map<Long, User> findAllAsMapUsingStream() {
        return findAllAsStream().collect(Collectors.toMap(User::getId, Function.identity()));
    }
}
