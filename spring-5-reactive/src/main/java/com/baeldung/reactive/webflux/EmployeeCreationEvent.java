package com.baeldung.reactive.webflux;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeCreationEvent {
    private String employeeId;
    private String creationTime;
}
