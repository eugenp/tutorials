package com.baeldung.querydslvsjpacriteria.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    private String description;

    @ManyToOne
    private GroupUser groupUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public GroupUser getUser() {
        return groupUser;
    }

    public void setUser(GroupUser groupUser) {
        this.groupUser = groupUser;
    }
}
