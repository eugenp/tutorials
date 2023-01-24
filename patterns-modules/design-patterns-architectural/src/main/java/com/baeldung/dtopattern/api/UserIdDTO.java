package com.baeldung.dtopattern.api;

public class UserIdDTO {

    private String id;

    public UserIdDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
