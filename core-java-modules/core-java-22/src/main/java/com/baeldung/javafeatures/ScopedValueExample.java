package com.baeldung.javafeatures;

import java.util.Random;

public class ScopedValueExample {
    public final static ScopedValue<User> LOGGED_IN_USER = ScopedValue.newInstance();

    public void serve(Request request) {
        User loggedInUser = authenticateUser(request);
        ScopedValue.where(LOGGED_IN_USER, loggedInUser)
          .run(() -> processRequest(request));
    }

    private User authenticateUser(Request request) {
        return new User(new Random().nextInt(100), STR."User\{new Random().nextInt()}");
    }

    private void processRequest(Request request) {
        System.out.println("Processing request :: " + ScopedValueExample.LOGGED_IN_USER.get()
          .toString());
    }
}

class User {
    private int id;
    private String userName;

    public User(int id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String toString() {
        return STR."User :: \{this.id}";
    }
}

class Request {

}
