package com.baeldung.springboot.swagger.model;

import com.baeldung.springboot.swagger.views.Views;
import com.fasterxml.jackson.annotation.JsonView;

public class Author {

    @JsonView(Views.Private.class)
    private Integer id;

    @JsonView(Views.Public.class)
    private String name;

    @JsonView(Views.Public.class)
    private String email;

    public Author() {
    }

    public Author(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
