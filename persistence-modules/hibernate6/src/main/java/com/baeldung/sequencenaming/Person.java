package com.baeldung.sequencenaming;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
//@Table(name = "my_person_table")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_custom_seq")
//    @SequenceGenerator(name = "person_custom_seq", sequenceName = "person_custom_seq", allocationSize = 10)
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
