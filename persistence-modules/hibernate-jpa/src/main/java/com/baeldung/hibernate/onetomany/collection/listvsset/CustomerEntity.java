package com.baeldung.hibernate.onetomany.collection.listvsset;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderEntity> orderList = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    private List<AddressEntity> addressList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    private Set<OrderEntity> orderSet = new HashSet<>();


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

    public List<OrderEntity> getOrderList() {
        return orderList;
    }

    public List<AddressEntity> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressEntity> addressList) {
        this.addressList = addressList;
    }

    public void setOrderList(List<OrderEntity> orderList) {
        this.orderList = orderList;
    }

    public Set<OrderEntity> getOrderSet() {
        return orderSet;
    }

    public void setOrderSet(Set<OrderEntity> orderSet) {
        this.orderSet = orderSet;
    }
}
