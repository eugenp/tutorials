package com.baeldung.serializeentityid;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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
