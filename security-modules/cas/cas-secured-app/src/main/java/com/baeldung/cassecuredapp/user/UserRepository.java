package com.baeldung.cassecuredapp.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.baeldung.cassecuredapp.user.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(@Param("email") String email);

}
