package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.db.SpringDataUserRepository;
import com.baeldung.hexagonal.db.entity.UserEntity;
import com.baeldung.hexagonal.domain.model.User;
import com.baeldung.hexagonal.domain.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DbUserRepository implements UserRepository {
    private final SpringDataUserRepository springDataUserRepository;

    @Override
    public User save(User user) {
        UserEntity userEntity = springDataUserRepository.save(mapUserToUserEntity(user));
        return mapUserEntityToUser(userEntity);
    }

    @Override
    public Optional<User> findById(long id) {
        return springDataUserRepository.findById(id).map(this::mapUserEntityToUser);
    }

    private UserEntity mapUserToUserEntity(User user) {
        return new UserEntity(user.getName(), user.getAge());
    }

    private User mapUserEntityToUser(UserEntity userEntity) {
        return new User(userEntity.getId(), userEntity.getName(), userEntity.getAge());
    }
}
