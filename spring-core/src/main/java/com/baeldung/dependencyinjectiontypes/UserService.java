package com.baeldung.dependencyinjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getUsers() {
        return String.join(", ", userDao.getUsers());
    }

}
