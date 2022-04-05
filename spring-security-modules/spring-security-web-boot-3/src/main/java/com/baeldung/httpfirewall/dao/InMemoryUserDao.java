package com.baeldung.httpfirewall.dao;

import com.baeldung.httpfirewall.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryUserDao {
    private Map<String, User> map = new HashMap<>();

    /**
     * Persists the user. The user is store in an In-Memory store (a HashMap)
     * The default implementation is an In-Memory persistence
     * @param user The user that should be persisted
     */

    public void save(User user) {
        map.put(user.getId(), user);
    }

    /**
     * Finds the user from the in-memory data store.
     * The default implementation is an In-Memory persistence
     *
     * @param userId The ID of the user that has to be fetched
     * @return An optional of the requested user
     */
    public Optional<User> findById(String userId) {
        return Optional.ofNullable(map.get(userId));
    }

    /**
     * Finds all the users from the in-memory data store
     * The default implementation is an In-Memory persistence
     */

    public Optional<List<User>> findAll() {
        return Optional.of(new ArrayList<>(map.values()));
    }

    /**
     * Delete the user from the data store
     * The default implementation is an In-Memory persistence
     * @param userId The user that has to be deleted
     */

    public void delete(String userId) {
        map.remove(userId);
    }

    /**
     * Checks if the user exists
     * The default implementation is an In-Memory persistence
     * @param userId The user that has to be checked for
     */

    public boolean isExists(String userId) {
        return map.containsKey(userId);
    }
}
