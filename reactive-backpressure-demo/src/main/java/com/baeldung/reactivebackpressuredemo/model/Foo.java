package com.baeldung.reactivebackpressuredemo.model;

import java.io.Serializable;

public class Foo implements Serializable {

    private static final long serialVersionUID = -6840360001048791573L;

    private String id;
    private String name;

    public Foo() {

    }

    public Foo(String id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Foo [id=" + id + ", name=" + name + "]";
    }

}
