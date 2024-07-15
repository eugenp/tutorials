package com.baeldung.quarkus.todos.boundary;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TodoDto {

    // do not allow to read id from request body, only write in into a response
    @JsonProperty(access = Access.READ_ONLY)
    private Long id;
    @Size(min = 3)
    private String title;
    private boolean completed;
    private LocalDate dueDate;

}
