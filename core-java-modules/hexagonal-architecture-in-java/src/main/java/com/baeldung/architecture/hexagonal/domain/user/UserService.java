package com.baeldung.architecture.hexagonal.domain.user;

import com.baeldung.architecture.hexagonal.port.UserCreationService;
import com.baeldung.architecture.hexagonal.port.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserCreationService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User createUser(String userId, String firstName, String lastName) {
        User user = new User(userId, firstName, lastName);
        userDao.createUser(user);
        return user;
    }
}
