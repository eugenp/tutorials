package com.baeldung.boot.architecture.hexagonal.repository;

import com.baeldung.boot.architecture.hexagonal.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Thanos Floros (thanosfloros@strong-programmer.com)
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    List<User> findAll();
}
