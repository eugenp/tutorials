package com.baeldung.quarkus.todos.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity(name = "Todo")
@Table(name = "todos")
public class TodoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	private boolean completed;
	private LocalDate dueDate;

}
