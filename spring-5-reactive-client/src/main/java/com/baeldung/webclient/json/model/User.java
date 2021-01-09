package com.baeldung.webclient.json.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private final int id;
    private final String name;
    private final List<Address> addressList;

    @JsonCreator
    public User(
            @JsonProperty("id") int id,
            @JsonProperty("name") String name,
            @JsonProperty("addressList") List<Address> addressList) {
        this.id = id;
        this.name = name;
        this.addressList = addressList;
    }

    public String getName() {
        return name;
    }

    public List<Address> getAddressList() { return addressList; }
}
