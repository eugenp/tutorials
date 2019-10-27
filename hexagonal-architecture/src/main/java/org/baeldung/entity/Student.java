package org.baeldung.entity;

import javax.persistence.*;

@Entity
public class Student {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;
    @Column(name = "schoolFees")
    private double schoolFees;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "age")
    private Integer age;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public double getSchoolFees() {
        return schoolFees;
    }

    public void setSchoolFees(double schoolFees) {
        this.schoolFees = schoolFees;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
