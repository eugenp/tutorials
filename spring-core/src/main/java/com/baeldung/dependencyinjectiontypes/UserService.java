package com.baeldung.dependencyinjectiontypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void printUsers() {
        LOGGER.info("Users: {}", userDao.getUsers());
    }

}
