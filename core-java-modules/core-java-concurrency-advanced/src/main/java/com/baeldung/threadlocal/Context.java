package com.baeldung.threadlocal;


public class Context {
    private final String userName;

    Context(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Context{" +
          "userNameSecret='" + userName + '\'' +
          '}';
    }
}
