package com.example.hexagonal.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * A user that interacts with the content
 * of movie hub
 */
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userid;
    private String name;
    private String location;
    private Date customerSince;

    protected Customer() {
    }

    public Customer(String name, String location) {
        this.name = name;
        this.location = location;
        this.customerSince = new Date();
    }

    public long getUserid() {
        return userid;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public Date getCustomerSince() {
        return customerSince;
    }
}
