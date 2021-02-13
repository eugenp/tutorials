package com.baeldung.pattern.cleanarchitecture.usercreation;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
class UserDataMapper {

    @Id
    String name;

    String password;

    LocalDateTime creationTime;

    public UserDataMapper() {
    }

    public UserDataMapper(String name, String password, LocalDateTime creationTime) {
        super();
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
