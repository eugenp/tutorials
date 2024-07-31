package com.baeldung.quarkus.testcontainers;

import java.util.Set;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

@Entity
public class Customer extends PanacheEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<Order> orders;

}
