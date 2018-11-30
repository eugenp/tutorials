package com.baeldung.debugging.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FooDto {

    private Integer id;
    private String name;
}
