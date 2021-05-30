package com.shaheen.hexa.database.h2.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
public class PersonEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String firstName;
    private Integer age;

    public PersonEntity(final String name, final String firstName, final Integer age) {
        this.name = name;
        this.firstName = firstName;
        this.age = age;
    }
}
