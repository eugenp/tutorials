package com.baeldung.reactive.model;

import lombok.AllArgsConstructor;
import lombok.Data;

//@AllArgsConstructor
@Data
public class Foo {

    private long id;
    private String name;

    public Foo() {
        // TODO Auto-generated constructor stub
    }

    public Foo(long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

}
