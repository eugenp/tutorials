package com.baeldung.hexgonal.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {

    private Long id;
    private String firstName;
    private String lastName;
    private String code;

}
