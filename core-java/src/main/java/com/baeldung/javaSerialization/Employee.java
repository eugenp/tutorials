package com.baeldung.javaSerialization;

import java.io.Serializable;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1234343434343434L;
    String id;
    String name;

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

}
