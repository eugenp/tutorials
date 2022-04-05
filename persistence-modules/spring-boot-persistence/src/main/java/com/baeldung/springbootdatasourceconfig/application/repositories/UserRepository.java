package com.baeldung.springbootdatasourceconfig.application.repositories;

import com.baeldung.springbootdatasourceconfig.application.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {}
