package org.baeldung.customannotation;

import java.io.Serializable;

public class Person implements Serializable {

    private static final long serialVersionUID = 9005331414216374586L;

    private Long id;
    private String name;

    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
