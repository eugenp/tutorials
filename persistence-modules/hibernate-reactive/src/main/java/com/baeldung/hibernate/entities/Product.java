package com.baeldung.hibernate.entities;

@Entity
public class Product {
    @Id
    private Long id;
    private String name;
}