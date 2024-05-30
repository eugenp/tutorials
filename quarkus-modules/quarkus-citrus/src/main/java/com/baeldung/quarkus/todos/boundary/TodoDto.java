package com.baeldung.quarkus.todos.boundary;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TodoDto {

	// do not allow to read id from request body, only write in into a response
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;
	@Size(min = 3)
	private String title;
	private boolean completed;
	// @JsonProperty("due_date") -> see application.properties (quarkus.jackson.property-naming-strategy)
	private LocalDate dueDate;

}
