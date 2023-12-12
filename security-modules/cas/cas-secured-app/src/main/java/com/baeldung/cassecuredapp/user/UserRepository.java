package com.baeldung.cassecuredapp.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<CasUser, Long> {

    CasUser findByEmail(@Param("email") String email);

}
