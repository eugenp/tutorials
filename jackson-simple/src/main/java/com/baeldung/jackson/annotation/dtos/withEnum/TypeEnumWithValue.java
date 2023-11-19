package com.baeldung.jackson.annotation.dtos.withEnum;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TypeEnumWithValue {
    TYPE1(1, "Type A"), TYPE2(2, "Type 2");

    private Integer id;

    @JsonValue
    private String name;


    TypeEnumWithValue(int id, String name) {
        this.id = id;
        this.name = name;
    }

    //@JsonValue
    public String getName() {
        return name;
    }
}
