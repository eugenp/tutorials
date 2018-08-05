package com.baeldung.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeEvent {

    private long id;
    private String action;

    // standard getters and setters
}
