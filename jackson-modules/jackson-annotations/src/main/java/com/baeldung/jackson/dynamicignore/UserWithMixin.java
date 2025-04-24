package com.baeldung.jackson.dynamicignore;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserWithMixin {

    private Long id;

    private String name;

    public UserWithMixin(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserWithMixin() {
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
    public interface PublicMixin {

        @JsonIgnore
        Long getId();
    }

}
