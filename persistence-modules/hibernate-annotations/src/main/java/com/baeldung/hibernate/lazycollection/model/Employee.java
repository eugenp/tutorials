package com.baeldung.hibernate.lazycollection.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OrderColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    public Employee() {

    }

    public Employee(String name, Branch mainBranch, Branch subBranch, Branch additionalBranch) {
        this.name = name;
        this.mainBranch = mainBranch;
        this.subBranch = subBranch;
        this.additionalBranch = additionalBranch;
    }

    @ManyToOne
    private Branch mainBranch;

    @ManyToOne
    private Branch subBranch;

    @ManyToOne
    private Branch additionalBranch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Branch getMainBranch() {
        return mainBranch;
    }

    public void setMainBranch(Branch mainBranch) {
        this.mainBranch = mainBranch;
    }

    public Branch getSubBranch() {
        return subBranch;
    }

    public void setSubBranch(Branch subBranch) {
        this.subBranch = subBranch;
    }

    public Branch getAdditionalBranch() {
        return additionalBranch;
    }

    public void setAdditionalBranch(Branch additionalBranch) {
        this.additionalBranch = additionalBranch;
    }
}