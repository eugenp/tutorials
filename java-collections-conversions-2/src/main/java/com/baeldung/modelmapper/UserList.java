package com.baeldung.modelmapper;

import java.util.Collection;

/**
 * UserList class that contain collection of users
 * @author sasam0320
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
