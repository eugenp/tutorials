package com.baeldung.pattern.hexagonal.architecture.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Address {
    private @Id @GeneratedValue Long id;
    private String name;
    private String house;
    private String street;
    private String city;
    private String zip;
}
