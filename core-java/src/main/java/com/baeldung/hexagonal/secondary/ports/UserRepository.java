package com.baeldung.hexagonal.secondary.ports;

import com.baeldung.hexagonal.data.User;

public interface UserRepository {

    public boolean create(User user);

    public boolean fetch(String uid, String password);

}
