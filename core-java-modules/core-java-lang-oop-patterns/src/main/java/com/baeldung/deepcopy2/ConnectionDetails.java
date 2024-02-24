package com.baeldung.deepcopy2;

public class ConnectionDetails implements Cloneable {
    private String url;
    private String username;
    private String password;

    // constructors, getters and setters omitted

    public ConnectionDetails(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public ConnectionDetails(ConnectionDetails that) {
        url = that.url;
        username = that.username;
        password = that.getPassword();
    }

    @Override
    public ConnectionDetails clone() {
        try {
            return (ConnectionDetails) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}