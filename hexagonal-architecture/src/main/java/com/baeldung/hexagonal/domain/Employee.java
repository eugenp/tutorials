package com.baeldung.hexagonal.domain;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {
    private Long id;
    private String name;
    private String family;

    // getters and setters
}