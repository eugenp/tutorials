package com.baeldung.debugging.server.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Foo {

    @Id
    private Long id;
    private String name;

}
