package com.baeldung.associations.biredirectional;

import java.util.List;
import jakarta.persistence.*;


@Entity
public class Department {

    @Id
    private Long id;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

      public List<Employee> getEmployees() {
        return employees;
    }
 

}
