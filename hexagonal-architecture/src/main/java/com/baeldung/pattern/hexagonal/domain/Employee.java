package com.baeldung.pattern.hexagonal.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id")
    Integer id;

    @Column(name = "name")
    String name;

    @Column(name = "age")
    Integer age;

    @Column(name = "department")
    String department;

    public Employee() {
    }

    public Employee(Integer id, String name, Integer age, String department) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", age=" + age + ", department=" + department + "]";
    }

}
