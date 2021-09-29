package com.baeldung.httpfirewall.dao;

import com.baeldung.httpfirewall.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao {

    /**
     * Persists the user. The user store depends on the injected implementation.
     * The default implementation is an In-Memory persistence
     * @param user The user that should be persisted
     */
    void save(User user);

    /**
     * Finds the user from the  data store
     * The default implementation is an In-Memory persistence
     * @param userId The Id of the user that has to be fetched
     */
    Optional<User> findById(String userId);

    /**
     * Finds all the users from the data store
     * The default implementation is an In-Memory persistence
     */
    Optional<List<User>> findAll();

    /**
     * Delete the user from the data store
     * The default implementation is an In-Memory persistence
     * @param userId The user that is to be deleted
     */
    void delete(String userId);

    /**
     * Checks if the user exists
     * The default implementation is an In-Memory persistence
     * @param userId The user that has to be checked for
     */
    boolean isExists(String userId);
}
