package com.baeldung.lambda.todo.config;

public class Config {
    private String toDoEndpoint;
    private String postEndpoint;
    private String environmentName;

    private Credentials toDoCredentials;
    private Credentials postCredentials;

    public String getToDoEndpoint() {
        return toDoEndpoint;
    }

    public void setToDoEndpoint(String toDoEndpoint) {
        this.toDoEndpoint = toDoEndpoint;
    }

    public String getPostEndpoint() {
        return postEndpoint;
    }

    public void setPostEndpoint(String postEndpoint) {
        this.postEndpoint = postEndpoint;
    }

    public String getEnvironmentName() {
        return environmentName;
    }

    public void setEnvironmentName(String environmentName) {
        this.environmentName = environmentName;
    }

    public Credentials getToDoCredentials() {
        return toDoCredentials;
    }

    public void setToDoCredentials(Credentials toDoCredentials) {
        this.toDoCredentials = toDoCredentials;
    }

    public Credentials getPostCredentials() {
        return postCredentials;
    }

    public void setPostCredentials(Credentials postCredentials) {
        this.postCredentials = postCredentials;
    }
}
