package com.baeldung.hexagonalarchitecture.ports.definition;

import com.baeldung.hexagonalarchitecture.entity.User;

public interface UserOutputPort {
    User save(User user);
}
