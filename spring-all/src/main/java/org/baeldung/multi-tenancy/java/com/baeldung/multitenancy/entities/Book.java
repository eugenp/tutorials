package com.baeldung.multitenancy.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    Long id;

    @NotNull
    @Column(name = "name")
    String name;

    // standard getters and setters
}
