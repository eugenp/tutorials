package com.baeldung.registrypostprocessor.bean;

public class ApiClient {
    private String name;
    private String url;
    private String key;

    public ApiClient(String name, String url, String key) {
        this.name = name;
        this.url = url;
        this.key = key;
    }

    public ApiClient() {
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getKey() {
        return key;
    }

    public String getConnectionProperties() {
        return "Connecting to " + name + " at " + url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setKey(String key) {
        this.key = key;
    }

}