package com.baeldung.deepcopy2;

import java.io.Serializable;

public class Customer implements Serializable {

    public String username;
    public String email;

    public Customer(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
