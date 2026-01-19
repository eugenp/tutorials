package com.baeldung.proxyauth;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.ProxySelector;

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

    public Authenticator authenticator() {
        if (!requiresAuth()) {
            return null;
        }
        return new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password.toCharArray());
            }
        };
    }

    public ProxySelector proxySelector() {
        return ProxySelector.of(new InetSocketAddress(host, port));
    }

    public Proxy proxy() {
        return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
    }
}
