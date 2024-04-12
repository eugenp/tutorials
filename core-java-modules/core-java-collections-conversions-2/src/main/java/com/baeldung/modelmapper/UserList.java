package com.baeldung.modelmapper;

import java.util.Collection;

/**
 * UserList class that contain collection of users
 *
 * @author Sasa Milenkovic
 */
public class UserList {

    public UserList() {}
    private Collection<User> users;

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }
}
