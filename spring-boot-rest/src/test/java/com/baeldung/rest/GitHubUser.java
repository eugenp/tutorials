package com.baeldung.rest;

public class GitHubUser {

    private String login;

    public GitHubUser() {
        super();
    }

    // API

    public String getLogin() {
        return login;
    }

    public void setLogin(final String login) {
        this.login = login;
    }

}
