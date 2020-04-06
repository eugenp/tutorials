package com.baeldung.javahexagonal.adapter.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baeldung.javahexagonal.core.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findUserByEmail(@Param("email") String email);

}
