package com.baeldung.architecture.hexagonal.port;

import com.baeldung.architecture.hexagonal.domain.user.User;

public interface UserCreationService {

    User createUser(String userId, String firstName, String lastName);
}
