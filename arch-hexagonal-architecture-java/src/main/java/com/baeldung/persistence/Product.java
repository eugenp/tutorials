package com.baeldung.persistence;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    // Column definitions

    // Constructor
    public Product() {

    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}