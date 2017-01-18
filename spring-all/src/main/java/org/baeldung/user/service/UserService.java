package org.baeldung.user.service;

import org.baeldung.user.model.MyUser;

public interface UserService {

    /**
     * Method to fetch user object based on user name
     *
     * @param userName
     * @return MyUser
     */
    MyUser getUserByUsername(final String userName);
}
