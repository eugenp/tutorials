package com.baeldung.hexagonalarchitecture.serverside.mariadb;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataMariadbUserRepository extends CrudRepository<UserEntity, UUID> {
}
