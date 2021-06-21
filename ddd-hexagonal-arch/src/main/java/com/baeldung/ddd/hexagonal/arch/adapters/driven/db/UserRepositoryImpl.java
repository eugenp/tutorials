package com.baeldung.ddd.hexagonal.arch.adapters.driven.db;

import com.baeldung.ddd.hexagonal.arch.core.domain.User;
import com.baeldung.ddd.hexagonal.arch.core.ports.driven.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryImpl implements UserRepository {
    private UserDBRepository userDBRepository;

    public UserRepositoryImpl(UserDBRepository userDBRepository) {
        this.userDBRepository = userDBRepository;
    }

    @Override
    public void save(User user) {
        this.userDBRepository.save(mapToEntity(user));
    }

    @Override
    public void activate(User user) {
        this.userDBRepository.save(mapToEntity(user));
    }

    @Override
    public User findByEmail(String email) {
        UserEntity userEntity = this.userDBRepository.findByEmail(email);
        return mapToUser(userEntity);
    }

    private UserEntity mapToEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setIsActive(user.isActive());
        return userEntity;
    }

    private User mapToUser(UserEntity userEntity) {
        return new User(
                userEntity.getFirstName(),
                userEntity.getLastName(), userEntity.getEmail(),
                userEntity.getIsActive()
        );
    }
}
