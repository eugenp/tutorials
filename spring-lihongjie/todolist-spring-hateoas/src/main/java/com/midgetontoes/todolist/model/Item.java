package com.midgetontoes.todolist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.core.Relation;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Relation(value = "item", collectionRelation = "items")
public class Item extends AbstractEntity {

    @Lob
    private String description;

    private Priority priority = Priority.MEDIUM;

    private boolean completed = false;

    private LocalDateTime completedAt;

    @ManyToOne
    @JsonIgnore
    private List list;

    protected Item() {
    }

    public Item(String description, List list) {
        this.description = description;
        this.list = list;
    }

    public Item(String description, Priority priority, List list) {
        this.description = description;
        this.priority = priority;
        this.list = list;
    }

    public void markAsCompleted() {
        this.completed = true;
        this.completedAt = LocalDateTime.now(ZoneId.of("UTC"));
    }

    public void markAsUncompleted() {
        this.completed = false;
        this.completedAt = null;
    }

    public String getDescription() {
        return description;
    }


    public Priority getPriority() {
        return priority;
    }


    public boolean isCompleted() {
        return completed;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public enum Priority {
        HIGH, MEDIUM, LOW
    }
}
