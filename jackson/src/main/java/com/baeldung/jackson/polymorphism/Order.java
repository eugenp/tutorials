package com.baeldung.jackson.polymorphism;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.UUID;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class Order {

    private UUID id;
    private Type type;
    private int internalAudit;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "ordertype")
    @JsonSubTypes({ @JsonSubTypes.Type(value = InternalType.class, name = "internal") })
    public static class Type {
        public long id;
        public String name;
    }

    @JsonTypeName("internal")
    public static class InternalType extends Type {
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

    public Order(int internalAudit) {
        this();
        this.type = new Type();
        this.type.id = 20;
        this.type.name = "Order";
        this.internalAudit = internalAudit;
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
