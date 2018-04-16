package com.baeldung.easymock;

import java.util.List;

public interface IUserService {
    public boolean addUser(User user);
    public List<User> findByFirstName(String firstName);
    public List<User> findByLastName(String lastName);
    public List<User> findByAge(double age);
    public List<User> findByEmail(String email);
}
