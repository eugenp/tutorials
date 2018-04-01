package com.baeldung.beaninjectiontypes.constructorinjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class User {

    private Address address;

    @Autowired
    public User(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return String.format("User Address: %s", address);
    }

}
