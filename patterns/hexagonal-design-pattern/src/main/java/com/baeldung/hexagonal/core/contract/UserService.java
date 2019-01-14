package com.baeldung.hexagonal.core.contract;

import java.util.List;

public interface UserService {

    void addUser(User user);

    List<User> getUsers();
}
