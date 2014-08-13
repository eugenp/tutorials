package org.baeldung.persistence.service;

import org.baeldung.persistence.model.User;

public interface UserService {

    public User registerNewUserAccount(UserDto userAccountData) throws EmailExistsException;
}
