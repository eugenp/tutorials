package com.baeldung.proxyauth;

public class ProxyConfig {
    private String host;
    private int port;
    private String username;
    private String password;

    public ProxyConfig(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public ProxyConfig(String host, int port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean requiresAuth() {
        return username != null && password != null;
    }
}
