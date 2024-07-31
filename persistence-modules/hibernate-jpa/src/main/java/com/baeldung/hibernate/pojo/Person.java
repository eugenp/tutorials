package com.baeldung.hibernate.pojo;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import com.baeldung.hibernate.converters.PersonNameConverter;

@Entity(name = "PersonTable")
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    @Convert(converter = PersonNameConverter.class)
    private PersonName personName;

    public PersonName getPersonName() {
        return personName;
    }

    public void setPersonName(PersonName personName) {
        this.personName = personName;
    }

    public Long getId() {
        return id;
    }

}
