package com.baeldung.architecture.hexagonal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity public class Book {

        @Id Long id;

        @Column String title;

        @Column Long isbn;

        //setters & getters

}
