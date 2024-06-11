package com.baeldung.jpa.savechildobjects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UnidirectionalChild {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
