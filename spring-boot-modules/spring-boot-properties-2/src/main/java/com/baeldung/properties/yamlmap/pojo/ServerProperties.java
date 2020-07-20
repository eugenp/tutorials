package com.baeldung.properties.yamlmap.pojo;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "server")
public class ServerProperties {

    private Map<String, String> application;
    private Map<String, List<String>> config;
    private Map<String, Credential> users;

    public Map<String, String> getApplication() {
        return application;
    }

    public void setApplication(Map<String, String> application) {
        this.application = application;
    }

    public Map<String, List<String>> getConfig() {
        return config;
    }

    public void setConfig(Map<String, List<String>> config) {
        this.config = config;
    }

    public Map<String, Credential> getUsers() {
        return users;
    }

    public void setUsers(Map<String, Credential> users) {
        this.users = users;
    }

    public static class Credential {

        private String username;
        private String password;

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
}