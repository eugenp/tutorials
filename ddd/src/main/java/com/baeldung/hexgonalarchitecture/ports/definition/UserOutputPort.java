package com.baeldung.hexgonalarchitecture.ports.definition;

import com.baeldung.hexgonalarchitecture.entity.User;

public interface UserOutputPort {
    User save(User user);
}
