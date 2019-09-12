package com.baeldung.hibernate.H2.EntityMapToTwoTables.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="STUDENT")
@SecondaryTable(name="STUDENT_DETAILS")
public class StudentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;

    String firstName;
    String lastName;

    @Column(table = "STUDENT_DETAILS")
    String clgId;
    @Column(table = "STUDENT_DETAILS")
    String departmentName;

    public StudentEntity(int id, String firstName, String lastName, String clgId, String departmentName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.clgId = clgId;
        this.departmentName = departmentName;
    }

    public StudentEntity() {
    }

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

    public String getClgId() {
        return clgId;
    }

    public void setClgId(String clgId) {
        this.clgId = clgId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }


    @Override
    public String toString() {
        return "com.spring.boot.twoTableEntity.entity.StudentEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", clgId='" + clgId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }

}
