package com.baeldung.persistence;

import javax.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {

    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    // Column definitions

    // Constructor

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}