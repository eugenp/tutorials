package com.baeldung.jpa.defaultvalues;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity()
@Table(name = "users_entity")
public class UserEntity {
    @Id
    private Long id;

    @Column(name = "name")
    @ColumnDefault("'John Snow'")
    private String name;

    @Column(name = "age")
    @ColumnDefault("25")
    private Integer age;

    @ColumnDefault("false")
    private Boolean locked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @PrePersist
    public void prePersist() {
        if (name == null) {
            name = "John Snow";
        }
        if (age == null) {
            age = 25;
        }
        if (locked == null) {
            locked = false;
        }
    }
}