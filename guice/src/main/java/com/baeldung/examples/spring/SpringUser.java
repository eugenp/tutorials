package com.baeldung.examples.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baeldung.examples.common.Account;
import com.baeldung.examples.common.Address;

@Component
public class SpringUser {

    @Autowired
    private Account account;

    private Address address;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Address getAddress() {
        return address;
    }

    @Autowired
    public void setAddress(Address address) {
        this.address = address;
        address.setCity("Default");
    }

}
