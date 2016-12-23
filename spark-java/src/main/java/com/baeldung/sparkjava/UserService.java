package com.baeldung.sparkjava;

import java.util.Collection;
import java.util.HashMap;

public interface UserService {
    public void addUser (User user) ;
    public Collection<User> getUsers () ;
    public User getUser (String id) ;
    public User editUser (String id, HashMap userArg) throws Exception;
    public void deleteUser (String id) ;
    public boolean userExist (String id);
}
