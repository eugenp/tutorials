package com.baeldung.naming.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Preference {

    @Id private Long id;

    private String name;

    @ManyToOne private Account account;

}
