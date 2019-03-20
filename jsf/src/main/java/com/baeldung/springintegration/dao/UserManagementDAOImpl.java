package com.baeldung.springintegration.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

@Repository
public class UserManagementDAOImpl implements UserManagementDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserManagementDAOImpl.class);

    private List<String> users;

    @PostConstruct
    public void initUserList() {
        users = new ArrayList<>();
    }

    @Override
    public boolean createUser(String newUserData) {
        if (newUserData != null) {
            users.add(newUserData);
            LOGGER.info("User {} successfully created", newUserData);
            return true;
        } else {
            return false;
        }
    }

    public UserManagementDAOImpl() {
    }

}
