package com.baeldung.sparkjava;

import java.util.Collection;

public interface UserService {
    public void addUser(User user);

    public Collection<User> getUsers();

    public User getUser(String id);

    public User editUser(User user) throws UserException;

    public void deleteUser(String id);

    public boolean userExist(String id);
}
