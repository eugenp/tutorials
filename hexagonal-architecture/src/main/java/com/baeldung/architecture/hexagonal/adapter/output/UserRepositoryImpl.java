package com.baeldung.architecture.hexagonal.adapter.output;

import com.baeldung.architecture.hexagonal.db.entity.UserEntity;
import com.baeldung.architecture.hexagonal.db.jpa.UserDAO;
import com.baeldung.architecture.hexagonal.port.outbound.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserDAO userDAO;

    public UserRepositoryImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserEntity save(UserEntity userEntity) {
        return userDAO.save(userEntity);
    }

    @Override
    public Optional<UserEntity> findById(Integer userId) {
        return userDAO.findById(userId);
    }
}
