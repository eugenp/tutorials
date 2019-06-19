package com.baeldung.hexagonalarchitecturejava;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class Book {
    @GeneratedValue
    Long id;

    String name;


}

