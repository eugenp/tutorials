package com.baeldung.spring.data.neo4j.model;

import org.neo4j.graphdb.Direction;
import org.neo4j.ogm.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@NodeEntity
public class Book {

    private static final AtomicLong TS = new AtomicLong();

    @GraphId
    private Long id;
    private String title;
    private int released;

    @Relationship(type="AUTHORED_BY", direction = Relationship.INCOMING)
    private Person person;

    public Book() {
        this.id = TS.getAndIncrement();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleased() {
        return released;
    }

    public void setReleased(int released) {
        this.released = released;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}