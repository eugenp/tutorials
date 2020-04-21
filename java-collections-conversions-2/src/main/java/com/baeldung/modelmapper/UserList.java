package com.baeldung.modelmapper;

import java.util.Collection;

/**
 * @author sasam0320
 * @description UserList class that contain collection of users
 */
public class UserList {

    private Collection<User> users;

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
