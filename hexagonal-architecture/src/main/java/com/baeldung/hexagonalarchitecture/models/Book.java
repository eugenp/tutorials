package com.baeldung.hexagonalarchitecture.models;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "sequenceGenerator", initialValue = 1, allocationSize = 1)
    @GeneratedValue(generator = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

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
}
