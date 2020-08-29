package com.baeldung.hexagonal.architecture.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

    private int id;
    private String name;
    private String city;
    private int age;

}
