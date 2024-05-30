package com.baeldung.quarkus.todos.domain;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
public class Todo {

	private Long id;
	@Size(min = 4)
	private String title;
	private boolean completed;
	private LocalDate dueDate;

}
