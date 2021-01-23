package com.baeldung.dddmodules.hexagonalarchitecture.ports;

import com.baeldung.dddmodules.hexagonalarchitecture.core.User;

public interface UserService {
    User createUser(User userToCreate);
}
