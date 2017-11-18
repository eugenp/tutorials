package com.baeldung.jacksonannotation.inclusion.jsonignoretype;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

import java.util.UUID;

/**
 * Source code github.com/eugenp/tutorials
 *
 * @author Alex Theedom www.baeldung.com
 * @version 1.0
 */
public class Order {

    private UUID id;
    private Type type;

    @JsonIgnoreType
    public static class Type {
        public long id;
        public String name;
    }

    public Order() {
        this.id = UUID.randomUUID();
    }

    public Order(Type type) {
        this();
        this.type = type;
    }

    public UUID getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
