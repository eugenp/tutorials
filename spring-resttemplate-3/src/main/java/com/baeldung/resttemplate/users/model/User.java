package com.baeldung.resttemplate.users.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    public User() {
        super();
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("addressList")
    public List<Address> getAddressList() {
        return addressList;
    }

    @JsonProperty("addressList")
    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    @Override
    public String toString() {
        return "User{" +
                       "id=" + id +
                       ", name='" + name + '\'' +
                       ", addressList=" + addressList +
                       '}';
    }
}
