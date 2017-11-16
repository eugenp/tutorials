package com.baeldung.dependencyinjectiontypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserManager {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserManager.class);

    private UserDao userDao;

    public UserManager(UserDao userDao) {
        this.userDao = userDao;
    }

    public void printUsers() {
        LOGGER.info("Users: {}", userDao.getUsers());
    }

}
