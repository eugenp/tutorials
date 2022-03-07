package com.baeldung.pattern.hexagonal2.actor.secondary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.baeldung.pattern.hexagonal2.domain.model.User;

@Component
public class Database {
    private Map<UUID, User> data = new HashMap<>();

    public User addDataRecord(User user) {
        UUID id = UUID.randomUUID();
        user.setId(id);
        this.data.put(user.getId(), user);
        return user;
    }

    public List<User> getDataRecords() {
        return this.data.values()
            .stream()
            .collect(Collectors.toList());
    }

    public User deleteDataRecord(UUID id) {
        return this.data.remove(id);
    }
}
