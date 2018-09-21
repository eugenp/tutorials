package com.baeldung.definition.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Company {
    private Address address;
    private Account account;

    public Company(Address address, Account account) {
        this.address = address;
        this.account = account;
    }
}
