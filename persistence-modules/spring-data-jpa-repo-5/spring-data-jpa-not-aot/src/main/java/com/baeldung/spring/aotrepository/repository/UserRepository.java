package com.baeldung.spring.aotrepository.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.spring.aotrepository.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);

    @Transactional(readOnly = true)
    List<User> findAll();

    List<User> findAllById(Iterable<Long> longs);

    @Query(value = "SELECT * FROM users", nativeQuery = true)
    List<User> nativeQueryFindAllUsers();

    @Query(value = "SELECT u FROM User u")
    List<User> queryFindAllUsers();

    @Transactional(readOnly = true)
    @Query(value = "SELECT u FROM User u WHERE u.firstName = :firstName")
    List<User> queryFindByFirstNameSorted(@Param(value = "firstName") String firstName, Sort sort);
}
