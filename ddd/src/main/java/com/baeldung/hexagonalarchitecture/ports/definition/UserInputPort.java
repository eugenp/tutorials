package com.baeldung.hexagonalarchitecture.ports.definition;

import com.baeldung.hexagonalarchitecture.entity.User;

public interface UserInputPort {
    User save(User user);
}
