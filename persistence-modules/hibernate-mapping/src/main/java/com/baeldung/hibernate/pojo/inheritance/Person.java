package com.baeldung.hibernate.pojo.inheritance;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Person {

    @Id
    private long personId;

    private String name;

    public Person() {
    }

    public Person(long personId, String name) {
        this.personId = personId;
        this.name = name;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
