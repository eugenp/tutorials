package org.baeldung.demo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Foo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    public Foo() {
    }

    public Foo(String name) {
        this.name = name;
    }

    public Foo(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
