package com.baeldung.entity;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity public class Person {

    @Id private String id;
    private String name;
    @OneToOne @JoinColumn(name = "address") private com.baeldung.entity.Address address;

    public Person() {
    }

    public Person(String id, String name, com.baeldung.entity.Address address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public com.baeldung.entity.Address getAddress() {
        return address;
    }

    public void setAddress(com.baeldung.entity.Address address) {
        this.address = address;
    }
}
