package com.baeldung.optionalreturntype;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HandleOptionalTypeExample {
    static Map<String, User> usersByName = new HashMap();
    static {
        User user1 = new User();
        user1.setUserId(1l);
        user1.setFirstName("baeldung");
        usersByName.put("baeldung", user1);
    }

    public static void main(String[] args) {
        changeUserName("baeldung", "baeldung-new");
        changeUserName("user", "user-new");
    }

    public static void changeUserName(String oldFirstName, String newFirstName) {
        Optional<User> userOpt = findUserByName(oldFirstName);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setFirstName(newFirstName);

            System.out.println("user with name " + oldFirstName + " is changed to " + user.getFirstName());
        } else {
            // user is missing
            System.out.println("user with name " + oldFirstName + " is not found.");
        }
    }

    public static Optional<User> findUserByName(String name) {
        // look up the user in the database, the user object below could be null
        User user = usersByName.get(name);
        Optional<User> opt = Optional.ofNullable(user);

        return opt;
    }
}
