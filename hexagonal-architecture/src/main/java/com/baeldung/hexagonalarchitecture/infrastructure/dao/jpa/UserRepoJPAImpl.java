package com.baeldung.hexagonalarchitecture.infrastructure.dao.jpa;

import com.baeldung.hexagonalarchitecture.domain.User;
import com.baeldung.hexagonalarchitecture.domain.dao.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserRepositoryJPA userRepositoryJPA;

    @Override
    public void save(User user) {
        userRepositoryJPA.save(new UserEntity(user));
    }
}
