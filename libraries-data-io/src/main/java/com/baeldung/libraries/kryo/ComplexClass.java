package com.baeldung.libraries.kryo;

import java.io.Serializable;

public class ComplexClass implements Serializable{
    private static final long serialVersionUID = 123456L;
    private String name = "Bael";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
