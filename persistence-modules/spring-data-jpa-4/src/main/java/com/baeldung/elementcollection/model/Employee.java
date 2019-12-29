package com.baeldung.elementcollection.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Employee {
    @Id
    private int id;
    private String name;
    @ElementCollection
    @CollectionTable(
            name = "employee_phone",
            joinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Phone> phones;

    public Employee() {
    }

    public Employee(int id) {
        this.id = id;
    }

    public Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        Employee user = (Employee) o;
        return getId() == user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
