package com.baeldung.dataloaderbatchprocessing.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor
public class User {

    private final String id;
    private final String name;
}

