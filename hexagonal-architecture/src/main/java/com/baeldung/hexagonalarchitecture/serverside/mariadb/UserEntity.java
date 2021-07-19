package com.baeldung.hexagonalarchitecture.serverside.mariadb;

import com.baeldung.hexagonalarchitecture.businesslogic.dto.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "tbl_user")
public class UserEntity {
    @Id
    @Column(name = "id", nullable = false, columnDefinition = "CHAR(36) CHARACTER SET ascii")
    private UUID id;

    private String name;
    private Integer age;
    private boolean active;

    public UserEntity(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
        this.active = user.isActive();
    }

    public UserEntity() {

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

    public User toUser() {
        return new User(this.id, this.name, this.age, this.active);
    }
}
