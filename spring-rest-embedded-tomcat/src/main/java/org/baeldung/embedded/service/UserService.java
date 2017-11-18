package org.baeldung.embedded.service;

import java.util.ArrayList;
import java.util.List;

import org.baeldung.embedded.domain.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private List<User> users = new ArrayList<>();

    public void addUser(String name) {
        User user = new User();
        user.setName(name);
        if (name == "HarryPotter") {
            user.setHobby("Quidditch");
        } else {
            user.setHobby("MuggleActivity");
        }
        users.add(user);
    }

    public User getUser(String name) {
        for (User user : users) {
            if (user.getName()
                .equalsIgnoreCase(name)) {
                return user;
            }
        }

        User user = new User();
        user.setName(name);
        user.setHobby("None");

        return user;
    }
}
