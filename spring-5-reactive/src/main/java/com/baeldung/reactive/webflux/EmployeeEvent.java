package com.baeldung.reactive.webflux;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEvent {

    private Employee employee;
    private Date date;
    
    // constructors, getters and setters
}
