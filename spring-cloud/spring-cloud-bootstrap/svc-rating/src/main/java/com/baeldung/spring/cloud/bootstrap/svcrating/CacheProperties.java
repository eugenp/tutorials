package com.baeldung.spring.cloud.bootstrap.svcrating;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="cache.redis")
public class CacheProperties {
public String hostName;
public int port;

public String getHostName() {
    return hostName;
}
public void setHostName(String hostName) {
    this.hostName = hostName;
}
public int getPort() {
    return port;
}
public void setPort(int port) {
    this.port = port;
}


}
