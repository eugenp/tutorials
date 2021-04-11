package com.baeldung.architecture.hexagonal.domain;

import javax.validation.constraints.NotNull;

public class UserOutDTO {
    @NotNull
    private String name;
    private Integer id;

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
