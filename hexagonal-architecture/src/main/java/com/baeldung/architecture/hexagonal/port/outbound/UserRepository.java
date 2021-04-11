package com.baeldung.architecture.hexagonal.port.outbound;

import com.baeldung.architecture.hexagonal.db.entity.UserEntity;

import java.util.Optional;

public interface UserRepository {

    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> findById(Integer userId);

}
