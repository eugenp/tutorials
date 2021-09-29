package com.baeldung.httpfirewall.service;

import com.baeldung.httpfirewall.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    /**
     * Creates a user. Checks if the user already exists and then persists the user
     *
     * @param user The user that is to be persisted into the store
     */
    void saveUser(User user);

    /**
     * Get a user. Returns a user
     *
     * @param userId The user identifier that has to be fetched form the repository
     */
    Optional<User> findById(String userId);

    /**
     * Fetch all the users in the store
     * @return A list of all the users
     */
    Optional<List<User>> findAll();

    /**
     * Delete the user with a given id
     */
    void deleteUser(String userId);
}
