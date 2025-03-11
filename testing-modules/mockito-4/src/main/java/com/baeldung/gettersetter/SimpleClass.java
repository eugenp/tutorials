package com.baeldung.gettersetter;

public class SimpleClass implements IdAndName {

    private Long id;

    private String name;

    public SimpleClass(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public SimpleClass() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
