package com.baeldung.associations.biredirectional;

import jakarta.persistence.*;


@Entity
public class Employee {

    @Id
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
 
    //getters and setters
}
