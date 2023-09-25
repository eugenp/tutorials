package com.baeldung.swagger2bootmvc.model;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public class Foo {
    private long id;

    @Schema(name = "name", type = "array", example = "[\"str1\", \"str2\", \"str3\"]")
    private List<String> name;

    public Foo() {
        super();
    }

    public Foo(final long id, final List<String> name) {
        super();

        this.id = id;
        this.name = name;
    }

    //

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(final List<String> name) {
        this.name = name;
    }

}