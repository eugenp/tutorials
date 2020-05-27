package com.baeldung.customerservice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {

    private int id;
    private String firstName;
    private String lastName;

}