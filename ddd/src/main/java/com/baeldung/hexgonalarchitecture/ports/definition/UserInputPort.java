package com.baeldung.hexgonalarchitecture.ports.definition;

import com.baeldung.hexgonalarchitecture.entity.User;

public interface UserInputPort {
    User save(User user);
}
