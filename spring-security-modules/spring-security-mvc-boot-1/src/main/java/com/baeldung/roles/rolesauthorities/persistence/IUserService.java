package com.baeldung.roles.rolesauthorities.persistence;

import com.baeldung.roles.rolesauthorities.model.User;

public interface IUserService {

    User findUserByEmail(String email);

}
