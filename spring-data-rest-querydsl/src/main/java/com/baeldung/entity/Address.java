package com.baeldung.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) public class Address {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id", unique = true, nullable = false) private Long id;
    @Column private String address;
    @Column private String country;
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name = "address_id") @JsonBackReference private AddressAvailability
            addressAvailability;
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "user_id") @JsonBackReference private User user;

    public Address() {
    }

    public Address(String address, String country, AddressAvailability addressAvailability, User user) {
        this.id = id;
        this.address = address;
        this.country = country;
        this.addressAvailability = addressAvailability;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public AddressAvailability getAddressAvailability() {
        return addressAvailability;
    }

    public void setAddressAvailability(AddressAvailability addressAvailability) {
        this.addressAvailability = addressAvailability;
    }
}
