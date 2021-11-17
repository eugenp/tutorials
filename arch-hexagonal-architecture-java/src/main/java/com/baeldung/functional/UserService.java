package com.baeldung.functional;

import com.baeldung.persistence.User;

public interface UserService {
    User addUser(String username, String firstName, String lastName);
}
