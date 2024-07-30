package com.baeldung.quarkus.todos.domain;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Todo {

    private Long id;
    @Size(min = 3)
    private String title;
    private boolean completed;
    private LocalDate dueDate;

}
