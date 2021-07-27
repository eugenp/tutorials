package com.baeldung.hexagonal.inside.business;

import com.baeldung.hexagonal.inside.domain.User;
import com.baeldung.hexagonal.inside.port.inbound.UserServicePort;
import com.baeldung.hexagonal.inside.port.outbound.UserDaoPort;

import java.util.List;

public class UserServiceImpl implements UserServicePort {
    private final UserDaoPort userDao;

    public UserServiceImpl(UserDaoPort userDao) {
        this.userDao = userDao;
    }

    @Override
    public boolean addUser(String name) {
        if (name == null || "".equals(name)) return false;

        List<User> userList = userDao.getList();
        for (User user : userList) {
            if (name.equals(user.getName())) {
                return false;
            }
        }

        int maxUserId = 0;
        for (User user : userList) {
            if (user.getId() > maxUserId) {
                maxUserId = user.getId();
            }
        }

        int newUserId = maxUserId + 1;
        User newUser = new User(newUserId, name);
        userDao.addUser(newUser);
        return true;
    }

    @Override
    public List<User> getUsers() {
        return userDao.getList();
    }
}