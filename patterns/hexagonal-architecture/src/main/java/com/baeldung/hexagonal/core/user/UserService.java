package com.baeldung.hexagonal.core.user;

public interface UserService {

    void createUser(String email, String name);

    User getUser(String email);

}
