package com.baeldung.model;

import java.util.Collection;

/**
 * @author sasam0320
 * @date 4/18/2020
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
