package com.baeldung.validation.listvalidation.model;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.baeldung.validation.listvalidation.constraint.CustomConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Movie {

    private String id;

    @CustomConstraint
    private String name;

    @Size(min = 2, message = "Atleast 2 genres should be provided.")
    private List<String> genres;

    @NotNull(message = "Actor list cannot be null.")
    @NotEmpty(message = "Actor list cannot be empty.")
    private List<Actor> actors;

    public Movie(String name, List<String> genres, List<Actor> actors) {
        this.id = UUID.randomUUID()
            .toString();
        this.name = name;
        this.genres = genres;
        this.actors = actors;
    }

}
