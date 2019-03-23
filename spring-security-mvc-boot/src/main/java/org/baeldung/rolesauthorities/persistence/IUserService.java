package org.baeldung.rolesauthorities.persistence;

import org.baeldung.rolesauthorities.model.User;

public interface IUserService {

    User findUserByEmail(String email);

}
