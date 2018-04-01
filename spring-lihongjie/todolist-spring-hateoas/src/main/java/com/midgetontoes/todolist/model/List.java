package com.midgetontoes.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.core.Relation;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
@Relation(value = "list", collectionRelation = "lists")
public class List extends AbstractEntity {
    private String name;

    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "list", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<Item> items = new HashSet<>();

    protected List() {
    }

    public List(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public Set<Item> getItems() {
        return items;
    }
}
