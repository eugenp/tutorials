package com.baeldung.hexagonalarchitecture.dao;

import com.baeldung.hexagonalarchitecture.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
}
