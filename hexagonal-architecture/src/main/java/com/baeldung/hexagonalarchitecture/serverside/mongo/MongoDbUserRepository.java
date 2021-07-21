package com.baeldung.hexagonalarchitecture.serverside.mongo;

import com.baeldung.hexagonalarchitecture.businesslogic.dto.User;
import com.baeldung.hexagonalarchitecture.businesslogic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
//@Primary
public class MongoDbUserRepository implements UserRepository {

    private final SpringDataMongoUserRepository userRepository;

    @Autowired
    public MongoDbUserRepository(final SpringDataMongoUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(new MongoUser(user));
    }

    @Override
    public User findUser(UUID uuid) {
        Optional<MongoUser> byId = userRepository.findById(uuid);
        return byId.map(MongoUser::toUser).orElse(null);
    }

    @Override
    public void delete(UUID uuid) {
        userRepository.deleteById(uuid);
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
