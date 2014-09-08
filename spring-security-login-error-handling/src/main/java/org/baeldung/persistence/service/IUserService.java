package org.baeldung.persistence.service;

import org.baeldung.persistence.model.User;

public interface IUserService {

    public User registerNewUserAccount(UserDto userAccountData) throws EmailExistsException;

}
