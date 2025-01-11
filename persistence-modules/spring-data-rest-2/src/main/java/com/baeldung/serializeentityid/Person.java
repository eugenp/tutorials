package com.baeldung.serializeentityid;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Person {

    /**
     * This doesn't work without a projection
     */
    //@JsonProperty
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
