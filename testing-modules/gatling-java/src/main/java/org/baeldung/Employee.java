package org.baeldung;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {
    private String empName;
    private Address address;
    private String id;
    private Set<String> projects;

}
