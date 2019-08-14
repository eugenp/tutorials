package com.baeldung.domain;

import lombok.Data;

@Data
public class Friend {
    private Long id;
    private String name;
    private Integer age;
    private Byte sex;
    // standard setters and getters
}
