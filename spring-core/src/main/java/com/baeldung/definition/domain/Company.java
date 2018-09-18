package com.baeldung.definition.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class Company {
    private Address address;
    private Account account;

    public Company(Address address) {
        this.address = address;
    }

    @Autowired
    public void setAccount(Account account) {
        this.account = account;
    }
}
