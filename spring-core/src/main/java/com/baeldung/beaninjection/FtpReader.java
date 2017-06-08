package com.baeldung.beaninjection;

public class FtpReader {
    private String ftpHost = null;
    private Integer ftpPort = null;

    // Constructor with arguments
    public FtpReader(String host, Integer port) {
        this.ftpHost = host;
        this.ftpPort = port;
    }

    public String getFtpHost() {
        return ftpHost;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    public Integer getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(Integer ftpPort) {
        this.ftpPort = ftpPort;
    }
}
