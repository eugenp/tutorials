package com.baeldung.hexagonal.outside.driven.mockdb;

import com.baeldung.hexagonal.inside.domain.User;

import java.util.ArrayList;
import java.util.List;

public class MockRepository {
    private static final List<User> userList = new ArrayList<>();

    static {
        User user1 = new User(1, "Lucy");
        User user2 = new User(2, "Lily");
        User user3 = new User(3, "Tom");

        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
    }

    public static void saveUser(User user) {
        userList.add(user);
    }

    public static List<User> getUserList() {
        return userList;
    }
}