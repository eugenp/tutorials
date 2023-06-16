package com.baeldung.hibernate.immutable.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Immutable;

import jakarta.persistence.*;

@Entity
@Immutable
@Table(name = "events_generated")
public class EventGeneratedId {

    @Id
    @Column(name = "event_generated_id")
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

    public EventGeneratedId() {
    }

    public EventGeneratedId(String name, String description) {
        this.name = name;
        this.description = description;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}