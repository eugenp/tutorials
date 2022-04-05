package com.baeldung.di.aspectj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class PersonObject {
    @Autowired
    private IdService idService;

    private int id;
    private String name;

    public PersonObject(String name) {
        this.name = name;
    }

    void generateId() {
        this.id = idService.generateId();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}