package com.baeldung.spring.aotrepository.repository;

import com.baeldung.spring.aotrepository.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends Repository<User, Long> {

    User save(User user);

    @Transactional(readOnly = true)
    List<User> findAll();

    List<User> findAllById(Iterable<Long> longs);

    List<User> findByLastNameContainingIgnoreCase(String lastName);

    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<User> nativeQueryFindAllUsers();

    @Query(value = "SELECT u FROM User u")
    List<User> queryFindAllUsers();

    @Transactional(readOnly = true)
    @Query(value = "SELECT u FROM User u WHERE u.firstName = :firstName")
    List<User> queryFindByFirstNameSorted(@Param(value = "firstName") String firstName, Sort sort);

    void delete(User entity);
}
