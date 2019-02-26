package com.baeldung.sampleapp.web.dto;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Foo")
public class Foo {
    private long id;
    private String name;

    public Foo() {
        super();
    }

    public Foo(final String name) {
        super();

        this.name = name;
    }

    public Foo(final long id, final String name) {
        super();

        this.id = id;
        this.name = name;
    }

    // API

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}