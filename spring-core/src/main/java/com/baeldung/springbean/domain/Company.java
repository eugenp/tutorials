package com.baeldung.springbean.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Company {
    private Address address;

    public Company(Address address) {
        this.address = address;
    }
}
