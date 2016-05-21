/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baeldung.springintegration.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

/**
 * @author Tayo
 */
public class UserManagementDAOImpl extends IUserManagementDAO {

    private List<String> users;

    @PostConstruct
    public void initUserList() {
        users = new ArrayList<String>();
    }

    @Override
    public boolean createUser(String newUserData) {
        if (newUserData != null) {
            users.add(newUserData);
            Logger.getAnonymousLogger().log(Level.INFO, "User {0} successfully created", newUserData);
            return true;
        } else {
            return false;
        }
    }

    public UserManagementDAOImpl() {
    }


}
