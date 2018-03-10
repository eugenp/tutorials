package com.baeldung.googlehttpclientguide;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

public class User extends GenericJson {
    @Key
    private String login;
    @Key
    private long id;
    @Key
    private String url;
    @Key
    private String company;
    @Key
    private String blog;
    @Key
    private String email;

    @Key("subscriptions_url")
    private String subscriptionsUrl;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "login=" + login + ", id=" + id + ", url=" + url + ", company=" + company + ", blog=" + blog + ", email=" + email + '}';
    }

}
