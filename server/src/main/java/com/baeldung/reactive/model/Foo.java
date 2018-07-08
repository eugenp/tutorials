package com.baeldung.reactive.model;

import java.io.Serializable;

public class Foo implements Serializable{

    private long id;
    private String name;

    public Foo(long id, String name) {
        this.id = id;
        this.name = name;
    }

}
