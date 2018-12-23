package com.baeldung.examples.guice;

import com.baeldung.examples.common.Account;
import com.baeldung.examples.common.Address;
import com.google.inject.Inject;

public class GuiceUser {

    @Inject
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

    @Inject
    public void setAddress(Address address) {
        this.address = address;
        address.setCity("Default");
    }

}
