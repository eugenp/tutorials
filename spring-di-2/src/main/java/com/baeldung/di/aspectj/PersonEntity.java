package com.baeldung.di.aspectj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Configurable(preConstruction = true)
public class PersonEntity {
    @Autowired
    @Transient
    private IdService idService;

    @Id
    private int id;
    private String name;

    public PersonEntity() {
    }

    public PersonEntity(String name) {
        id = idService.generateId();
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
