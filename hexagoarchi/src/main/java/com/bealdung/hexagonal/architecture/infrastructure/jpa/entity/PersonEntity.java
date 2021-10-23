package com.bealdung.hexagonal.architecture.infrastructure.jpa.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bealdung.hexagonal.architecture.domain.model.Person;

@Entity
@Table(name = "persons")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static PersonEntity fromPerson(Person person) {
        PersonEntity ePerson = new PersonEntity();
        ePerson.setFirstName(person.getFirstName());
        ePerson.setLastName(person.getLastName());
        return ePerson;
    }

    public Person toPerson() {
        Person person = new Person();
        person.setId(this.getId());
        person.setFirstName(this.getFirstName());
        person.setLastName(this.getLastName());
        return person;

    }

}