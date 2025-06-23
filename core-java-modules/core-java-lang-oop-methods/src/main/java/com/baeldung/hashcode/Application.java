package com.baeldung.hashcode;

import java.util.HashMap;
import java.util.Map;

import com.baeldung.hashcode.standard.User;

public class Application {

    public static void main(String[] args) {
        Map<User, User> users = new HashMap<>();
        User user1 = new User(1L, "John", "john@domain.com");
        User user2 = new User(2L, "Jennifer", "jennifer@domain.com");
        User user3 = new User(3L, "Mary", "mary@domain.com");

        users.put(user1, user1);
        users.put(user2, user2);
        users.put(user3, user3);
        if (users.containsKey(user1)) {
            System.out.print("User found in the collection");
        }
    }
}