package com.baeldung.associations.unidirectional;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Department {

    @Id
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private List<Employee> employees;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}


