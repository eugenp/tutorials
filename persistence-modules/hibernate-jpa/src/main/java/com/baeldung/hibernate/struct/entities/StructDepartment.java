package com.baeldung.hibernate.struct.entities;

import jakarta.persistence.*;

@Entity
public class StructDepartment {
    @Id
    @GeneratedValue
    private Integer id;

    @Column
    private String departmentName;

    @Embedded
    @Column
    private StructManager manager;

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", departmentName='" + departmentName + '\'' +
                ", manager=" + manager +
                '}';
    }

    public StructDepartment(String departmentName, StructManager manager) {
        this.departmentName = departmentName;
        this.manager = manager;
    }

    public Integer getId() {
        return id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public StructManager getManager() {
        return manager;
    }

    public void setManager(StructManager manager) {
        this.manager = manager;
    }
}