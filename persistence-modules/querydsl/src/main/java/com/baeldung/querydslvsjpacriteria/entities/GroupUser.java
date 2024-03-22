package com.baeldung.querydslvsjpacriteria.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class GroupUser {

    @Id
    @GeneratedValue
    private Long id;

    private String login;

    @ManyToMany(mappedBy = "groupUsers", cascade = CascadeType.PERSIST)
    private Set<UserGroup> userGroups = new HashSet<>();

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "groupUser")
    private Set<Task> tasks = new HashSet<>(0);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public Set<Task> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }
}
