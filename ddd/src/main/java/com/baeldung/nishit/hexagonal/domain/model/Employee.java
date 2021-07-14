package com.baeldung.nishit.hexagonal.domain.model;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String id;
    private String name;
    private int age;
}
