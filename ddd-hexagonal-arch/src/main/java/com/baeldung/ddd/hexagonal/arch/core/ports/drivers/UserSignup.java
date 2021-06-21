package com.baeldung.ddd.hexagonal.arch.core.ports.drivers;

import com.baeldung.ddd.hexagonal.arch.core.domain.User;

public interface UserSignup {
    void signup(User user);
}
