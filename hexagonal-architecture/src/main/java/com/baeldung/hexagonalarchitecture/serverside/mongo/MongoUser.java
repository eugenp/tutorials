package com.baeldung.hexagonalarchitecture.serverside.mongo;

import com.baeldung.hexagonalarchitecture.businesslogic.dto.User;

import javax.persistence.Table;
import java.util.UUID;

@Table(name = "user")
public class MongoUser {
    private UUID id;
    private String name;
    private Integer age;
    private boolean active;

    public MongoUser(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
        this.active = user.isActive();
    }

    public MongoUser() {
    }

    public User toUser(){
        return new User(this.id, this.name, this.age, this.active);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
