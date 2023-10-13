package com.baeldung.system;

public class EnvironmentExample {
    public void getUserName() {
        String username = System.getenv("USERNAME");
        System.out.println("User: " + username);
    }

}
