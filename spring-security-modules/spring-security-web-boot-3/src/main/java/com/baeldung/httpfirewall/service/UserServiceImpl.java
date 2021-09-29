package com.baeldung.httpfirewall.service;


import com.baeldung.httpfirewall.dao.UserDao;
import com.baeldung.httpfirewall.exception.DuplicateUserException;
import com.baeldung.httpfirewall.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * Creates a user. Checks if the user already exists and then persists the user
     * @param user The user that is to be persisted into the store
     */
    @Override
    public void saveUser(User user) {
        if (userDao.isExists(user.getId()))
            throw new DuplicateUserException("User already exists");
        userDao.save(user);
    }

    /**
     * Get a user. Returns a user
     *
     * @param userId The user that has to be fetched form the repository
     */
    public Optional<User> findById(String userId) {
        return userDao.findById(userId);
    }

    /**
     * Fetch all the users in the store
     * @return A list of all the users
     */
    public Optional<List<User>> findAll() {
        return userDao.findAll();
    }

    /**
     * Delete the user with a given id
     * @param userId The identifier of the user
     */
    @Override
    public void deleteUser(String userId) {
        userDao.delete(userId);
    }
}
