package com.baeldung.jackson.dtos.withEnum;

public enum TypeEnumSimple {
    TYPE1(1, "Type A"), TYPE2(2, "Type 2");

    private Integer id;
    private String name;

    private TypeEnumSimple(final Integer id, final String name) {
        this.id = id;
        this.name = name;
    }

    // API

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}
