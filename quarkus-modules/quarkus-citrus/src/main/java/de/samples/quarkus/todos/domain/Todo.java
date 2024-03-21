package de.samples.quarkus.todos.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import jakarta.validation.constraints.Size;

@Setter
@Getter
public class Todo {

	private Long id;
	@Size(min = 4)
	private String title;
	private boolean completed;
	private LocalDate dueDate;

}
