package com.baeldung.jackson.dynamicignore;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("publicFilter")
public class UserWithFilter {

    private Long id;

    private String name;

    public UserWithFilter(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserWithFilter() {
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

}
