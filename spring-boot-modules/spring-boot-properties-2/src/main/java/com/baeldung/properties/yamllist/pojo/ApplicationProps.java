package com.baeldung.properties.yamllist.pojo;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationProps {

    private List<Object> profiles;
    private List<List<String>> team;
    private List<User> users;

    public List<Object> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Object> profiles) {
        this.profiles = profiles;
    }

    public List<List<String>> getTeam() {
        return team;
    }

    public void setTeam(List<List<String>> team) {
        this.team = team;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public static class User {

        private String username;
        private String password;
        private List<String> roles;

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

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }

    }
}