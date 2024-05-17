package com.baeldung.api.bulk;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserRepository {

    private final Map<String, User> userMap = new HashMap<>();

    public List<User> createUsers(List<User> users) {
        List<User> newUsers = new ArrayList<>();
        users.forEach(user -> {
            if (!userMap.containsKey(user.getEmail())) {
                User newUser = getUser(userMap.size()+1, user.getName(), user.getEmail());
                newUsers.add(newUser);
                userMap.put(newUser.getEmail(), newUser);
            }
        });

        return newUsers;
    }

    @PostConstruct
    private void setupRepo() {
        User user1 = getUser(1, "test_user1", "test_user1@mail.com");
        userMap.put("test_user1@mail.com", user1);

        User user2 = getUser(2, "test_user2", "test_user2@mail.com");
        userMap.put("test_user2@mail.com", user2);

        User user3 = getUser(3, "test_user3", "test_user3@mail.com");
        userMap.put("test_user3@mail.com", user3);

        User user4 = getUser(4, "test_user4", "test_user4@mail.com");
        userMap.put("test_user4@mail.com", user4);

        User user5 = getUser(5, "test_user5", "test_user5@mail.com");
        userMap.put("test_user5@mail.com", user5);
    }

    private static User getUser(int id, String name, String email) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        return user;
    }
}
