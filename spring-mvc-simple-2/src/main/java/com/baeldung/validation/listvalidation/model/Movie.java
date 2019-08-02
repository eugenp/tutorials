package com.baeldung.validation.listvalidation.model;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Movie {

    private String id;

    @NotEmpty(message = "Movie name cannot be empty.")
    private String name;
    
    public Movie(String name) {
        this.id = UUID.randomUUID()
            .toString();
        this.name = name;
    }
    
}
