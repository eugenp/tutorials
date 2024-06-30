package com.baeldung.testcontainers.jdbc;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Wizard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long hogwardsId;

}
