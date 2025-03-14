package com.baeldung.gettersetter;

public class SimpleClass {

    private Long id;

    private String name;

    public SimpleClass(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SimpleClass() {
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
