package com.baeldung.spring.cloud.aws.sqs.introduction;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    private final Map<String, User> persistedUsers = new ConcurrentHashMap<>();

    public void save(User userToSave) {
        persistedUsers.put(userToSave.id(), userToSave);
    }

    public Optional<User> findById(String userId) {
        return Optional.ofNullable(persistedUsers.get(userId));
    }

    public Optional<User> findByName(String name) {
        return persistedUsers.values().stream()
            .filter(user -> user.name().equals(name))
            .findFirst();
    }

}
