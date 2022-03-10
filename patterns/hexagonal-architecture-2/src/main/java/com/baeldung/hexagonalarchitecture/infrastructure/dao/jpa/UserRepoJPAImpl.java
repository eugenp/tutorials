package com.baeldung.hexagonalarchitecture.infrastructure.dao.jpa;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import com.baeldung.hexagonalarchitecture.domain.dao.UserRepository;
import com.baeldung.hexagonalarchitecture.domain.model.User;

import lombok.RequiredArgsConstructor;

@Primary
@Repository
@RequiredArgsConstructor
public class UserRepoJPAImpl implements UserRepository {

    private final UserRepositoryJPA userRepositoryJPA;

    @Override
    public String save(User user) {
        return userRepositoryJPA.save(new UserEntity(user))
            .getId();
    }

    @Override
    public User findById(String id) {
        return userRepositoryJPA.findById(id)
            .map(UserEntity::toUser)
            .orElse(null);
    }
}
