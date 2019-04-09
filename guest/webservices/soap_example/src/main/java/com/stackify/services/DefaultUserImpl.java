package com.stackify.services;

import java.util.ArrayList;

import javax.jws.WebService;

import com.stackify.models.User;
import com.stackify.models.Users;

@WebService(endpointInterface = "com.stackify.services.UserService")
public class DefaultUserImpl implements UserService {

    ArrayList<User> usersList = new ArrayList<>();

    @Override
    public void addUser(User user) {
        usersList.add(user);
    }

    @Override
    public Users getUsers() {
        Users users = new Users();
        users.setUsers(usersList);
        return users;
    }

}
