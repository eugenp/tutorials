package com.baeldung.shallowvsdeepcopy;

public class App {

    private String loggedInUser;

    public App(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }
}
