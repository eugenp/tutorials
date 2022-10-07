package com.baeldung.shallowDeepCopyExamples.pojo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Employee implements Serializable {
    private String firstName;

    private String lastName;

    private int age;

    private EmployerDetails employerDetails;
}
