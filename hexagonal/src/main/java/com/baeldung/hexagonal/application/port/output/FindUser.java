package com.baeldung.hexagonal.application.port.output;

import com.baeldung.hexagonal.domain.User;

public interface FindUser {
    User byName(String name);
}
