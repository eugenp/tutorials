package com.baeldung.hexagonal.central.domain;

import java.util.UUID;

public class Account {

    private UUID id;

    private String name;

    private AccountType type;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Account [name=" + name + ", type=" + type + "]";
    }

}
