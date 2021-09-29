package com.baeldung.httpfirewall.service;

import com.baeldung.httpfirewall.dao.InMemoryUserDao;
import com.baeldung.httpfirewall.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {

    private final InMemoryUserDao inMemoryUserDao;

    public UserServiceImpl(InMemoryUserDao inMemoryUserDao) {
        this.inMemoryUserDao = inMemoryUserDao;
    }

    /**
     * Creates a user. Checks if the user already exists and then persists the user
     * @param user The user that is to be persisted into the store
     */
    public void saveUser(User user) {
        inMemoryUserDao.save(user);
    }

    /**
     * Get a user. Returns a user
     *
     * @param userId The user that has to be fetched form the repository
     */
    public Optional<User> findById(String userId) {
        return inMemoryUserDao.findById(userId);
    }

    /**
     * Fetch all the users in the store
     * @return A list of all the users
     */
    public Optional<List<User>> findAll() {
        return inMemoryUserDao.findAll();
    }

    /**
     * Delete the user with a given id
     * @param userId The identifier of the user
     */
    public void deleteUser(String userId) {
        inMemoryUserDao.delete(userId);
    }
}
