package com.baeldung.springboothexagonal.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Vehicle {

    @Id
    private Long id;

    private String name;
}
