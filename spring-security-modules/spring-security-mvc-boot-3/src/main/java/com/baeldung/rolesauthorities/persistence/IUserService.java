package com.baeldung.rolesauthorities.persistence;

import com.baeldung.rolesauthorities.model.User;

public interface IUserService {

    User findUserByEmail(String email);

}
