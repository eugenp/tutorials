package com.baeldung.apache.curator.modeled;

public class HostConfig {
    private String hostname;
    private int port;

    public HostConfig() {

    }

    public HostConfig(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
