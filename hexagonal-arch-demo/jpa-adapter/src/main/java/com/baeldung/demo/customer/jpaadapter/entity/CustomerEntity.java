package com.baeldung.demo.customer.jpaadapter.entity;

@Entity
@Table(name = "CUSTOMER")
public class CustomerEntity {
    @Column(name = "PAIR_TIMESTAMP")
    public String firstName;
    @Column(name = "PAIR_TIMESTAMP")
    public String lastName;
    @Column(name = "PAIR_TIMESTAMP")
    public String zipCode;
    @Column(name = "PAIR_TIMESTAMP")
    public String emailId;
    @Column(name = "PAIR_TIMESTAMP")
    public String ssn;
}
