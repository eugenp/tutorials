package com.baeldung.injection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //Field injection
    //@Autowired
    private UserDAO userDAO;

    public UserService() {
    }

    //Constructor injection
    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    //Setter injection
    // @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    @Override
    public String toString() {
        return "UserService{" +
                "userDAO=" + userDAO +
                '}';
    }
}
