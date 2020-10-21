package com.baeldung.spring.data.snowflake.repository;

import com.baeldung.spring.data.snowflake.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query(value = "SELECT ID as \"id\", NAME as \"name\" FROM USER WHERE ID = ?", nativeQuery = true)
    User findByUserId(Long id);

    @Query(value = "SELECT ID as \"id\", NAME as \"name\" FROM USER", nativeQuery = true)
    List<User> findAllUsers();
}
