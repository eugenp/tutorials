package com.baeldung.deepshallowcopy.library;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    private String name;
    private String batch;
    private String department;
    private Address address;
}
