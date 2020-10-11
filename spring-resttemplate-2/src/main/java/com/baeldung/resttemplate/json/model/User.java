package com.baeldung.resttemplate.json.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private int id;
    private String name;
    private List<Address> addressList;

    public User(int id, String name, List<Address> addressList) {
        this.id = id;
        this.name = name;
        this.addressList = addressList;
    }

    public User() { super(); }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) { this.id = id; }

    public void setName(String name) {
        this.name = name;
    }

    public List<Address> getAddressList() { return addressList; }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
