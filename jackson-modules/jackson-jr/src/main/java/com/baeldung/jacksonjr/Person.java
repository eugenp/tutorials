package com.baeldung.jacksonjr;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @JsonProperty("person_name")
    private String name;
    private int age;
    private LocalDate birthDate;
}
