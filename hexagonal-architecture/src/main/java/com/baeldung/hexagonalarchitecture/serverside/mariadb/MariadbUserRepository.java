package com.baeldung.hexagonalarchitecture.serverside.mariadb;

import com.baeldung.hexagonalarchitecture.businesslogic.dto.User;
import com.baeldung.hexagonalarchitecture.businesslogic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@Primary
public class MariadbUserRepository implements UserRepository {

    private SpringDataMariadbUserRepository userRepository;

    @Autowired
    public MariadbUserRepository(SpringDataMariadbUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        UserEntity userEntity = new UserEntity(user);
        userRepository.save(userEntity);
    }

    @Override
    public User findUser(UUID uuid) {
        Optional<UserEntity> byId = userRepository.findById(uuid);
        return byId.map(UserEntity::toUser).orElse(null);
    }
}
