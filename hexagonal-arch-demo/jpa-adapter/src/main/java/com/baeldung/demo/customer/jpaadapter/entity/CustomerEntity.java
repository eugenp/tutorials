package com.baeldung.demo.customer.jpaadapter.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity {
    @Column(name = "FIRST_NAME")
    public String firstName;
    @Column(name = "LAST_NAME")
    public String lastName;
    @Column(name = "ZIP")
    public String zipCode;
    @Column(name = "EMAIL")
    public String emailId;
    @Column(name = "SSN")
    public String ssn;
    @Id
    @Column(name = "CUSTOMER_ID", nullable = false)
    public String customerId;
}
