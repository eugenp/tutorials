package com.baeldung.jackson.dynamicignore;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter("publicFilter")
public class UserFilter {

    private Long id;

    private String name;

    public UserFilter(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserFilter() {
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

}
