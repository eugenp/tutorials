package com.baeldung.spring.data.jpa.joinquery.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
public class Customer {
    public Customer(){}
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "customer"
    , cascade = CascadeType.ALL)
    private Set<CustomerOrder> customerOrders;

    public Customer(Long id, Set<CustomerOrder> customerOrders, String email, LocalDate dob, String name) {
        this.id = id;
        this.customerOrders = customerOrders;
        this.email = email;
        this.dob = dob;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerOrders(Set<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }

    @Override
    public String toString() {
        return "Patient{" +
          "id=" + id +
          ", orders=" + customerOrders +
          ", email='" + email + '\'' +
          ", dob=" + dob +
          ", name='" + name + '\'' +
          '}';
    }

    public Set<CustomerOrder> getOrders() {
        return customerOrders;
    }

    public void setOrders(Set<CustomerOrder> orders) {
        this.customerOrders = orders;
    }

    @Column
    private String email;

    @Column(name = "dob", columnDefinition = "DATE")
    private LocalDate dob;

    @Column
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer patient = (Customer) o;
        return Objects.equals(id, patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
