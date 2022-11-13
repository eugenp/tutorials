package com.baeldung.formlogin.model;

import java.io.Serializable;

public class FooDTO implements Serializable {

    private static final long serialVersionUID = -5422285893276747592L;

    private long id;
    private String name;

    public FooDTO(final String name) {
        this.name = name;
    }

    public FooDTO() {
    }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final FooDTO other = (FooDTO) obj;
        if (name == null) {
            return other.name == null;
        } else return name.equals(other.name);
    }

    @Override
    public String toString() {
        return "Foo [name=" + name + "]";
    }

}
