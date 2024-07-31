package com.baeldung.jackson.map;

import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonValue;

public class Fruit {
    public String variety;

    @JsonKey
    public String name;

    public Fruit(String variety, String name) {
        this.variety = variety;
        this.name = name;
    }

    @JsonValue
    public String getFullName() {
        return this.variety + " " + this.name;
    }
}
