package com.baeldung.architecture.hexagonal.user.core.ports.outgoing;

import com.baeldung.architecture.hexagonal.user.core.model.User;

public interface UserRepository {

    boolean save(User user);

    boolean delete(String userId);
}
