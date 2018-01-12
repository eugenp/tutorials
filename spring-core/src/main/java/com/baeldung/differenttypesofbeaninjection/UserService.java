package com.baeldung.differenttypesofbeaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {

    @Autowired
    private Address address;

    public String getUserAddress() {
        return address.getAddress();
    }
}
