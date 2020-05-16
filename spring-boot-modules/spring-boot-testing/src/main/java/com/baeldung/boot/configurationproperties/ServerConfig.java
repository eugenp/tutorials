package com.baeldung.boot.configurationproperties;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "server")
public class ServerConfig {

    private Address address;
    private Map<String, String> resourcesPath;

    public static class Address {

        private String ip;

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Map<String, String> getResourcesPath() {
        return resourcesPath;
    }

    public void setResourcesPath(Map<String, String> resourcesPath) {
        this.resourcesPath = resourcesPath;
    }
}
