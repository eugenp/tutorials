package com.baeldung.spring.data.jpa.query.validation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.group = :group")
    List<User> findByGroup(@Param("group") String group);

    @Query("SELECT u FROM User u WHERE u.firstName = :firstName")
    List<User> findByFirstName(@Param("firstName") String firstName);

    @Query(value = "SELECT * FROM users WHERE status = 1", nativeQuery = true)
    List<User> findActiveUsers();
}