package services;

import models.User;

import java.util.List;

public interface UserService {

    void saveUser(User user);
    List<User> getUsers();
}
