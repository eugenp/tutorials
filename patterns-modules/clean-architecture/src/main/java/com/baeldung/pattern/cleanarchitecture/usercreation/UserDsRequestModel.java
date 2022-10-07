package com.baeldung.pattern.cleanarchitecture.usercreation;

import java.time.LocalDateTime;

class UserDsRequestModel {

    String name;
    String password;
    LocalDateTime creationTime;

    public UserDsRequestModel(String name, String password, LocalDateTime creationTime) {
        this.name = name;
        this.password = password;
        this.creationTime = creationTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

}
