package com.baeldung.boot.naming.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Preference {

    @Id private Long id;

    private String name;

    @ManyToOne private Account account;

}
