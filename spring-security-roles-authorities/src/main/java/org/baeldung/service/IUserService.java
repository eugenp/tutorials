package org.baeldung.service;

import org.baeldung.persistence.model.User;

public interface IUserService {

    User findUserByEmail(String email);

}
