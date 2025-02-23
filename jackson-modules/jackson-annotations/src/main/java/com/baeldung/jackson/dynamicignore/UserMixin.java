package com.baeldung.jackson.dynamicignore;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserMixin {

    private Long id;

    private String name;

    public UserMixin(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserMixin() {
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    /**
     * Mixin interface that is used to hide sensitive information
     */
    public interface PublicMixIn {
        @JsonIgnore
        Long getId();
    }

}
