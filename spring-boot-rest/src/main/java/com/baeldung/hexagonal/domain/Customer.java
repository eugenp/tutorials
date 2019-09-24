package com.baeldung.hexagonal.domain;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    public Customer(String name, String companyName) {
        this.name = name;
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
}
