package com.baeldung.hibernate.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Department {
    @Id
    long id;
    String name;
    @OneToMany(mappedBy="department")
    List<Employee> employees;
}
